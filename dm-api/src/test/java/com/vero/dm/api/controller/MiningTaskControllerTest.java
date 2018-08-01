package com.vero.dm.api.controller;


import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vero.dm.repository.dto.MiningResultDto;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JavaType;
import com.vero.dm.api.base.ConfigurationWirer;
import com.vero.dm.model.*;
import com.vero.dm.repository.dto.DataMiningGroupDto;
import com.vero.dm.repository.dto.MiningTaskDto;
import com.vero.dm.service.*;
import com.vero.dm.service.constant.ResourcePath;


/**
 * MiningTaskController Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * ���� 31, 2018
 *        </pre>
 * 
 * @version 1.0
 */
public class MiningTaskControllerTest extends ConfigurationWirer
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

    @Test
    public void testGetResultRecord()
        throws Exception
    {
        // 创建一个任务
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

        // 分组预备人员
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
        String reqUrl = ApiVersion.API_VERSION.concat(ResourcePath.RESULT_PATH);

        // 获取标识为submitterId的用户上传在任务号为taskId任务下的数据挖掘结果
        String resultStr = mockMvc.perform(
            get(reqUrl).contentType(MediaType.APPLICATION_JSON_UTF8).param("submitterIds",
                students.get(0).getUserId()).param("taskId", createdTask.getTaskId())).andDo(
                    print()).andExpect(status().isOk()).andExpect(
                        jsonPath(
                            "$.content").exists()).andReturn().getResponse().getContentAsString();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
            MiningResultDto.class);
        JSONObject jsonObject = new JSONObject(resultStr);
        // 获取分页之后的内容
        List<MiningResultDto> results = objectMapper.readValue(jsonObject.get("content").toString(),
            javaType);
        // 每个学生必定有一条记录,三个阶段都有一个发掘结果
        Assert.assertEquals(3, results.size());
        List<String> ids = results.stream().map(MiningResultDto::getSubmitterId).collect(
            Collectors.toList());
        Assert.assertTrue(ids.contains(students.get(0).getStudentId()));
        // studentService.deleteBatchByStudentIds(studentIds);
        //groupService.deleteMiningGroupById(group.getGroupId());

        List<String> prePaths = new ArrayList<>();
        for(int i = 1;i<=3;i++){
            String testFileName1 = "test" + i + ".txt";
            String testFileName2 = "test" + (i + 1) + ".txt";
            Integer resultId = results.get(i - 1).getResultId();
            //每个阶段上传一条记录
            String recordStr1 = uploadResult(testFileName1, resultId);
            String recordStr2 = uploadResult(testFileName2, resultId);
            ResultRecord resStr1 = objectMapper.readValue(recordStr1, ResultRecord.class);
            ResultRecord resStr2 = objectMapper.readValue(recordStr2, ResultRecord.class);
            prePaths.add(resStr1.getPath());
            prePaths.add(resStr2.getPath());
        }

        String recordsReq = ApiVersion.API_VERSION + ResourcePath.TASK_PATH +"/"+ createdTask.getTaskId() + "/result_records";

        String recordStr =   mockMvc.perform(
                get(recordsReq).contentType(MediaType.APPLICATION_JSON_UTF8).param("submitterIds",students.get(0).getUserId())).andDo(
                print()).andExpect(status().isOk()).andExpect(
                jsonPath(
                        "$.content").exists()).andReturn().getResponse().getContentAsString();
        JavaType resultRecordType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
                ResultRecord.class);
        List<ResultRecord> records = objectMapper.readValue(new JSONObject(recordStr).get("content").toString(), resultRecordType);
        Assert.assertEquals(6, records.size());
        List<String> paths = records.stream().map(ResultRecord::getPath).collect(Collectors.toList());
        Assert.assertTrue(paths.containsAll(prePaths));
    }

    private String uploadResult(String testFileName, Integer resultId) throws Exception {
        URL f1 = Thread.currentThread().getContextClassLoader().getResource(testFileName);
        File testFile = new File(Objects.requireNonNull(f1).getFile());
        MockMultipartFile firstFile = new MockMultipartFile("resultFile", testFile.getName(),
            MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(testFile));
        String uploadAddr = ApiVersion.API_VERSION.concat(ResourcePath.RESULT_PATH).concat(
            "/").concat(String.valueOf(resultId).concat("/records"));
        // 发起上传请求，将Multipart file 映射到键为'resultFile'的fileMap中，否则会抛出Bad Request 400异常
        return mockMvc.perform(
            MockMvcRequestBuilders.fileUpload(uploadAddr).file(firstFile).contentType(
                MediaType.MULTIPART_FORM_DATA_VALUE)).andDo(print()).andExpect(
                    status().isCreated()).andDo(
                        print()).andReturn().getResponse().getContentAsString();
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

}
