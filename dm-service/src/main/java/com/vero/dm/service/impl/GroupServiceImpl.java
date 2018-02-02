package com.vero.dm.service.impl;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.model.Teacher;
import com.vero.dm.repository.dto.*;
import com.vero.dm.service.GroupService;

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
        Student leader = studentJpaRepository.findOne(groupDto.getLeaderId());
        List<Student> members = studentJpaRepository.findAll(groupDto.getMemberIds());
        DataMiningTask task = taskJpaRepository.findOne(groupDto.getTaskId());
        if (!members.contains(leader))
        {
            log.error("The group leader must be one of the members.");
            return null;
        }
        BeanUtils.copyProperties(groupDto, group);
        group.setDataMiningTask(task);
        group.setGroupBuilder(builder);
        group.setGroupMembers(new LinkedHashSet<>(members));
        group.setGroupLeader(leader);
        group.setBuiltTime(new Date());
        groupJpaRepository.save(group);
        return group;
    }

    @Override
    public Page<DataMiningGroup> fetchPageableGroups(Pageable pageable)
    {

        return groupJpaRepository.findAll(pageable);
    }

    // 默认简单分组方法
    public DividingGroupInfo initDefaultGroupingStrategy(GroupingConfigParams params)
    {
        // 将要被分配的任务;
        List<DataMiningGroup> previewDefaultGroups = new LinkedList<>();
        DataMiningTask task = taskJpaRepository.findOne(params.getTaskId());
        List<Student> studentsPrepareDivided = getStudentsPrepareDivided(params);
        if (studentsPrepareDivided == null || studentsPrepareDivided.isEmpty())
        {
            log.error("Could't found students who are corresponding to the request.");
            throw new EntityNotFoundException("找不到的符合要求的待分组学生");
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
                if (i % gradient == 0)
                {
                    DataMiningGroup group = buildGroup(params.getBuilderId(), task.getTaskId(), i,
                        perGroupStudents);
                    // 生成预览分组情况，待管理员确认;
                    previewDefaultGroups.add(group);
                    perGroupStudents = new LinkedList<>();
                }
                i++ ;
            }
            // 组员人数非偶数，将后面不足分组梯度且比梯度的一半小的学生列表全加入到最后一个队伍；
            if (restStudent < gradient / 2)
            {
                previewDefaultGroups.get(previewDefaultGroups.size() - 1).getGroupMembers().addAll(
                    perGroupStudents);
            }
        }
        // 符合条件的学生比每组的人数少,直接作为一组
        if (studentsPrepareDivided.size() < gradient)
        {
            DataMiningGroup group = buildGroup(params.getBuilderId(), task.getTaskId(), 1,
                studentsPrepareDivided);
            previewDefaultGroups.add(group);
        }
        // 剩下的学生超过梯度的一半，另起一组
        if (restStudent >= gradient / 2)
        {
            DataMiningGroup group = buildGroup(params.getBuilderId(), task.getTaskId(), 1,
                perGroupStudents);
            previewDefaultGroups.add(group);
        }
        String queryKey = UUID.randomUUID().toString();
        cacheGroups.put(queryKey, previewDefaultGroups);
        return new DividingGroupInfo(queryKey,
            PreviewDividingGroupDto.build(previewDefaultGroups));
    }

    private DataMiningGroup buildGroup(String builderId, String taskId, int arrangementId,
                                       List<Student> perGroupStudents)
    {
        DataMiningGroup group = new DataMiningGroup();
        group.setGroupMembers(new LinkedHashSet<>(perGroupStudents));
        group.setGroupLeader(perGroupStudents.get(0));
        group.setDataMiningTask(taskJpaRepository.findOne(taskId));
        group.setGroupBuilder(teacherJpaRepository.findOne(builderId));
        group.setBuiltTime(new Date());
        group.setGroupName("Group_" + group.getBuiltTime());
        group.setArrangementId(String.valueOf(arrangementId));
        return group;
    }

    @Override
    public List<DataMiningGroup> sureDividingGroupRequest(String queryKey)
    {
        List<DataMiningGroup> groups = cacheGroups.get(queryKey);
        if (groups == null || groups.isEmpty())
        {
            log.error(
                "Could not query corresponding groups information.Make sure your have init divide strategy firstly.");
            throw new IllegalArgumentException("查询不到对应的分组信息");
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
            return studentJpaRepository.fetchStudentWithoutGroup(params.getBeginDate(),
                params.getEndDate());
        }
        else if (studentIds != null && !studentIds.isEmpty())
        {
            return studentJpaRepository.findAll(studentIds);
        }
        else if (params.getIsIgnoreArrangedTask())
        {
            // 全部学生参与分组
            return studentJpaRepository.findAll();
        }
        return null;
    }

    @Override
    public DataMiningGroup fetchGroupDetails(String groupId)
    {

        return groupJpaRepository.findOne(groupId);
    }

    @Override
    public DataMiningGroup deleteMiningGroupById(String groupId)
    {
        DataMiningGroup group = fetchGroupDetails(groupId);
        group.setGroupLeader(null);
        group.setGroupMembers(null);
        group.setGroupBuilder(null);
        group.setDataMiningTask(null);
        groupJpaRepository.save(group);
        groupJpaRepository.delete(groupId);
        return group;
    }

    @Override
    public StudentDto updateLeader(String studentId, String groupId)
    {
        DataMiningGroup group = fetchGroupDetails(groupId);
        Student leader = studentJpaRepository.findOne(studentId);
        if (leader == null)
        {
            log.error("Could not found student request to be set leader.");
            throw new EntityNotFoundException("找不到要被设为组长的学生信息");
        }
        if (!group.getGroupMembers().contains(leader)) {
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
        List<Student> students = studentJpaRepository.findAll(studentIds);
        DataMiningGroup group = findById(groupId);
        if (!students.contains(group.getGroupLeader()))
        {
            group.setGroupLeader(null);
        }
        group.setGroupMembers(new LinkedHashSet<>(students));
        groupJpaRepository.save(group);
        return StudentDto.build(students);
    }
}
