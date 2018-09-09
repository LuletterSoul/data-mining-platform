package com.vero.dm.api.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.vero.dm.api.base.ConfigurationWirer;
import com.vero.dm.exception.error.ErrorInfo;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.model.Student;
import com.vero.dm.service.StudentService;
import com.vero.dm.service.constant.ResourcePath;
import org.springframework.test.context.ActiveProfiles;


/**
 * StudentController Tester.
 *
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 *
 *        <pre>
 * ���� 3, 2018
 *        </pre>
 *
 * @version 1.0
 */
@ActiveProfiles(profiles = "dev")
public class StudentControllerTest extends ConfigurationWirer
{

    private StudentService studentService;

    private String basePath = ApiVersion.API_VERSION + ResourcePath.STUDENT_PATH;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Before
    public void before()
            throws Exception
    {}

    @After
    public void after()
            throws Exception
    {}

    /**
     * Method: create(@RequestBody Student student)
     */
    @Test
    public void testCreateStudent()
            throws Exception
    {
        Student student1 = new Student();
        student1.setPassword("123");
        student1.setGrade("2015级");
        student1.setClassName("9151068402");
        student1.setStudentName("学生注册服务测试者");
        student1.setUsername("测试者" + UUID.randomUUID().toString().substring(0, 5));
        student1.setStudentId("915106840" + UUID.randomUUID().toString().substring(0, 3));
        student1.setProfession("软件工程");

        String stu1Str = objectMapper.writeValueAsString(student1);
        mockMvc.perform(
                post(basePath).content(stu1Str).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(
                print()).andExpect(
                status().isCreated()).andReturn().getResponse().getContentAsString();
    }

    /**
     * Method: create(@RequestBody Student student)
     */
    @Test
    public void testDuplicatedUsername()
            throws Exception
    {
        Student student1 = new Student();
        student1.setPassword("123");
        student1.setGrade("2015级");
        student1.setClassName("9151068402");
        student1.setStudentName("学生注册服务测试者");
        student1.setUsername("测试者" + UUID.randomUUID().toString().substring(0, 5));
        student1.setStudentId("915106840" + UUID.randomUUID().toString().substring(0, 3));
        student1.setProfession("软件工程");

        String stu1Str = objectMapper.writeValueAsString(student1);
        mockMvc.perform(
                post(basePath).content(stu1Str).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(
                print()).andExpect(
                status().isCreated()).andReturn().getResponse().getContentAsString();

        String e = mockMvc.perform(
                post(ApiVersion.API_VERSION.concat(ResourcePath.STUDENT_PATH)).content(
                        stu1Str).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(
                status().isConflict()).andReturn().getResponse().getContentAsString();

        ErrorInfo errorInfo = objectMapper.readValue(e, ErrorInfo.class);
        Assert.assertEquals(ExceptionCode.DuplicatedUsername.getCode(), errorInfo.getErrorCode());
    }

    @Test
    public void testCreateThrowRegisterInvalid()
            throws Exception
    {
        Student student1 = new Student();
        student1.setGrade("2015级");
        student1.setClassName("9151068402");
        student1.setStudentName("学生注册服务测试者");
        student1.setProfession("软件工程");

        String stu1Str = objectMapper.writeValueAsString(student1);
        String e = mockMvc.perform(
                post(basePath).content(stu1Str).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(
                print()).andExpect(
                status().isBadRequest()).andReturn().getResponse().getContentAsString();
        ErrorInfo errorInfo = objectMapper.readValue(e, ErrorInfo.class);
        Assert.assertEquals(ExceptionCode.RegisterInvalidException.getCode(),
                errorInfo.getErrorCode());
    }

    @Test
    public void testStudentAcceptableAccount()
            throws Exception
    {
        String existId = "LIUXIANGDE_" + UUID.randomUUID().toString().substring(0, 8);
        Student student1 = new Student();
        student1.setGrade("2015级");
        student1.setClassName("9151068402");
        student1.setStudentName("学生注册服务测试者");
        student1.setProfession("软件工程");
        student1.setStudentId(existId);
        student1.setRegister(true);
        studentService.save(student1);

        Student newStu = new Student();
        newStu.setPassword("123");
        newStu.setStudentId(existId);
        newStu.setUsername("测试者");

        String stu1Str = objectMapper.writeValueAsString(newStu);
        String e = mockMvc.perform(
                post(basePath).content(stu1Str).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(
                print()).andExpect(
                status().isForbidden()).andReturn().getResponse().getContentAsString();
        ErrorInfo errorInfo = objectMapper.readValue(e, ErrorInfo.class);
        Assert.assertEquals(ExceptionCode.AccountAccepted.getCode(), errorInfo.getErrorCode());
    }
}
