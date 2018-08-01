package com.vero.dm.web.controllers;


import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.JavaType;
import com.vero.dm.api.controller.ApiVersion;
import com.vero.dm.model.*;
import com.vero.dm.repository.dto.DataMiningGroupDto;
import com.vero.dm.repository.dto.MiningTaskDto;
import com.vero.dm.service.*;
import com.vero.dm.service.constant.ResourcePath;
import com.vero.dm.web.base.ConfigurationWirer;


/**
 * StageController Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * ���� 28, 2018
 *        </pre>
 * 
 * @version 1.0
 */

public class StageControllerTest extends ConfigurationWirer
{

    private StudentService studentService;

    private MiningTaskService taskService;

    private GroupService groupService;

    private MiningTaskStageService stageService;

    private MiningResultService resultService;

    private List<Student> students = new ArrayList<>();

    private List<String> studentIds = new ArrayList<>();

    private DataMiningTask createdTask;

    @Autowired
    public void setGroupService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @Autowired
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @Autowired
    public void setTaskService(MiningTaskService taskService)
    {
        this.taskService = taskService;
    }

    @Autowired
    public void setStageService(MiningTaskStageService stageService)
    {
        this.stageService = stageService;
    }

    @Autowired
    public void setResultService(MiningResultService resultService)
    {
        this.resultService = resultService;
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
     * Method: setResultService(MiningResultService resultService)
     */

    /**
     * Method: createStage(@RequestBody MiningTaskStage stage)
     */
    @Test
    public void testCreateStage()
        throws Exception
    {
        MiningTaskDto task = new MiningTaskDto();
        task.setTaskName("挖掘结果的依附任务");
        task.setPlannedTimeRange(new Date[] {new Date(), new Date()});
        DataMiningTask createdTask = taskService.saveOrUpdateMiningTask(task);
        List<MiningTaskStage> stages = new ArrayList<>();
        // 创建三个阶段
        for (int i = 1; i <= 3; i++ )
        {
            MiningTaskStage stage = new MiningTaskStage();
            stage.setComment("测试阶段" + i);
            stage.setOrderId(i);
            stage.setTask(createdTask);
            stage.setBegin(new Date());
            stage.setEnd(new Date());
            String stageString = objectMapper.writeValueAsString(stage);
            String str = mockMvc.perform(
                post(ApiVersion.API_VERSION.concat(ResourcePath.STAGE_PATH)).content(
                    stageString).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        status().isCreated()).andDo(
                            print()).andReturn().getResponse().getContentAsString();
            MiningTaskStage created = objectMapper.readValue(str, MiningTaskStage.class);
            Assert.assertNotNull(created);
            Assert.assertNotNull(created.getStageId());
            Assert.assertEquals(created.getTask().getTaskId(), createdTask.getTaskId());
            stages.add(created);
        }
    }

    private void buildCandidates(Integer candidateSize)
    {
        // 生成待分组信息
        for (int i = 0; i < candidateSize; i++ )
        {
            Student student = new Student();
            student.setUsername("有追求的继承者们" + i);
            student.setStudentId(UUID.randomUUID().toString().substring(0, 16));
            student.setPassword("liuxiangde");
            student.setStudentName(UUID.randomUUID().toString().substring(0, 8));
            studentService.save(student);
            students.add(student);
            studentIds.add(student.getStudentId());
        }
    }

    // 获取特定阶段的全部挖掘结果
    @Test
    public void testGetAllResults()
        throws Exception
    {
        MiningTaskDto task = new MiningTaskDto();
        task.setTaskName("挖掘结果的依附任务");
        task.setPlannedTimeRange(new Date[] {new Date(), new Date()});
        DataMiningTask createdTask = taskService.saveOrUpdateMiningTask(task);
        List<MiningTaskStage> stages = new ArrayList<>();

        // 创建三个阶段
        for (int i = 1; i <= 3; i++ )
        {
            MiningTaskStage stage = new MiningTaskStage();
            stage.setComment("测试阶段" + i);
            stage.setOrderId(i);
            stage.setTask(createdTask);
            stage.setBegin(new Date());
            stage.setEnd(new Date());
            String stageString = objectMapper.writeValueAsString(stage);
            String str = mockMvc.perform(
                post(ApiVersion.API_VERSION.concat(ResourcePath.STAGE_PATH)).content(
                    stageString).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        status().isCreated()).andDo(
                            print()).andReturn().getResponse().getContentAsString();
            MiningTaskStage created = objectMapper.readValue(str, MiningTaskStage.class);
            Assert.assertNotNull(created);
            Assert.assertNotNull(created.getStageId());
            Assert.assertEquals(created.getTask().getTaskId(), createdTask.getTaskId());
            stages.add(created);
        }
        buildCandidates(20);
        DataMiningGroupDto groupDto = new DataMiningGroupDto();
        groupDto.setMemberIds(studentIds);
        groupDto.setGroupName("下载记录测试分组");
        groupDto.setDataMiningTask(createdTask);
        groupDto.setBuilderId(students.get(0).getUserId());
        groupDto.setLeaderId(students.get(0).getStudentId());
        groupDto.setGroupLeader(students.get(0));
        groupDto.setTaskId(createdTask.getTaskId());
        groupDto.setArrangementId("1321412");
        groupDto.setTaskStatus(1);
        DataMiningGroup group = groupService.createGroup(groupDto);
        Integer stageId = stages.get(0).getStageId();
        String reqUrl = ApiVersion.API_VERSION.concat(ResourcePath.STAGE_PATH).concat("/").concat(
            String.valueOf(stageId).concat("/findResultByRecords"));

        // 获取数据挖掘结果
        String resultStr = mockMvc.perform(
            get(reqUrl).contentType(MediaType.APPLICATION_JSON_UTF8).param("all",
                "true")).andExpect(status().isOk()).andDo(print()).andExpect(
                    jsonPath("$.content").exists()).andReturn().getResponse().getContentAsString();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
            MiningResult.class);
        JSONObject jsonObject = new JSONObject(resultStr);
        // 获取分页之后的内容
        List<MiningResult> results = objectMapper.readValue(jsonObject.get("content").toString(),
            javaType);
        // 每个学生必定有一条记录
        Assert.assertEquals(students.size(), results.size());
        List<String> ids = results.stream().map(r -> r.getSubmitter().getStudentId()).collect(
            Collectors.toList());
        Assert.assertTrue(ids.containsAll(studentIds));
        // studentService.deleteBatchByStudentIds(studentIds);
        groupService.deleteMiningGroupById(group.getGroupId());
    }

