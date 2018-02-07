package com.vero.dm.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.api.progress.UploadProgressListener;
import com.vero.dm.model.Student;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.service.StudentService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@Slf4j
@RequestMapping(value = ApiVersion.API_VERSION + "/students")
public class StudentController
{
    private StudentService studentService;

    @Autowired
    @Qualifier("studentServiceImpl")
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentDto> create(@RequestBody Student student)
    {
        StudentDto studentDto = studentService.save(student);
        return new ResponseEntity<StudentDto>(studentDto, HttpStatus.CREATED);
    }

    @ApiOperation("上传一个Excel文件,由文件导入学生数据,文件的模板由服务器提供")
    @PostMapping(value = "/excel_students")
    public ResponseEntity<List<Student>> importStudents(@RequestPart MultipartFile file,
                                                        @RequestParam(UploadProgressListener.PROC_QUERY_KEY) String progressQueryId)
    {
        return new ResponseEntity<>(studentService.importStudents(file), HttpStatus.OK);
    }



    /**
     * 后续需要处理好修改密码等敏感操作 DTO跟
     * 
     * @param studentDto
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<StudentDto> update(@RequestBody StudentDto studentDto)
    {
        return new ResponseEntity<StudentDto>(studentService.update(studentDto), HttpStatus.OK);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentDto> delete(@PathVariable("studentId") String studentId)
    {
        StudentDto studentDto = studentService.deleteByStudentId(studentId);
        return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public StudentDto get(@PathVariable("studentId") String studentId)
    {
        return studentService.getStudentById(studentId);
    }

    @RequestMapping(value = "/markStudentsWithArray", method = RequestMethod.PUT)
    public ResponseEntity<Integer> markStudent(@RequestBody List<String> studentIds)
    {
        return new ResponseEntity<Integer>(studentService.markStudents(studentIds), HttpStatus.OK);
    }

    @RequestMapping(value = "/unMarkStudentWithArray", method = RequestMethod.PUT)
    public ResponseEntity<Integer> unMarkStudent(@RequestBody List<String> studentIds)
    {
        return new ResponseEntity<Integer>(studentService.unMarkStudents(studentIds),
            HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteWithArray", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteBatch(@RequestBody List<String> studentIds)
    {
        return new ResponseEntity<Integer>(studentService.deleteWithIdArray(studentIds),
            HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<StudentDto> studentsPageable(@PageableDefault(size = 10, page = 0, sort = {
        "studentId"}, direction = Sort.Direction.DESC) Pageable pageable)
    {
        return studentService.getStudentList(pageable);
    }
}
