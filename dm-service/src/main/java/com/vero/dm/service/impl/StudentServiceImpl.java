package com.vero.dm.service.impl;


import com.dm.org.dto.StudentDTO;
import com.dm.org.model.FavoriteStatus;
import com.dm.org.model.Student;
import com.dm.org.model.StudentStatus;
import com.dm.org.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Service
public class StudentServiceImpl extends UserServiceImpl implements StudentService
{
    @Override
    public List<StudentDTO> getStudentList()
    {
        List<Student> students = studentDao.getStudentList();
        List<StudentDTO> list = new LinkedList<StudentDTO>();
        for (Student student : students)
        {
            list.add(StudentDTO.build(student));
        }
        return list;
    }

    @Override
    public List<String> getStudentIds()
    {
        return studentDao.getStudentIds();
    }

    @Override
    public Page<StudentDTO> getStudentList(Pageable pageable)
    {
        int counts = studentDao.countAll();
        List<Student> students = studentDao.get(pageable);
        List<StudentDTO> studentDTOS = new LinkedList<StudentDTO>();
        for (Student s :
                students) {
            studentDTOS.add(StudentDTO.build(s));
        }
        return new PageImpl<StudentDTO>(studentDTOS, pageable, counts);
    }

    @Override
    public StudentDTO deleteByStudentId(String studentId)
    {
        Student student = studentDao.getStudentById(studentId);
        studentDao.deleteStudentById(studentId);
        return StudentDTO.build(student);
    }

    public StudentDTO save(Student student)
    {
        FavoriteStatus favoriteStatus = this.getFavoriteStatusPersisted(student.getFavorite().getFavoriteId());
        StudentStatus studentStatus = this.getStudentStatusPersisted(student.getStatus().getStatusId());
        student.setFavorite(favoriteStatus);
        student.setStatus(studentStatus);
        this.registerUser(student);
        return new StudentDTO(student);
    }



    @Override
    public StudentDTO update(StudentDTO studentDTO)
    {
        Student student = studentDao.getStudentById(studentDTO.getStudentId());
        BeanUtils.copyProperties(studentDTO,student , "status", "favorite");
        studentDao.update(student);
        return new StudentDTO(student);
    }

    @Override
    public StudentDTO getStudentById(String studentId)
    {
        return StudentDTO.build(studentDao.getStudentById(studentId));
    }

    @Override
    public int markStudents(List<String> studentIds)
    {
        return studentDao.markStudents(studentIds);
    }

    @Override
    public int unMarkStudents(List<String> studentIds)
    {
        return studentDao.unMarkStudents(studentIds);
    }

    @Override
    public int deleteWithIdArray(List<String> studentIds)
    {
        return studentDao.deleteByArray(studentIds);
    }

    @Override
    public FavoriteStatus getFavoriteStatusPersisted(Integer statusId) {
        return studentDao.getFavoriteStatusPersisted(statusId);
    }
    @Override
    public StudentStatus getStudentStatusPersisted(Integer statusId) {
        return studentDao.getStudentStatus(statusId);
    }

    public List<Student> fetchStudentWithoutTasks() {
        return null;
    }
}
