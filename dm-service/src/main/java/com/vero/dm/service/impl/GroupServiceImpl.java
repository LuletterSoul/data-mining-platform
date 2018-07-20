package com.vero.dm.service.impl;


import static com.vero.dm.repository.specifications.GroupSpecifications.groupSpec;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityNotFoundException;

import com.vero.dm.model.enums.TaskProgressStatus;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.group.PreviewGroupsNotFoundException;
import com.vero.dm.exception.group.StudentNotFoundException;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.model.Teacher;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.repository.dto.*;
import com.vero.dm.service.GroupService;
import com.vero.dm.util.date.DateStyle;
import com.vero.dm.util.date.DateUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Service
@Slf4j
public class GroupServiceImpl extends AbstractBaseServiceImpl<DataMiningGroup, String> implements GroupService
{

    private Integer gradient = 12;

    /**
     * 缓存分组列表
     */
    private Map<String, List<DataMiningGroup>> cacheGroups = new ConcurrentHashMap<>();

    public void setGradient(Integer gradient)
    {
        this.gradient = gradient;
    }

    @Override
    public DataMiningGroup createGroup(DataMiningGroupDto groupDto)
    {
        DataMiningGroup group = new DataMiningGroup();
        Teacher builder = teacherJpaRepository.findOne(groupDto.getBuilderId());
        Student leader = studentJpaRepository.findByStudentId(groupDto.getLeaderId());
        List<Student> members = studentJpaRepository.findByStudentIds(groupDto.getMemberIds());
        DataMiningTask task = taskJpaRepository.findOne(groupDto.getTaskId());
        if (!members.contains(leader))
        {
            log.error("The group leader must be one of the members.");
            return null;
        }
        BeanUtils.copyProperties(groupDto, group);
        updateTaskStatus(groupDto, group);
        group.setDataMiningTask(task);
        group.setTeacherBuilder(builder);
        group.setGroupMembers(new LinkedHashSet<>(members));
        group.setGroupLeader(leader);
        group.setBuiltTime(new Date());
        groupJpaRepository.save(group);
        this.arrangeTask(groupDto.getGroupId(), groupDto.getTaskId());
        return group;
    }

    @Override
    public DataMiningGroup updateGroup(DataMiningGroupDto groupDto)
    {
        this.updateLeader(groupDto.getLeaderId(), groupDto.getGroupId());
        this.configureGroupMembers(groupDto.getGroupId(), groupDto.getMemberIds());
        DataMiningGroup group = groupJpaRepository.findOne(groupDto.getGroupId());
        BeanUtils.copyProperties(groupDto, group);
        updateTaskStatus(groupDto, group);
        this.arrangeTask(groupDto.getGroupId(), groupDto.getTaskId());
        groupJpaRepository.saveAndFlush(group);
        return group;
    }

    private void updateTaskStatus(DataMiningGroupDto groupDto, DataMiningGroup group) {
        group.setTaskStatus(MiningTaskStatus.map.get(groupDto.getTaskStatus()));
        if (!StringUtils.isEmpty(groupDto.getTaskId())) {
            if (group.getTaskStatus().equals(MiningTaskStatus.toBeAssigned)) {
                group.setTaskStatus(MiningTaskStatus.newTask);
            }
        }
    }

    @Override
    public Page<DataMiningGroup> fetchPageableGroups(Pageable pageable, String groupName,
                                                     Date beginDate, Date endDate,
                                                     String leaderStudentId,
                                                     MiningTaskStatus taskStatus, Boolean fetch)
    {
        log.debug("Begin group information fetch process,Time:[{}]", new Date());
        if (fetch) {
            return new PageImpl<>(groupJpaRepository.findAll(
                    groupSpec(groupName, beginDate, endDate, leaderStudentId, taskStatus)));
        }
        return groupJpaRepository.findAll(
            groupSpec(groupName, beginDate, endDate, leaderStudentId, taskStatus), pageable);
    }

    public Page<DataMiningGroup> fetchPageableGroups(Pageable pageable)
    {
        return groupJpaRepository.findAll(pageable);
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
        cacheGroups.put(queryKey, previewDefaultGroups);
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
            group.setDataMiningTask(taskJpaRepository.findOne(taskId));
            group.setTaskStatus(MiningTaskStatus.newTask);
        }
        Teacher teacherBuilder = teacherJpaRepository.findOne(builderId);
        Student studentBuilder = studentJpaRepository.findOne(builderId);
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

