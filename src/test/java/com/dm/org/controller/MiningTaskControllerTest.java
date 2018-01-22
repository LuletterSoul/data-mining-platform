package com.dm.org.controller;

import com.dm.org.base.ConfigurationWirer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public class MiningTaskControllerTest extends ConfigurationWirer{

    private MockMvc mockMvc;

    private MiningTaskService taskService;

    private AlgorithmService algorithmService;

    private DataSetContainerService containerService;

    private GroupService groupService;

    private StudentService studentService;

    private AlgorithmTypeService algorithmTypeService;

    private List<String> algorithmIds = new LinkedList<String>();

    private List<String> containerIds = new LinkedList<String>();

    private List<String> groupIds = new LinkedList<String>();

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setTaskService(MiningTaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setAlgorithmService(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @Autowired
    public void setContainerService(DataSetContainerService containerService) {
        this.containerService = containerService;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setAlgorithmTypeService(AlgorithmTypeService algorithmTypeService) {
        this.algorithmTypeService = algorithmTypeService;
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void deleteById() throws Exception {

    }

    @Test
    public void getList() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void create() throws Exception {
        Date date = new Date();
        Timestamp setUpDate = new Timestamp(date.getTime());

        //构建模拟的数据挖掘任务
        DataMiningTask miningTask = new DataMiningTask();
        miningTask.setTaskName("基于C4.5算法的研究与PRSA数据集的分类分析统计");
//        miningTask.setStartTime(setUpDate);

        String serializedMiningTask = objectMapper.writeValueAsString(miningTask);
        //模拟管理员提交实践任务的申请通知
        MvcResult result =mockMvc.perform(post("/tasks")
                .content(serializedMiningTask)
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated()).andDo(print()).andReturn();

        String createdTaskJsonString = result.getResponse().getContentAsString();
        DataMiningTask createdTask = objectMapper.readValue(createdTaskJsonString, DataMiningTask.class);
        String taskId = createdTask.getTaskId();


        //构建模拟的数据挖掘分组
        DataMiningGroup group = new DataMiningGroup();
        group.setGroupName("谁念西风独自凉");
        group.setSetUpDate(setUpDate);
        groupService.save(group);
        groupIds.add(group.getGroupId());

        //生成组员信息
        for(int i =0;i<10;i++) {
            Student student = new Student();
            student.setUsername("有追求的继承者们"+UUID.randomUUID().toString().substring(0,4));
            student.setStudentId(UUID.randomUUID().toString().substring(0, 16));
            student.setPassword("liuxiangde");
            student.setStudentName(UUID.randomUUID().toString().substring(0, 8));
            studentService.registerUser(student);
        }
        //模拟添加组员的客户端请求
        List<String> studentIds = studentService.getStudentIds();
        String serializedStudentIds = objectMapper.writeValueAsString(studentIds);
        mockMvc.perform(post("/groups/"+group.getGroupId()+"/groupMembers/addMembersWithArray").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(serializedStudentIds)).andExpect(status().isCreated()).andDo(print());

        //模拟设置组长的客户端操作
        String leaderId = studentIds.get(0);
        mockMvc.perform(patch("/groups/"+group.getGroupId()+"/leader").content(leaderId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print());

        //构建模拟的算法描述
        AlgorithmType algorithmType = new AlgorithmType("分类", "Classification");
        algorithmTypeService.save(algorithmType);
        Algorithm algorithm = new Algorithm("C4.5", "机器学习算法中的一种分类决策树算法,其核心算法是ID3算法", algorithmType);
        algorithmService.save(algorithm);
        algorithmIds.add(algorithm.getAlgorithmId());

        //模拟管理员给实践任务分配算法操作
        String serializedAlgorithmIds = objectMapper.writeValueAsString(algorithmIds);
        mockMvc.perform(post("/tasks/" + taskId + "/algorithms/configWithArray")
                .content(serializedAlgorithmIds).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated()).andDo(print());

        //模拟管理员给时间任务分配相应的挖掘数据
        List<String> containers = containerService.getContainerIds();
        String jSonContainerIds = objectMapper.writeValueAsString(containers);
        mockMvc.perform(post("/tasks/" + taskId + "/containers/addSetsWithArray")
                .content(jSonContainerIds)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated()).andDo(print());

        //模拟管理员给任务分配分配团队
        String jsonGroupIds = objectMapper.writeValueAsString(groupIds);
        mockMvc.perform(post("/tasks/"+taskId+"/groups/involveWithArray")
                .content(jsonGroupIds)
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void groups() throws Exception {

    }

    @Test
    public void getInvolvedGroup() throws Exception {

    }

    @Test
    public void removeGroup() throws Exception {

    }

    @Test
    public void involveGroup() throws Exception {

    }

    @Test
    public void involveGroups() throws Exception {

    }

    @Test
    public void removeGroups() throws Exception {

    }

    @Test
    public void getAlgorithms() throws Exception {

    }

    @Test
    public void configAlgorithms() throws Exception {

    }

    @Test
    public void configAlgorithm() throws Exception {

    }

    @Test
    public void cancelAlgorithm() throws Exception {

    }

    @Test
    public void cancelAlgorithms() throws Exception {

    }

    @Test
    public void arrangeMiningSet() throws Exception {

    }

    @Test
    public void arrangeMiningSets() throws Exception {

    }

    @Test
    public void getInvolvedSets() throws Exception {

    }

    @Test
    public void deleteAllRelContainers() throws Exception {

    }

    @Test
    public void deleteContainersWithArray() throws Exception {

    }

}