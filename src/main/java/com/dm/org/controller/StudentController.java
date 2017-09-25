package com.dm.org.controller;


import java.util.List;

import com.dm.org.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dm.org.dto.StudentDTO;
import com.dm.org.service.StudentService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = "/students"
        )
public class StudentController
    {
        private StudentService studentService;

        private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

        @Autowired
    @Qualifier("studentServiceImpl")
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentDTO> create(@RequestBody Student student)
    {
        StudentDTO studentDTO = studentService.save(student);
        return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.CREATED);
    }

    /**
     * 后续需要处理好修改密码等敏感操作
     * DTO跟
     * @param studentDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO)
    {
        return new ResponseEntity<StudentDTO>(studentService.update(studentDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentDTO> delete(@PathVariable("studentId") String studentId)
    {
        StudentDTO studentDTO = studentService.deleteByStudentId(studentId);
        return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public StudentDTO get(@PathVariable("studentId") String studentId)
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
        return new ResponseEntity<Integer>(studentService.unMarkStudents(studentIds), HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteWithArray", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteBatch(@RequestBody List<String> studentIds)
    {
        return new ResponseEntity<Integer>(studentService.deleteWithIdArray(studentIds), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<StudentDTO> studentsPageable(@PageableDefault(size = 20,
            page = 0,
            sort = {"studentId"},
            direction = Sort.Direction.DESC) Pageable pageable) {
        return studentService.getStudentList(pageable);
    }
}
