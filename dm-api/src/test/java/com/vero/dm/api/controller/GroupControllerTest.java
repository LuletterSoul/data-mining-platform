package com.vero.dm.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.vero.dm.api.base.ConfigurationWirer;
import com.vero.dm.model.*;
import com.vero.dm.repository.dto.*;
import com.vero.dm.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.security.Timestamp;
import java.util.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:38 2018/7/19.
 * @since data-mining-platform
 */

@AutoConfigureMockMvc
@Rollback
@Transactional
public class GroupControllerTest extends ConfigurationWirer {

    private String baseUrl;

    @Autowired
    private MiningTaskService taskService;

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private DataSetContainerService containerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AlgorithmTypeService algorithmTypeService;

    private List<Integer> algorithmIds = new LinkedList<Integer>();

    private List<String> containerIds = new LinkedList<String>();

    private List<String> groupIds = new LinkedList<String>();

    @Test
    public void previewDefaultGroupings() throws Exception {

        MiningTaskStage stage1 = new MiningTaskStage();
        stage1.setBegin(new Date());
        stage1.setEnd(new Date());

        //构建模拟的数据挖掘任务
        MiningTaskDto miningTask = new MiningTaskDto();
        miningTask.setPlannedTimeRange(new Date[]{new Date(),new Date()});
        miningTask.setArrangementId("1");
        miningTask.setTaskName("基于C4.5算法的研究与PRSA数据集的分类分析统计");


        String serializedMiningTask = objectMapper.writeValueAsString(miningTask);
        //模拟管理员創建实践任务
        MvcResult result =mockMvc.perform(post(ApiVersion.API_VERSION.concat("/tasks"))
                .content(serializedMiningTask)
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated()).andDo(print()).andReturn();
        String createdTaskJsonString = result.getResponse().getContentAsString();
        DataMiningTask createdTask = objectMapper.readValue(createdTaskJsonString, DataMiningTask.class);
        String taskId = createdTask.getTaskId();


        GroupingConfigParams params = new GroupingConfigParams();


//        //构建模拟的数据挖掘分组
//        DataMiningGroup group = new DataMiningGroup();
//        group.setGroupName("谁念西风独自凉");
//        groupService.save(group);
//        groupIds.add(group.getGroupId());

        List<String> candidateIds = new LinkedList<>();
        List<Student> students = new ArrayList<>();
        //生成待分组信息
        for(int i =0;i<100;i++) {
            Student student = new Student();
            student.setUsername("有追求的继承者们"+i);
            student.setStudentId(UUID.randomUUID().toString().substring(0, 16));
            student.setPassword("liuxiangde");
            student.setStudentName(UUID.randomUUID().toString().substring(0, 8));
            studentService.save(student);
            students.add(student);
            candidateIds.add(student.getStudentId());
        }
        params.setBeginDate(new Date());
        params.setBuildingKey("123");
        params.setGradient(12);
        params.setBuilderId(students.get(0).getUserId());
        params.setEndDate(new Date());
        params.setLowerBound(2);
        params.setUpperBound(10);
        params.setTaskId(taskId);
        params.setSpecifiedDividingStudents(candidateIds);

        String paramString = objectMapper.writeValueAsString(params);
        //测试预览分组接口
        String groupInfo =  mockMvc.perform(post(ApiVersion.API_VERSION.concat("/groups/dividing_groups"))
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(paramString))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        DividingGroupInfo dividingGroupInfo = objectMapper.readValue(groupInfo, DividingGroupInfo.class);
        Assert.assertNotNull(dividingGroupInfo);
        Assert.assertNotNull(dividingGroupInfo.getQueryKey());
      //  Assert.assertEquals(dividingGroupInfo.getQueryKey(), params.getBuildingKey());
       // dividingGroupInfo.getDataMiningGroups();
        //模拟确认创建预览分组的请求
        String newGroupsString = mockMvc.perform(post(ApiVersion.API_VERSION
                .concat("/groups/dividing_groups/")
                .concat(dividingGroupInfo.getQueryKey())))
                .andExpect(status().isCreated())
                .andDo(print()).andReturn().getResponse().getContentAsString();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, DataMiningGroup.class);
        List<DataMiningGroup> createdGroups = objectMapper.readValue(newGroupsString, javaType);
        int size = createdGroups.size();
        Integer total = 0;
        for (int i = 0; i < size; i++) {
            DataMiningGroup g = createdGroups.get(i);
            String studentStr = mockMvc.perform(get(ApiVersion.API_VERSION.concat("/groups").concat("/").concat(g.getGroupId()).concat("/members")))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
            JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, StudentDto.class);
            List<StudentDto> members = objectMapper.readValue(studentStr, javaType2);
            System.out.println("Group "+ i +" member scale is: "+members.size());
            total += members.size();
        }

        Assert.assertEquals((long)total,(long) candidateIds.size());

//        //模拟添加组员的客户端请求
//        List<String> studentIds = studentService.getStudentIds();
//        String serializedStudentIds = objectMapper.writeValueAsString(studentIds);
//        mockMvc.perform(post("/groups/"+group.getGroupId()+"/groupMembers/addMembersWithArray").contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(serializedStudentIds)).andExpect(status().isCreated()).andDo(print());


//        //构建模拟的算法描述
//        AlgorithmType algorithmType = new AlgorithmType("分类", "Classification");
//        algorithmTypeService.save(algorithmType);
//        Algorithm algorithm = new Algorithm("C4.5", "机器学习算法中的一种分类决策树算法,其核心算法是ID3算法", algorithmType);
//        algorithmService.save(algorithm);
//        algorithmIds.add(algorithm.getAlgorithmId());

//        //模拟管理员给实践任务分配算法操作
//        String serializedAlgorithmIds = objectMapper.writeValueAsString(algorithmIds);
//        mockMvc.perform(post("/tasks/" + taskId + "/algorithms/configWithArray")
//                .content(serializedAlgorithmIds).contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isCreated()).andDo(print());

//        //模拟管理员给时间任务分配相应的挖掘数据
//        List<String> containers = containerService.getContainerIds();
//        String jSonContainerIds = objectMapper.writeValueAsString(containers);
//        mockMvc.perform(post("/tasks/" + taskId + "/containers/addSetsWithArray")
//                .content(jSonContainerIds)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isCreated()).andDo(print());

//        //模拟管理员给任务分配分配团队
//        String jsonGroupIds = objectMapper.writeValueAsString(groupIds);
//        mockMvc.perform(post("/tasks/"+taskId+"/groups/involveWithArray")
//                .content(jsonGroupIds)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated()).andDo(print());
    }
}