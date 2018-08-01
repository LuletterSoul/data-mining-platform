package com.vero.dm.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.JavaType;
import com.vero.dm.api.controller.ApiVersion;
import com.vero.dm.exception.error.ErrorInfo;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.MiningTaskStage;
import com.vero.dm.model.Student;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.GroupingConfigParams;
import com.vero.dm.repository.dto.MiningTaskDto;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.service.AlgorithmTypeService;
import com.vero.dm.service.StudentService;
import com.vero.dm.web.base.ConfigurationWirer;

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

    @Autowired
    private StudentService studentService;

    @Autowired
    private AlgorithmTypeService algorithmTypeService;

    GroupingConfigParams params = new GroupingConfigParams();

    private Set<MiningTaskStage> stages = new HashSet<>();

    private List<String> taskIds = new ArrayList<>();

    private List<String> candidateIds = new LinkedList<>();

    private List<Student> students = new ArrayList<>();


    @Test
    public void simpleGroupingStrategyTest() throws Exception {
        reset();
        Integer lowBound = 1;
        Integer upperBound = 14;
        Integer taskSize = 5;
        Integer candidateSize = 100;
        Integer strategyId = 1;
        MiningTaskStage stage1 = new MiningTaskStage();
        stage1.setBegin(new Date());
        stage1.setEnd(new Date());
        stages.add(stage1);
        buildTasks(taskSize);
        buildCandidates(candidateSize);
        //建立分组参数，选择策略为简单分组策略
        buildParams(lowBound,upperBound,strategyId);
        List<DataMiningGroup> createdGroups = createGroups(status().isCreated(),status().isOk());
        int size = createdGroups.size();
        Integer total = 0;
        total = totalMembers(createdGroups, size, total);
        Assert.assertEquals((long)total,(long) (lowBound + upperBound)/2 * taskSize);
    }

    @Test
    public void randomGroupingStrategyTest() throws Exception {
        reset();
        Integer lowBound = 1;
        Integer upperBound = 14;
        Integer taskSize = 5;
        Integer candidateSize = 100;
        Integer strategyId = 2;
        MiningTaskStage stage1 = new MiningTaskStage();
        stage1.setBegin(new Date());
        stage1.setEnd(new Date());
        stages.add(stage1);
        buildTasks(taskSize);
        buildCandidates(candidateSize);
        buildParams(lowBound,upperBound,strategyId);
        List<DataMiningGroup> createdGroups = createGroups(status().isCreated() ,status().isOk());
        int size = createdGroups.size();
        Integer total = 0;
        total = totalMembers(createdGroups, size, total);
//        Assert.assertEquals((long)total,(long) (lowBound + upperBound)/2 * taskSize);
        System.out.println("被分组的总学生人数为: "+ total);
    }

    @Test
    public void restrictGroupingStrategyExceptionTest() throws Exception {
        reset();
        Integer lowBound = 1;
        Integer upperBound = 14;
        Integer taskSize = 5;
        Integer candidateSize = 100;
        Integer strategyId = 3;
        MiningTaskStage stage1 = new MiningTaskStage();
        stage1.setBegin(new Date());
        stage1.setEnd(new Date());
        stages.add(stage1);
        buildTasks(taskSize);
        buildCandidates(candidateSize);
        buildParams(lowBound,upperBound,strategyId);
        String paramString = objectMapper.writeValueAsString(params);
        //测试预览分组接口
        String errorInfoStr =  mockMvc.perform(post(ApiVersion.API_VERSION.concat("/groups/dividing_groups"))
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(paramString))
                .andExpect(status().isBadRequest()).andDo(print()).andReturn().getResponse().getContentAsString();
        ErrorInfo errorInfo = objectMapper.readValue(errorInfoStr, ErrorInfo.class);
        Assert.assertNotNull(errorInfo);
        Assert.assertEquals(errorInfo.getErrorCode(), ExceptionCode.InvalidGroupingConfig.getCode());
        Integer total = 0;
    }

    @Test
    public void restrictGroupingStrategyTest() throws Exception {
        reset();
        Integer lowBound = 5;
        Integer upperBound = 25;
        Integer taskSize = 5;
        Integer candidateSize = 100;
        Integer strategyId = 3;
        MiningTaskStage stage1 = new MiningTaskStage();
        stage1.setBegin(new Date());
        stage1.setEnd(new Date());
        stages.add(stage1);
        buildTasks(taskSize);
        buildCandidates(candidateSize);
        buildParams(lowBound,upperBound,strategyId);
        List<DataMiningGroup> createdGroups = createGroups(status().isCreated(), status().isOk());
        int size = createdGroups.size();
        Integer total = 0;
        total = totalMembers(createdGroups, size, total);
        Assert.assertEquals((long)total,(long)candidateSize);
        createdGroups.forEach(g-> Assert.assertTrue(taskIds.contains(g.getDataMiningTask().getTaskId())));
    }

    private void reset() {
        stages.clear();
        taskIds.clear();
        candidateIds.clear();
        students.clear();
        params = new GroupingConfigParams();
    }

    private Integer totalMembers(List<DataMiningGroup> createdGroups, int size, Integer total) throws Exception {
        for (int i = 0; i < size; i++) {
            DataMiningGroup g = createdGroups.get(i);
            String studentStr = mockMvc.perform(get(ApiVersion.API_VERSION.concat("/groups").concat("/").concat(g.getGroupId()).concat("/members")))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
            JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, StudentDto.class);
            List<StudentDto> members = objectMapper.readValue(studentStr, javaType2);
            System.out.println("Group "+ i +" member scale is: "+members.size());
            total += members.size();
        }
        return total;
    }


    private List<DataMiningGroup> createGroups(ResultMatcher created, ResultMatcher preview) throws Exception {
        String paramString = objectMapper.writeValueAsString(params);
        //测试预览分组接口
        String groupInfo =  mockMvc.perform(post(ApiVersion.API_VERSION.concat("/groups/dividing_groups"))
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(paramString))
                .andExpect(preview).andDo(print()).andReturn().getResponse().getContentAsString();
        DividingGroupInfo dividingGroupInfo = objectMapper.readValue(groupInfo, DividingGroupInfo.class);
        Assert.assertNotNull(dividingGroupInfo);
        Assert.assertNotNull(dividingGroupInfo.getQueryKey());
        String newGroupsString = mockMvc.perform(post(ApiVersion.API_VERSION
                .concat("/groups/dividing_groups/")
                .concat(dividingGroupInfo.getQueryKey())))
                .andExpect(created)
                .andDo(print()).andReturn().getResponse().getContentAsString();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, DataMiningGroup.class);
        return objectMapper.readValue(newGroupsString, javaType);
    }

    private void buildParams(int lowerBound,int upperBound,int strategyId) {
        params = new GroupingConfigParams();
        params.setStrategyId(strategyId);
        params.setBeginDate(new Date());
        params.setBuildingKey("123");
        params.setGradient(12);
        params.setBuilderId(students.get(0).getUserId());
        params.setEndDate(new Date());
        params.setLowerBound(lowerBound);
        params.setUpperBound(upperBound);
        params.setSpecifiedTasks(taskIds);
        params.setSpecifiedDividingStudents(candidateIds);
    }

    private void buildCandidates(Integer candidateSize) {
        //生成待分组信息
        for(int i =0;i<candidateSize;i++) {
            Student student = new Student();
            student.setUsername("有追求的继承者们"+i);
            student.setStudentId(UUID.randomUUID().toString().substring(0, 16));
            student.setPassword("liuxiangde");
            student.setStudentName(UUID.randomUUID().toString().substring(0, 8));
            studentService.save(student);
            students.add(student);
            candidateIds.add(student.getStudentId());
        }
    }

    private void buildTasks(int taskSize) throws Exception {
        for(int i = 0;i<taskSize;i++) {
            //构建模拟的数据挖掘任务
            MiningTaskDto miningTask = buildMiningTaskDto(i);
            String serializedMiningTask = objectMapper.writeValueAsString(miningTask);
            //模拟管理员創建实践任务
            MvcResult result =mockMvc.perform(post(ApiVersion.API_VERSION.concat("/tasks"))
                    .content(serializedMiningTask)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated()).andDo(print()).andReturn();
            String createdTaskJsonString = result.getResponse().getContentAsString();
            DataMiningTask createdTask = objectMapper.readValue(createdTaskJsonString, DataMiningTask.class);
            taskIds.add(createdTask.getTaskId());
        }
    }

    private MiningTaskDto buildMiningTaskDto(int i) {
        MiningTaskDto miningTask = new MiningTaskDto();
        miningTask.setPlannedTimeRange(new Date[]{new Date(),new Date()});
        miningTask.setArrangementId("1");
        miningTask.setTaskName("基于C4.5算法的研究与PRSA数据集的分类分析统计" + i);
        //miningTask.setStages(stages);
        return miningTask;
    }
}