    // 获取特定学生的挖掘结果
    @Test
    public void testGetSpecializedSubmitterResults()
        throws Exception
    {
        MiningTaskDto task = new MiningTaskDto();
        task.setTaskName("挖掘结果的依附任务");
        task.setPlannedTimeRange(new Date[] {new Date(), new Date()});
        DataMiningTask createdTask = taskService.saveOrUpdateMiningTask(task);
        List<MiningTaskStage> stages = new ArrayList<>();

        // 创建三个阶段
        for (int i = 1; i <= 3; i++ )
        {
            MiningTaskStage stage = new MiningTaskStage();
            stage.setComment("测试阶段" + i);
            stage.setOrderId(i);
            stage.setTask(createdTask);
            stage.setBegin(new Date());
            stage.setEnd(new Date());
            String stageString = objectMapper.writeValueAsString(stage);
            String str = mockMvc.perform(
                post(ApiVersion.API_VERSION.concat(ResourcePath.STAGE_PATH)).content(
                    stageString).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        status().isCreated()).andDo(
                            print()).andReturn().getResponse().getContentAsString();
            MiningTaskStage created = objectMapper.readValue(str, MiningTaskStage.class);
            Assert.assertNotNull(created);
            Assert.assertNotNull(created.getStageId());
            Assert.assertEquals(created.getTask().getTaskId(), createdTask.getTaskId());
            stages.add(created);
        }
        buildCandidates(20);
        DataMiningGroupDto groupDto = new DataMiningGroupDto();
        groupDto.setMemberIds(studentIds);
        groupDto.setGroupName("下载记录测试分组");
        groupDto.setDataMiningTask(createdTask);
        groupDto.setBuilderId(students.get(0).getUserId());
        groupDto.setLeaderId(students.get(0).getStudentId());
        groupDto.setGroupLeader(students.get(0));
        groupDto.setTaskId(createdTask.getTaskId());
        groupDto.setArrangementId("1321412");
        groupDto.setTaskStatus(1);
        DataMiningGroup group = groupService.createGroup(groupDto);
        Integer stageId = stages.get(0).getStageId();
        String reqUrl = ApiVersion.API_VERSION.concat(ResourcePath.STAGE_PATH).concat("/").concat(
            String.valueOf(stageId).concat("/results"));

        // 获取数据挖掘结果
        String resultStr = mockMvc.perform(
            get(reqUrl).contentType(MediaType.APPLICATION_JSON_UTF8).param(
                "submitterId", students.get(0).getUserId())).andExpect(status().isOk()).andDo(
                    print()).andExpect(
                        jsonPath(
                            "$.content").exists()).andReturn().getResponse().getContentAsString();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
            MiningResult.class);
        JSONObject jsonObject = new JSONObject(resultStr);


        // 获取分页之后的内容
        List<MiningResult> results = objectMapper.readValue(jsonObject.get("content").toString(),
            javaType);


        // 每个学生必定有一条记录
        Assert.assertEquals(1, results.size());
        List<String> ids = results.stream().map(r -> r.getSubmitter().getStudentId()).collect(
            Collectors.toList());
        Assert.assertTrue(ids.contains(students.get(0).getStudentId()));
        // studentService.deleteBatchByStudentIds(studentIds);
        groupService.deleteMiningGroupById(group.getGroupId());
    }

}
