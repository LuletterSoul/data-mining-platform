package com.dm.org.service.impl;

import com.dm.org.dto.StudentDTO;
import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;
import com.dm.org.model.Student;
import com.dm.org.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Service
public class GroupServiceImpl extends AbstractBaseServiceImpl<DataMiningGroup,String> implements GroupService {

    @Override
    public DataMiningGroup fetchGroupDetails(String groupId) {
        return groupDao.fetchGroup(groupId);
    }

    @Override
    public DataMiningGroup deleteMiningGroupById(String groupId) {
        DataMiningGroup group = fetchGroupDetails(groupId);
        this.deleteById(groupId);
        return group;
    }

    @Override
    public StudentDTO setGroupLeader(String studentId, String groupId) {
        Student leader = this.studentDao.getStudentById(studentId);
        DataMiningGroup group = groupDao.findById(groupId);
        group.setGroupLeader(leader);
        return StudentDTO.build(leader);
    }

    @Override
    public StudentDTO rescindGroupLeader(String studentId, String groupId) {
        DataMiningGroup  group = groupDao.findById(groupId);
        group.setGroupLeader(null);
        return StudentDTO.build(group.getGroupLeader());
    }

    @Override
    public List<StudentDTO> fetchGroupMembers(String groupId) {
        return StudentDTO.build(groupDao.fetchGroupMembers(groupId));
    }

    @Override
    public StudentDTO addGroupMember(String groupId, String studentId) {
        DataMiningGroup group = findById(groupId);
        Student student = studentDao.getStudentById(studentId);
        group.getGroupMembers().add(student);
        return StudentDTO.build(student);
    }

    @Override
    public List<StudentDTO> addGroupMembers(String groupId, List<String> studentIds) {
        List<Student> students = studentDao.getStudentsWithList(studentIds);
        DataMiningGroup group = findById(groupId);
        group.getGroupMembers().addAll(students);
        List<StudentDTO> studentDTOS = new LinkedList<StudentDTO>();
        for (Student student:students
             ) {
            studentDTOS.add(StudentDTO.build(student));
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO removeGroupMember(String groupId, String studentId) {
        Student member = this.studentDao.getStudentById(studentId);
        groupDao.removeMemberById(groupId, studentId);
        return StudentDTO.build(member);
    }

    @Override
    public List<StudentDTO> removeGroupMembers(String groupId, List<String> studentIds) {
        List<Student> members = studentDao.getStudentsWithList(studentIds);
        groupDao.removeMembersWithArray(groupId, studentIds);
        return StudentDTO.build(members);
    }

    @Override
    public List<StudentDTO> removeAllGroupMembers(String groupId) {
        List<StudentDTO> members = fetchGroupMembers(groupId);
        groupDao.removeAllMembers(groupId);
        return members;
    }

    @Override
    public DataMiningTask getInvolvedTask(String taskId) {
        return null;
    }

}
