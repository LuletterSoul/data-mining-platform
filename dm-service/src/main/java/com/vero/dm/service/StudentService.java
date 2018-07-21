package com.vero.dm.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vero.dm.repository.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.FavoriteStatus;
import com.vero.dm.model.Student;
import com.vero.dm.model.StudentStatus;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface StudentService extends UserService
{
    List<Student> importStudents(MultipartFile file);

    byte[] handleStudentExcelModuleDownload();

    List<String> getStudentIds();

    List<Student> findAllStudents();

    Student findStudentById(String id);

    List<Student> findByStudentIds(List<String> ids);


    /**
     * 分页获取的库内的学生信息
     * @param fetch
     * @param pageable
     * @param className
     * @param profession
     * @param grade
     * @param studentIdPrefix
     * @param studentName
     * @param beginDate
     * @param endDate
     * @return
     */
    Page<Student> getStudentList(boolean fetch,
                                 Pageable pageable,
                                 String className,
                                 String profession,
                                 String grade,
                                 String studentIdPrefix,
                                 String studentName,
                                 Date beginDate,
                                 Date endDate);



    /**
     * 获取全部空闲的学生
     * @param pageable
     * @param className
     * @param profession
     * @param grade
     * @param studentIdPrefix
     * @param studentName
     * @param beginDate
     * @param endDate
     * @return
     */
    List<Student> getAllLeisureStudents(Pageable pageable, String className, String profession, String grade, String studentIdPrefix, String studentName, Date beginDate, Date endDate);


    StudentDto deleteByStudentId(String studentId);

    StudentDto save(Student student);

    StudentDto update(StudentDto studentDto);

    StudentDto getStudentById(String studentId);

    int markStudents(List<String> studentIds);

    int unMarkStudents(List<String> studentIds);

    List<Student> deleteBatchByStudentIds(List<String> studentIds);

    FavoriteStatus getFavoriteStatusPersisted(Integer statusId);

    /**
     * @param statusId
     * @return
     */
    StudentStatus getStudentStatusPersisted(Integer statusId);

    Map<String, List<?>> getStudentPropertiesOptions();


}
