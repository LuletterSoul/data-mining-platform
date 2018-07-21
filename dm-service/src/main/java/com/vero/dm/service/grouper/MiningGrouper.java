package com.vero.dm.service.grouper;

import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.group.PreviewGroupsNotFoundException;
import com.vero.dm.exception.group.StudentNotFoundException;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.Student;
import com.vero.dm.model.Teacher;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.GroupingConfigParams;
import com.vero.dm.repository.dto.PreviewDividingGroupDto;
import com.vero.dm.service.GroupService;
import com.vero.dm.service.MiningTaskService;
import com.vero.dm.service.StudentService;
import com.vero.dm.service.TeacherService;
import com.vero.dm.util.date.DateStyle;
import com.vero.dm.util.date.DateUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  13:52 2018/7/21.
 * @since data-mining-platform
 */

@Slf4j
@Setter
@Component
public class MiningGrouper {

    private Integer gradient;

    private GroupService groupService;

    private MiningTaskService taskService;

    private StudentService studentService;

    private TeacherService teacherService;


    public void setGradient(Integer gradient) {
        this.gradient = gradient;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setTaskService(MiningTaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // 默认简单分组方法
    public DividingGroupInfo initDefaultGroupingStrategy(GroupingConfigParams params)
    {
        gradient = nextGradient(params);
        // 将要被分配的任务;
        List<DataMiningGroup> previewDefaultGroups = new LinkedList<>();
        List<Student> studentsPrepareDivided = getStudentsPrepareDivided(params);
        if (studentsPrepareDivided == null || studentsPrepareDivided.isEmpty())
        {
            String message = "Could't found students who are corresponding to the request.";
            log.error(message);
            throw new StudentNotFoundException(message, ExceptionCode.StudentNotFound);
        }
        Integer restStudent = studentsPrepareDivided.size() % gradient;
        // 用户要求的每个组的总人数,如果用户未配置，默认值为12；
        this.setGradient(params.getGradient());
        List<Student> perGroupStudents = new LinkedList<>();
        if (studentsPrepareDivided.size() >= gradient)
        {
            // int groupNum = studentIds.size() / gradient;
            int i = 1;
            for (Student student : studentsPrepareDivided)
            {
                perGroupStudents.add(student);
                // 到达固定人数分一组;
                if (i == gradient)
                {
                    DataMiningGroup group = buildGroup(params.getBuilderId(), params.getTaskId(),
                            i, perGroupStudents);
                    // 生成预览分组情况，待管理员确认;
                    previewDefaultGroups.add(group);
                    perGroupStudents = new LinkedList<>();
                    gradient = nextGradient(params);
                    i=0;
                }
                i++ ;
            }
            // 组员人数非偶数，将后面不足分组梯度且比梯度的一半小的学生列表全加入到最后一个队伍；
            if (restStudent < gradient / 2)
            {
                previewDefaultGroups.get(previewDefaultGroups.size() - 1).getGroupMembers().addAll(
                        perGroupStudents);
            }
            // 剩下的学生超过梯度的一半，另起一组
            else
            {
                DataMiningGroup group = buildGroup(params.getBuilderId(), params.getTaskId(), 1,
                        perGroupStudents);
                previewDefaultGroups.add(group);
            }
        }
        // 符合条件的学生比每组的人数少,直接作为一组
        if (studentsPrepareDivided.size() < gradient)
        {
            DataMiningGroup group = buildGroup(params.getBuilderId(), params.getTaskId(), 1,
                    studentsPrepareDivided);
            previewDefaultGroups.add(group);
        }
        String queryKey = UUID.randomUUID().toString();
        groupService.setPreviewGroupCache(queryKey, previewDefaultGroups);
        return new DividingGroupInfo(queryKey, PreviewDividingGroupDto.build(previewDefaultGroups),
                previewDefaultGroups.get(0).getDataMiningTask());
    }

    private int nextGradient(GroupingConfigParams params) {
        Random random = new Random();
        return random.nextInt(params.getUpperBound() - params.getLowerBound() + 1) + params.getLowerBound();
    }

    private DataMiningGroup buildGroup(String builderId, String taskId, int arrangementId,
                                       List<Student> perGroupStudents)
    {
        DataMiningGroup group = new DataMiningGroup();
        group.setGroupLeader(perGroupStudents.get(0));
        if (StringUtils.isEmpty(taskId))
        {
            group.setDataMiningTask(null);
            group.setTaskStatus(MiningTaskStatus.toBeAssigned);
        }
        else
        {
            group.setDataMiningTask(taskService.findById(taskId));
            group.setTaskStatus(MiningTaskStatus.newTask);
        }
        Teacher teacherBuilder = teacherService.findTeacherById(builderId);
        Student studentBuilder = studentService.findStudentById(builderId);
        if (!ObjectUtils.isEmpty(teacherBuilder)) {
            group.setTeacherBuilder(teacherBuilder);
        }
        if (!ObjectUtils.isEmpty(studentBuilder)) {
            group.setStudentBuilder(studentBuilder);
        }

        group.setBuiltTime(new Date());
        group.setGroupName(
                "Group_" + DateUtil.DateToString(group.getBuiltTime(), DateStyle.YYYY_MM_DD_HH_MM)
                        + "_" + arrangementId);
        group.setArrangementId(String.valueOf(arrangementId));
        group.setGroupMembers(new LinkedHashSet<>(perGroupStudents));
        return group;
    }


    private List<Student> getStudentsPrepareDivided(GroupingConfigParams params)
    {
        List<String> studentIds = params.getSpecifiedDividingStudents();
        // 如果用户不指定要分组的学生，且不忽略当前有任务在身学生
        if ((studentIds == null || studentIds.isEmpty()) && !params.getIsIgnoreArrangedTask())
        {
            // 前端传入的时间参数，要求在此时间端内，学生没有处于其他实践任务分组执行数据挖掘任务；
            // 以任务的计划时间为准
            // 获取符合要求的学生
            return groupService.fetchStudentWithoutGroup(params.getBeginDate(), params.getEndDate());
        }
        else if (studentIds != null && !studentIds.isEmpty())
        {
            return studentService.findByStudentIds(studentIds);
        }
        else if (params.getIsIgnoreArrangedTask())
        {
            // 全部学生参与分组
            return studentService.findAllStudents();
        }
        return null;
    }
}
