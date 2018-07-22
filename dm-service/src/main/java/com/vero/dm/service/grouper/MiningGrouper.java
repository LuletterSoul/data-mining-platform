package com.vero.dm.service.grouper;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.group.StudentNotFoundException;
import com.vero.dm.exception.group.TasksEmptyException;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.Student;
import com.vero.dm.model.Teacher;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.GroupingConfigParams;
import com.vero.dm.service.GroupService;
import com.vero.dm.service.MiningTaskService;
import com.vero.dm.service.StudentService;
import com.vero.dm.service.TeacherService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:52 2018/7/21.
 * @since data-mining-platform
 */

@Slf4j
@Setter
@Component
public class MiningGrouper
{

    private GroupService groupService;

    private MiningTaskService taskService;

    private StudentService studentService;

    private TeacherService teacherService;

    private GroupingStrategy groupingStrategy;

    private Map<Integer, GroupingStrategy> strategies;

    public MiningGrouper()
    {
        strategies = GroupingStrategyPool.createInstancePool(this);
    }

    @Autowired
    public void setGroupService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @Autowired
    public void setTaskService(MiningTaskService taskService)
    {
        this.taskService = taskService;
    }

    @Autowired
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService)
    {
        this.teacherService = teacherService;
    }

    // 默认简单分组方法
    public DividingGroupInfo initDefaultGroupingStrategy(GroupingConfigParams params)
    {
        List<Student> candidates = getStudentsPrepareDivided(params);
        List<String> taskIds = params.getSpecifiedTasks();
        switchStrategy(params);
        validateInputParams(candidates, taskIds);
        checkStrategyConfiguration();
        return groupingStrategy.doStrategy(params, candidates, params.getSpecifiedTasks());
    }

    private void switchStrategy(GroupingConfigParams params)
    {
        // 切换策略
        this.setGroupingStrategy(this.strategies.get(params.getStrategyId()));
    }

    private void checkStrategyConfiguration()
    {
        if (groupingStrategy == null)
        {
            log.debug("The grouping strategy is not configured.User simple grouping strategy.");
            groupingStrategy = new SimpleGroupingStrategy(this);
        }
    }

    private void validateInputParams(List<Student> candidates, List<String> taskIds)
    {
        if (candidates == null || candidates.isEmpty())
        {
            String message = "找不到请求的学生分组列表";
            log.error(message);
            throw new StudentNotFoundException(message, ExceptionCode.StudentNotFound);
        }
        if (taskIds.isEmpty())
        {
            String message = "待分配的任务不能为空";
            log.error(message);
            throw new TasksEmptyException(message, ExceptionCode.TasksEmpty);
        }
    }

    // private int nextGradient(GroupingConfigParams params) {
    // Random random = new Random();
    // return random.nextInt(params.getUpperBound() - params.getLowerBound() + 1) +
    // params.getLowerBound();
    // }

    public DataMiningGroup buildGroup(String builderId, String taskId, String arrangementId,
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
        if (!ObjectUtils.isEmpty(teacherBuilder))
        {
            group.setTeacherBuilder(teacherBuilder);
        }
        if (!ObjectUtils.isEmpty(studentBuilder))
        {
            group.setStudentBuilder(studentBuilder);
        }

        group.setBuiltTime(new Date());
        group.setGroupName("Group_" + arrangementId);
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
            return groupService.fetchStudentWithoutGroup(params.getBeginDate(),
                params.getEndDate());
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

    public String cachePreview(List<DataMiningGroup> previewDefaultGroups)
    {
        String queryKey = UUID.randomUUID().toString();
        groupService.setPreviewGroupCache(queryKey, previewDefaultGroups);
        return queryKey;
    }
}
