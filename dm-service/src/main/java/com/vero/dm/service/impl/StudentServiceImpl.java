package com.vero.dm.service.impl;


import java.util.List;

import com.vero.dm.repository.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.FavoriteStatus;
import com.vero.dm.model.Student;
import com.vero.dm.model.StudentStatus;
import com.vero.dm.service.StudentService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Service
public class StudentServiceImpl extends UserServiceImpl implements StudentService
{
    @Override
    public List<StudentDto> getStudentList()
    {
//        List<Student> students = studentDao.getStudentList();
//        List<StudentDto> list = new LinkedList<StudentDto>();
//        for (Student student : students)
//        {
//            list.add(StudentDto.build(student));
//        }
//        return list;
        return null;
    }

    @Override
    public List<String> getStudentIds()
    {
//        return studentDao.getStudentIds();
        return null;
    }

    @Override
    public Page<StudentDto> getStudentList(Pageable pageable)
    {
//        int counts = studentDao.countAll();
//        List<Student> students = studentDao.get(pageable);
//        List<StudentDto> studentDTOS = new LinkedList<StudentDto>();
//        for (Student s : students)
//        {
//            studentDTOS.add(StudentDto.build(s));
//        }
//        return new PageImpl<StudentDto>(studentDTOS, pageable, counts);
        return null;
    }

    @Override
    public StudentDto deleteByStudentId(String studentId)
    {
//        Student student = studentDao.getStudentById(studentId);
//        studentDao.deleteStudentById(studentId);
//        return StudentDto.build(student);
        return null;
    }

    public StudentDto save(Student student)
    {
        FavoriteStatus favoriteStatus = this.getFavoriteStatusPersisted(
            student.getFavorite().getFavoriteId());
        StudentStatus studentStatus = this.getStudentStatusPersisted(
            student.getStatus().getStatusId());
        student.setFavorite(favoriteStatus);
        student.setStatus(studentStatus);
        this.registerUser(student);
        return new StudentDto(student);
    }

    @Override
    public StudentDto update(StudentDto studentDto)
    {
//        Student student = studentDao.getStudentById(studentDto.getStudentId());
//        BeanUtils.copyProperties(studentDto, student, "status", "favorite");
//        studentDao.update(student);
//        return new StudentDto(student);
        return studentDto;
    }

    @Override
    public StudentDto getStudentById(String studentId)
    {
//        return StudentDto.build(studentDao.getStudentById(studentId));
        return null;
    }

    @Override
    public int markStudents(List<String> studentIds)
    {
//        return studentDao.markStudents(studentIds);
        return 0;
    }

    @Override
    public int unMarkStudents(List<String> studentIds)
    {
//        return studentDao.unMarkStudents(studentIds);
        return 0;
    }

    @Override
    public int deleteWithIdArray(List<String> studentIds)
    {
//        return studentDao.deleteByArray(studentIds);
        return 0;
    }

    @Override
    public FavoriteStatus getFavoriteStatusPersisted(Integer statusId)
    {
//        return studentDao.getFavoriteStatusPersisted(statusId);
        return null;
    }

    @Override
    public StudentStatus getStudentStatusPersisted(Integer statusId)
    {
//        return studentDao.getStudentStatus(statusId);
        return null;
    }

    public List<Student> fetchStudentWithoutTasks()
    {
        return null;
    }
}
