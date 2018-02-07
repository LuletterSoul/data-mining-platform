package com.vero.dm.service;


import java.util.List;

import com.vero.dm.repository.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.FavoriteStatus;
import com.vero.dm.model.Student;
import com.vero.dm.model.StudentStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface StudentService extends UserService
{
    List<StudentDto> getStudentList();

    List<Student> importStudents(MultipartFile file);

    byte[] handleStudentExcelModuleDownload();

    List<String> getStudentIds();

    Page<StudentDto> getStudentList(Pageable pageable);

    StudentDto deleteByStudentId(String studentId);

    StudentDto save(Student student);

    StudentDto update(StudentDto studentDto);

    StudentDto getStudentById(String studentId);

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