    @Override
    public List<DataMiningGroup> sureDividingGroupRequest(String queryKey)
    {
        List<DataMiningGroup> groups = cacheGroups.get(queryKey);
        if (groups == null || groups.isEmpty())
        {
            String message = "Could not query corresponding groups information.Make sure your have init divide strategy firstly.";
            log.error(message);
            throw new PreviewGroupsNotFoundException(message, ExceptionCode.PreviewGroupsNotFound);
        }
        groupJpaRepository.save(groups);
        cacheGroups.remove(queryKey);
        return groups;
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
            return fetchStudentWithoutGroup(params.getBeginDate(), params.getEndDate());
        }
        else if (studentIds != null && !studentIds.isEmpty())
        {
            return studentJpaRepository.findByStudentIds(studentIds);
        }
        else if (params.getIsIgnoreArrangedTask())
        {
            // 全部学生参与分组
            return studentJpaRepository.findAll();
        }
        return null;
    }

    @Override
    public List<Student> fetchStudentWithoutGroup(Date begin, Date end)
    {
        return studentJpaRepository.fetchStudentWithoutGroup(begin, end);
    }

    @Override
    public DataMiningGroup fetchGroupDetails(String groupId)
    {

        return groupJpaRepository.findOne(groupId);
    }

    @Override
    public List<DataMiningGroup> fetchGroupDetails(List<String> groupIds)
    {
        return groupJpaRepository.findAll(groupIds);
    }

    @Override
    public DataMiningGroup deleteMiningGroupById(String groupId)
    {
        DataMiningGroup group = fetchGroupDetails(groupId);
        cancelGroupConfiguration(group);
        groupJpaRepository.delete(groupId);
        return group;
    }

    private void cancelGroupConfiguration(DataMiningGroup group)
    {
        group.setGroupLeader(null);
        group.setGroupMembers(null);
        group.setStudentBuilder(null);
        group.setDataMiningTask(null);
        groupJpaRepository.save(group);
    }

    @Override
    public List<DataMiningGroup> deleteGroupBatch(List<String> groupIds)
    {
        List<DataMiningGroup> groups = fetchGroupDetails(groupIds);
        groups.forEach(this::cancelGroupConfiguration);
        groupJpaRepository.delete(groups);
        return groups;
    }

    @Override
    public StudentDto updateLeader(String studentId, String groupId)
    {
        DataMiningGroup group = fetchGroupDetails(groupId);
        Student leader = studentJpaRepository.findByStudentId(studentId);
        if (leader == null)
        {
            log.error("Could not found student request to be set leader.");
            throw new EntityNotFoundException("找不到要被设为组长的学生信息");
        }
        if (!group.getGroupMembers().contains(leader))
        {
            throw new IllegalArgumentException("组长必须为该组的一名成员");
        }
        group.setGroupLeader(leader);
        groupJpaRepository.save(group);
        return StudentDto.build(group.getGroupLeader());
    }

    @Override
    public List<StudentDto> fetchGroupMembers(String groupId)
    {
        Set<Student> students = fetchGroupDetails(groupId).getGroupMembers();
        List<StudentDto> studentDtos = new LinkedList<>();
        students.forEach(s -> studentDtos.add(StudentDto.build(s)));
        return studentDtos;
    }

    @Override
    public List<StudentDto> configureGroupMembers(String groupId, List<String> studentIds)
    {
        List<Student> students = studentJpaRepository.findByStudentIds(studentIds);
        DataMiningGroup group = groupJpaRepository.findOne(groupId);
        if (!students.contains(group.getGroupLeader()))
        {
            group.setGroupLeader(null);
        }
        group.setGroupMembers(new LinkedHashSet<>(students));
        groupJpaRepository.save(group);
        return StudentDto.build(students);
    }

    @Override
    public List<String> fetchGroupNames()
    {
        return groupJpaRepository.findGroupNames();
    }

    @Override
    public List<Student> fetchGroupLeaders()
    {
        return groupJpaRepository.findLeaders();
    }

    @Override
    public DataMiningTask arrangeTask(String groupId, String taskId) {
        if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(taskId)) {
            return null;
        }
        DataMiningTask task = taskJpaRepository.findOne(taskId);
        DataMiningGroup group = fetchGroupDetails(groupId);
        group.setDataMiningTask(task);
        TaskProgressStatus status = task.getProgressStatus();
        //更新当前任务状态
        Boolean isAssigned = status.equals(TaskProgressStatus.newCreate) || status.equals(TaskProgressStatus.toBeAssigned);
        if (isAssigned) {
            task.setProgressStatus(TaskProgressStatus.assigned);
        }
        taskJpaRepository.save(task);
        groupJpaRepository.save(group);
        return task;
    }

    @Override
    public MiningTaskStatus updateGroupStatus(String groupId, MiningTaskStatus newStatus) {
        DataMiningGroup group = fetchGroupDetails(groupId);
        group.setTaskStatus(newStatus);
        groupJpaRepository.save(group);
        return newStatus;
    }

    @Override
    public List<MiningTaskStatus> fetchStatusOptions()
    {
        return Arrays.asList(MiningTaskStatus.values());
    }

}
