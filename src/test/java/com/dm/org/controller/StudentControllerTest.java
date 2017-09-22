package com.dm.org.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dm.org.dto.StudentDTO;
import com.dm.org.model.FavoriteStatus;
import com.dm.org.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dm.org.base.ConfigurationWirer;
import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.StudentService;

import java.util.List;
import java.util.UUID;


/**
 * StudentController Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * 17, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class StudentControllerTest extends ConfigurationWirer
{
    private StudentService studentService;

    private StatelessCredentialsService credentialsService;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    @Qualifier("studentServiceImpl")
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @Autowired
    public void setCredentialsService(StatelessCredentialsService credentialsService)
    {
        this.credentialsService = credentialsService;
    }

    @Before
    public void setUp()
        throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Method: testRegister()
     */
    @Test
    public void testRegister()
        throws Exception
    {

    }

    @Before
    public void before()
        throws Exception
    {
        generateTestingStudents();
    }

    private void generateTestingStudents() {
        studentService.deleteAll();
        for(int i =0;i<20;i++) {
        Student student = new Student();
        FavoriteStatus favoriteStatus = new FavoriteStatus();
        favoriteStatus.setFavoriteId(1);
        student.setUserName("qq313700046@icloud.com");
        student.setStudentId(UUID.randomUUID().toString().substring(0, 16));
        student.setPassword("liuxiangde");
        student.setStudentName(UUID.randomUUID().toString().substring(0, 8));
        student.setFavorite(favoriteStatus);
        studentService.registerUser(student);
        }
    }

    @After
    public void after()
        throws Exception
    {}

    /**
     * Method: studentList()
     */
    @Test
    public void testStudentList()
        throws Exception
    {
    }

    /**
     * Method: testStudentListPageable()
     */
    @Test
    public void testStudentListPageable()
        throws Exception
    {
        String url = "/students";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("size", "10");
        params.add("page", "1");
        params.add("sort", "studentId");
        mockMvc.perform(get(url).params(params).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(
                print());
    }

    @Test
    public void create()
        throws Exception
    {
        studentService.deleteByStudentId("915106840327");
        String url = "/students";
        Student student = new Student();
        student.setUserName("qq313700046@icloud.com");
        student.setStudentId("915106840327");
        student.setPassword("liuxiangde");
        student.setStudentName("刘祥德");
        String jsonString = mapper.writeValueAsString(student);
        mockMvc.perform(post(url)
                .content(jsonString.getBytes())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void update()
        throws Exception
    {
        String url = "/students";
        StudentDTO studentDTO = studentService.getStudentById("915106840327");
        studentDTO.setClassName("软工二班");
        String jsonString = mapper.writeValueAsString(studentDTO);
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString.getBytes())).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void deleteStudent()
            throws Exception
    {
        List<String> studentIds = studentService.getStudentIds();
        String url = "/students";
        mockMvc.perform(delete(url + "/" + studentIds.get(0)).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andDo(print());
    }
    @Test
    public void markStudent()
        throws Exception
    {
    }

    @Test
    public void unMarkStudent()
        throws Exception
    {

    }

    @Test
    public void deleteBatch()
        throws Exception
    {
        List<String> studentIds = studentService.getStudentIds();
        List<String> subStudentIdS = studentIds.subList(0, 5);
        String jsonString = mapper.writeValueAsString(subStudentIdS);
        String url = "/students/deleteWithIdArray";
        mockMvc.perform(delete(url).content(jsonString)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andDo(print());
    }
}
