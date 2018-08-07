package com.vero.dm.api.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.model.Student;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.security.credentials.StatelessCredentialsServer;
import com.vero.dm.service.StudentService;
import com.vero.dm.service.UserService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Profile(value = {"prod", "dev", "test"})
@RestController
@Slf4j
@RequestMapping(value = ApiVersion.API_VERSION + ResourcePath.STUDENT_PATH)
public class StudentController
{
    private StudentService studentService;

    private StatelessCredentialsServer credentialsServer;

    private UserService userService;

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setCredentialsServer(StatelessCredentialsServer credentialsServer)
    {
        this.credentialsServer = credentialsServer;
    }


    @Autowired
    @Qualifier("studentServiceImpl")
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @ApiOperation("创建一个学生")
    @CacheEvict(cacheNames = "studentPageableCache", allEntries = true)
    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody Student student)
    {
        StudentDto d = credentialsServer.registerStudent(student);
        Student newStu = studentService.findByStudentId(d.getStudentId());
        List<String> roleNames = new ArrayList<>();
        // 为每个学生分配学生角色，赋予访问权限
        roleNames.add("student");
        userService.correlateRoles(newStu.getUserId(), roleNames);
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }

    @ApiOperation("上传一个Excel文件,由文件导入学生数据,文件的模板由服务器提供")
    @CacheEvict(cacheNames = "studentPageableCache", allEntries = true)
    @PostMapping(value = "/excel_students")
    public ResponseEntity<List<StudentDto>> importStudents(@RequestPart MultipartFile file)
    {
        List<Student> students = studentService.importStudents(file);
        List<String> roleNames = new ArrayList<>();
        // 为每个学生分配学生角色，赋予访问权限
        roleNames.add("student");
        userService.correlateRoles(students, roleNames);
        return new ResponseEntity<>(credentialsServer.registerImportedStudents(students),
            HttpStatus.OK);
    }

    @ApiOperation("创建一个学生信息")
    @CacheEvict(cacheNames = "studentPageableCache", allEntries = true)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<StudentDto> update(@RequestBody StudentDto studentDto)
    {
        return new ResponseEntity<>(studentService.update(studentDto), HttpStatus.OK);
    }

    @ApiOperation("获取空闲的学生列表")
    @GetMapping(value = "/leisure_students")
    @Cacheable(cacheNames = "studentPageableCache")
    public ResponseEntity<List<Student>> leisureStudents(@PageableDefault(size = 10, sort = {
        "studentId"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                         @ApiParam("行政班") @RequestParam(name = "className", required = false, defaultValue = "") String className,
                                                         @ApiParam("专业") @RequestParam(value = "profession", required = false, defaultValue = "") String profession,
                                                         @ApiParam("年级") @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
                                                         @ApiParam("学号前缀模糊查询") @RequestParam(value = "studentId", required = false, defaultValue = "") String studentIdPrefix,
                                                         @ApiParam("姓名") @RequestParam(value = "studentName", required = false, defaultValue = "") String studentName,
                                                         @ApiParam("开始日期") @RequestParam(value = "beginDate", required = false) Date beginDate,
                                                         @ApiParam("结束日期") @RequestParam(value = "endDate", required = false) Date endDate)
    {
        return new ResponseEntity<>(studentService.getAllLeisureStudents(pageable, className,
            profession, grade, studentIdPrefix, studentName, beginDate, endDate), HttpStatus.OK);
    }

    @ApiOperation("获取一个学生的信息")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public StudentDto get(@PathVariable("userId") String userId,
                          @RequestParam(value = "username", defaultValue = "-1",required = false) String username)
    {
        return studentService.getStudent(userId,username);
    }

//    @RequestMapping(value = "/markStudentsWithArray", method = RequestMethod.PUT)
////    public ResponseEntity<Integer> markStudent(@RequestBody List<String> studentIds)
////    {
////        return new ResponseEntity<>(studentService.markStudents(studentIds), HttpStatus.OK);
////    }
////
////    @RequestMapping(value = "/unMarkStudentWithArray", method = RequestMethod.PUT)
////    public ResponseEntity<Integer> unMarkStudent(@RequestBody List<String> studentIds)
////    {
////        return new ResponseEntity<>(studentService.unMarkStudents(studentIds), HttpStatus.OK);
////    }

    @ApiOperation("根据学号删除学生")
    @CacheEvict(cacheNames = "studentPageableCache", allEntries = true)
    @DeleteMapping
    public ResponseEntity<List<Student>> deleteBatch(@RequestBody List<String> studentIds)
    {
        return new ResponseEntity<>(studentService.deleteBatchByStudentIds(studentIds),
            HttpStatus.NO_CONTENT);
    }

    @ApiOperation("分页查询学生列表")
    @GetMapping
    @Cacheable(cacheNames = "studentPageableCache")
    public ResponseEntity<Page<Student>> studentsPageable(@PageableDefault(size = 10, sort = {
        "studentId"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                          @ApiParam("是否查询全部") @RequestParam(name = "fetch", required = false, defaultValue = "false") Boolean fetch,
                                                          @ApiParam("行政班") @RequestParam(name = "className", required = false, defaultValue = "") String className,
                                                          @ApiParam("专业") @RequestParam(value = "profession", required = false, defaultValue = "") String profession,
                                                          @ApiParam("年级") @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
                                                          @ApiParam("学号前缀模糊查询") @RequestParam(value = "studentId", required = false, defaultValue = "") String studentIdPrefix,
                                                          @ApiParam("姓名") @RequestParam(value = "studentName", required = false, defaultValue = "") String studentName,
                                                          @ApiParam("开始日期") @RequestParam(value = "beginDate", required = false) Date beginDate,
                                                          @ApiParam("结束日期") @RequestParam(value = "endDate", required = false) Date endDate)
    {
        return new ResponseEntity<>(studentService.getStudentList(fetch, pageable, className,
            profession, grade, studentIdPrefix, studentName, beginDate, endDate), HttpStatus.OK);
    }
}
