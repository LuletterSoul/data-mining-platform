package com.dm.org.service;

import com.dm.org.dto.StudentDTO;
import com.dm.org.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public interface StudentService extends UserService{
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


}
