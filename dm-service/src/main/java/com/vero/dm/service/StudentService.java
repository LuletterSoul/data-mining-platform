package com.vero.dm.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.FavoriteStatus;
import com.vero.dm.model.Student;
import com.vero.dm.model.StudentStatus;
import com.vero.dm.repository.dto.StudentDTO;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface StudentService extends UserService
{
    List<StudentDTO> getStudentList();

    List<String> getStudentIds();

    Page<StudentDTO> getStudentList(Pageable pageable);

    StudentDTO deleteByStudentId(String studentId);

    StudentDTO save(Student student);

    StudentDTO update(StudentDTO studentDTO);

    StudentDTO getStudentById(String studentId);

    int markStudents(List<String> studentIds);

    int unMarkStudents(List<String> studentIds);

    int deleteWithIdArray(List<String> studentIds);

    FavoriteStatus getFavoriteStatusPersisted(Integer statusId);

    /**
     * @param statusId
     * @return
     */
    StudentStatus getStudentStatusPersisted(Integer statusId);

}
