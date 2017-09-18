package com.dm.org.service.impl;


import com.dm.org.dao.impl.StudentDao;
import com.dm.org.dto.StudentDTO;
import com.dm.org.model.Student;
import com.dm.org.model.User;
import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
    public Page<Student> getStudentList(Pageable pageable)
    {
        return studentDao.get(pageable);
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
        this.registerUser(student);
        return new StudentDTO(student);
    }

    @Override
    public StudentDTO update(StudentDTO studentDTO)
    {
        Student student = studentDao.getStudentById(studentDTO.getStudentId());
        Student studentUpdated = StudentDTO.wrap(studentDTO);
        studentUpdated.setPassword(student.getPassword());
        studentUpdated.setPublicSalt(student.getPublicSalt());
        studentUpdated.setPrivateSalt(student.getPrivateSalt());
        studentDao.update(studentUpdated);
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
}
