package com.dm.org.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dm.org.base.ConfigurationWirer;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetCollectionService;
import com.dm.org.service.DataSetContainerService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * DataSetContainerController Tester.
 *
 * @author XiangDe Liu qq313700046@icloud.com
 * @version 1.0
 * @since <pre>
 * ���� 20, 2017
 *        </pre>
 */
public class DataSetContainerControllerTest extends ConfigurationWirer {

    private DataSetContainerService containerService;

    private DataSetCollectionService collectionService;

    private String baseUrl = "/dataSetContainer";

    private MockMvc mockMvc;

    @Autowired

    public void setContainerService(DataSetContainerService containerService) {
        this.containerService = containerService;
    }

    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Before
    public void before()
            throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void after()
            throws Exception {
    }

    @Test
    public void generateTestContainer() {
        Set<DataSetContainer> containers = new LinkedHashSet<DataSetContainer>();
        for (int i = 0; i < 50; i++) {
            containers.add(DataSetCollectionControllerTest.buildContainer());
        }
        containerService.saveBatch(containers);
    }

    /**
     * Method: delete(@PathVariable("containerId") String containerId)
     */
    @Test
    public void testDelete()
            throws Exception {
        String containerId = fetchContainerTestCase();

        mockMvc.perform(delete(baseUrl + "/" + containerId).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    private String fetchContainerTestCase() {
        //先根据当前存在测试数据删除;
        List<String> names = containerService.getContainerFileNames();
        String queryName = names.get(0);
        //顺带测试了一个简单的查询方法
        DataSetContainer dataSetContainer = containerService.getContainerByFileName(queryName);
        return dataSetContainer.getContainerId();
    }

    /**
     * Method: getList(@PageableDefault Pageable pageable)
     */
    @Test
    public void testGetList()
            throws Exception {
        mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andDo(print());
    }

    /**
     * Method: update(@RequestBody DataSetContainer setContainer)
     */
    @Test
    public void testUpdate()
            throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: create(@RequestBody DataSetContainer setContainer)
     */
    @Test
    public void testCreate()
            throws Exception {

    }

    /**
     * Method: uploadFile(@RequestPart(name = "file") MultipartFile
     * file, @PathVariable("containerId") String containerId)
     */
    @Test
    public void testUploadFile()
            throws Exception {
        String containerId = fetchContainerTestCase();
        File file = new File("C:\\Users\\31370\\Desktop\\科研训练 weka\\set1.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file",file.getName(),
                                                                    MediaType.MULTIPART_FORM_DATA_VALUE,
                                                                    fileInputStream);
        mockMvc.perform(MockMvcRequestBuilders
                .fileUpload(baseUrl + "/" + containerId + "/uploadSetData")
                .file(mockMultipartFile1))
                .andDo(print());
    }

    /**
     * Method: getCollection(@PathVariable("containerId") String containerId)
     */
    @Test
    public void testGetCollection()
            throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: fileLink(@PathVariable("containerId") String collectionId)
     */
    @Test
    public void testFileLink()
            throws Exception {
        String containerId = fetchContainerTestCase();
        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "/" + containerId + "/filePath")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andDo(print());
    }

    /**
     * Method: downloadFile()
     */
    @Test
    public void testDownloadFile()
                throws Exception
    {
        String containerId = fetchContainerTestCase();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "/" + containerId + "/filePath")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andDo(print()).andReturn();
        String filePath = mvcResult.getResponse().getContentAsString();
        mockMvc.perform(get(baseUrl + "/" + containerId)
                .param("filePath",filePath)
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andDo(print());

    }

}
