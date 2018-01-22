package com.vero.dm.service.impl;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.repository.dto.StudentDTO;
import com.vero.dm.repository.dto.TaskConfigParams;
import com.vero.dm.service.GroupService;
import com.vero.dm.util.DateUtil;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Service
public class GroupServiceImpl extends AbstractBaseServiceImpl<DataMiningGroup, String> implements GroupService
{

    private SimpleDateFormat dateFormat;

    private Integer DEFAULT_GRADIENT = 12;

    public void setDefaultGradient(Integer defaultGradient)
    {
        DEFAULT_GRADIENT = defaultGradient;
    }

    // 默认简单分组方法
    public List<DataMiningGroup> initDefaultGroupingStrategy(TaskConfigParams params)
    {
        // 将要被分配的任务;
        List<DataMiningGroup> previewDefaultGroups = new LinkedList<DataMiningGroup>();
        DataMiningTask task = miningTaskDao.findById(params.getTaskId());
        // 前端传入的时间参数，要求在此时间端内，学生没有处于其他实践任务分组执行数据挖掘任务；
        Date beginDate = DateUtil.GetSQLDate(params.getBeginDate());
        Date endDate = DateUtil.GetSQLDate(params.getEndDate());
        // 获取符合要求的学生
        List<String> studentIds = studentDao.fetchStudentWithoutGroup(beginDate, endDate);
        // 用户要求的每个组的总人数,如果用户未配置，默认值为12；
        if (params.getGradient() != 0)
        {
            this.setDefaultGradient(params.getGradient());
        }
        if (studentIds.size() >= DEFAULT_GRADIENT)
        {
            // int groupNum = studentIds.size() / DEFAULT_GRADIENT;
            int i = 1;
            List<String> groupStudentIds = new LinkedList<String>();
            for (String studentId : studentIds)
            {
                groupStudentIds.add(studentId);
                // 到达固定人数分一组;
                if (i % DEFAULT_GRADIENT == 0)
                {
                    DataMiningGroup group = new DataMiningGroup();
                    List<Student> students = studentDao.getStudentsWithList(groupStudentIds);
                    group.setGroupMembers(new LinkedHashSet<Student>(students));
                    // 生成预览分组情况，待管理员确认;
                    previewDefaultGroups.add(group);
                    groupStudentIds.clear();
                }
                i++ ;
            }
            // 组员人数非偶数，将后面不足分组基数的学生全加入到最后一个队伍；
            if (studentIds.size() % DEFAULT_GRADIENT != 0)
            {
                List<Student> students = studentDao.getStudentsWithList(groupStudentIds);
                previewDefaultGroups.get(previewDefaultGroups.size() - 1).getGroupMembers().addAll(
                    students);
            }
        }
        return previewDefaultGroups;
    }

    @Override
    public DataMiningGroup fetchGroupDetails(String groupId)
    {
        return groupDao.fetchGroup(groupId);
    }

    @Override
    public DataMiningGroup deleteMiningGroupById(String groupId)
    {
        DataMiningGroup group = fetchGroupDetails(groupId);
        this.deleteById(groupId);
        return group;
    }

    @Override
    public StudentDTO setGroupLeader(String studentId, String groupId)
    {
        Student leader = this.studentDao.getStudentById(studentId);
        DataMiningGroup group = groupDao.findById(groupId);
        group.setGroupLeader(leader);
        return StudentDTO.build(leader);
    }

    @Override
    public StudentDTO rescindGroupLeader(String studentId, String groupId)
    {
        DataMiningGroup group = groupDao.findById(groupId);
        group.setGroupLeader(null);
        return StudentDTO.build(group.getGroupLeader());
    }

    @Override
    public List<StudentDTO> fetchGroupMembers(String groupId)
    {
        return StudentDTO.build(groupDao.fetchGroupMembers(groupId));
    }

    @Override
    public StudentDTO addGroupMember(String groupId, String studentId)
    {
        DataMiningGroup group = findById(groupId);
        Student student = studentDao.getStudentById(studentId);
        group.getGroupMembers().add(student);
        return StudentDTO.build(student);
    }

    @Override
    public List<StudentDTO> addGroupMembers(String groupId, List<String> studentIds)
    {
        List<Student> students = studentDao.getStudentsWithList(studentIds);
        DataMiningGroup group = findById(groupId);
        group.getGroupMembers().addAll(students);
        List<StudentDTO> studentDTOS = new LinkedList<StudentDTO>();
        for (Student student : students)
        {
            studentDTOS.add(StudentDTO.build(student));
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO removeGroupMember(String groupId, String studentId)
    {
        Student member = this.studentDao.getStudentById(studentId);
        groupDao.removeMemberById(groupId, studentId);
        return StudentDTO.build(member);
    }

    @Override
    public List<StudentDTO> removeGroupMembers(String groupId, List<String> studentIds)
    {
        List<Student> members = studentDao.getStudentsWithList(studentIds);
        groupDao.removeMembersWithArray(groupId, studentIds);
        return StudentDTO.build(members);
    }

    @Override
    public List<StudentDTO> removeAllGroupMembers(String groupId)
    {
        List<StudentDTO> members = fetchGroupMembers(groupId);
        groupDao.removeAllMembers(groupId);
        return members;
    }

    @Override
    public DataMiningTask getInvolvedTask(String taskId)
    {
        return null;
    }

}
