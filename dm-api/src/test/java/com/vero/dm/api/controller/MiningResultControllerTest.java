package com.vero.dm.api.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vero.dm.api.base.ConfigurationWirer;
import com.vero.dm.model.*;
import com.vero.dm.model.enums.ResultState;
import com.vero.dm.repository.dto.MiningTaskDto;
import com.vero.dm.service.MiningTaskService;
import com.vero.dm.service.MiningTaskStageService;
import com.vero.dm.service.StudentService;
import com.vero.dm.service.constant.ResourcePath;




/**
 * MiningResultController Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * ���� 25, 2018
 *        </pre>
 * 
 * @version 1.0
 */

@AutoConfigureMockMvc
@Rollback
@Transactional
public class MiningResultControllerTest extends ConfigurationWirer
{

    private StudentService studentService;

    private MiningTaskService taskService;

    private MiningTaskStageService stageService;

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

    @Before
    public void before()
        throws Exception
    {}

    @After
    public void after()
        throws Exception
    {}

    /**
     * Method: createResult(@RequestBody MiningResult result)
     */
    @Test
    public void testCreateResult()
        throws Exception
    {
        MiningTaskStage stage = new MiningTaskStage();
        stage.setComment("测试阶段1");
        stage.setOrderId(1);
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
        // TODO: Test goes here...
    }

    /**
     * Method: uploadResult(@RequestPart MultipartFile resultFile, @PathVariable("resultId")
     * Integer resultId)
     */
    @Test
    public void testUploadResult()
        throws Exception
    {
        //保证库内有一个结果上传者的数据
        Student submitter = new Student();
        submitter.setUsername("测试的祥德");
        submitter.setStudentId("915103247328");
        submitter.setStudentName("测试的祥德");
        studentService.save(submitter);

        MiningTaskDto task = new MiningTaskDto();
        task.setTaskName("挖掘结果的依附任务");
        task.setPlannedTimeRange(new Date[]{new Date(), new Date()});
        DataMiningTask createdTask =  taskService.saveOrUpdateMiningTask(task);

        // 创建一个阶段
        MiningTaskStage stage = new MiningTaskStage();
        stage.setComment("测试阶段1");
        stage.setOrderId(1);
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

        // 创建一个挖掘结果
        MiningResult result = new MiningResult();
        result.setStage(created);
        result.setState(ResultState.noResult);
        result.setSubmitter(submitter);
        String resultStr = mockMvc.perform(
            post(ApiVersion.API_VERSION.concat(ResourcePath.RESULT_PATH)).content(
                objectMapper.writeValueAsString(result)).contentType(
                    MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated()).andDo(
                        print()).andReturn().getResponse().getContentAsString();
        MiningResult createdRes = objectMapper.readValue(resultStr, MiningResult.class);
        Assert.assertNotNull(createdRes);
        Assert.assertNotNull(createdRes.getResultId());

        URL f1 = Thread.currentThread().getContextClassLoader().getResource("test1.txt");
        File testFile = new File(Objects.requireNonNull(f1).getFile());
        MockMultipartFile firstFile = new MockMultipartFile("resultFile",testFile.getName(),MediaType.MULTIPART_FORM_DATA_VALUE ,new FileInputStream(testFile));
        String uploadAddr = ApiVersion.API_VERSION.concat(ResourcePath.RESULT_PATH).concat(
            "/").concat(String.valueOf(createdRes.getResultId()).concat("/records"));
        //发起上传请求，将Multipart file 映射到键为'resultFile'的fileMap中，否则会抛出Bad Request 400异常
        String recordStr = mockMvc.perform(
            MockMvcRequestBuilders.fileUpload(uploadAddr).file(firstFile).contentType(
                MediaType.MULTIPART_FORM_DATA_VALUE)).andDo(print()).andExpect(status().isCreated()).andDo(
                    print()).andReturn().getResponse().getContentAsString();
        ResultRecord resultRecord = objectMapper.readValue(recordStr, ResultRecord.class);
        System.out.println("Record Path:" + resultRecord.getPath());
    }

}
