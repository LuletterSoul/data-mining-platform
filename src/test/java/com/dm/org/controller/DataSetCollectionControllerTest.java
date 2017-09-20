package com.dm.org.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dm.org.base.ConfigurationWirer;
import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetCollectionService;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public class DataSetCollectionControllerTest extends ConfigurationWirer
{

    private MockMvc mockMvc;

    private String baseUrl = "/dataSet";

    private DataSetCollectionService collectionService;

    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService)
    {
        this.collectionService = collectionService;
    }

    @Before
    public void setUp()
        throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void generateCollection()
        throws Exception
    {
//        collectionService.deleteAll();
//        Set<DataSetCollection> collections = new LinkedHashSet<DataSetCollection>();
//        for (int i = 0; i < 15; i++ )
//        {
//            DataSetCollection collection = buildTestCollection();
//            collections.add(collection);
//        }
//        collectionService.saveBatch(collections);
    }

    private DataSetCollection buildTestCollection() {
        DataSetCollection collection = new DataSetCollection();
        collection.setCollectionName(UUID.randomUUID().toString().substring(0, 8));
        collection.setAbstractInfo(UUID.randomUUID().toString());
        collection.setRelevantPapers(UUID.randomUUID().toString());
        return collection;
    }

    @Test
    public void getSet()
        throws Exception
    {
        List<String> collectionNames = collectionService.getCollectionNames();
        String queryName = collectionNames.get(0);
        mockMvc.perform(get(baseUrl + "/" + queryName)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void deleteSet()
        throws Exception
    {
        List<String> collectionNames = collectionService.getCollectionNames();
        String queryName = collectionNames.get(0);
        mockMvc.perform(delete(baseUrl + "/" + queryName)).andExpect(status().isNoContent()).andDo(print());
    }

    @Test
    public void update()
        throws Exception
    {
        List<String> collectionNames = collectionService.getCollectionNames();
        String queryName = collectionNames.get(0);
        DataSetCollection collection = collectionService.getCollectionByName(queryName);
        String jsonString = objectMapper.writeValueAsString(collection);
        collection.setCollectionName(UUID.randomUUID().toString());
        mockMvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
                                    .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void create()
        throws Exception
    {
        DataSetCollection collection = buildTestCollection();
        String jsonString = objectMapper.writeValueAsString(collection);
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonString)).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void addContainer()
        throws Exception
    {
        List<DataSetCollection> collections = collectionService.findAll();
        String collectionId = collections.get(0).getCollectionId();
        DataSetContainer container = buildContainer();
        String jsonString = objectMapper.writeValueAsString(container);
        mockMvc.perform(post(baseUrl + "/" + collectionId).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
                .andExpect(status().isCreated()).andDo(print());
    }

    public static DataSetContainer buildContainer() {
        DataSetContainer container = new DataSetContainer();
        container.setFileName(UUID.randomUUID().toString());
        container.setData(UUID.randomUUID().toString().getBytes());
        container.setFileType(UUID.randomUUID().toString());
        container.setInstances(1000L);
        return container;
    }

    @Test
    public void removeContainer()
        throws Exception
    {
        List<DataSetCollection> collections = collectionService.findAll();
        String collectionId = collections.get(0).getCollectionId();
        MvcResult mvcResult = mockMvc.perform(get(baseUrl + "/" + collectionId + "/container")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, DataSetContainer.class);
        List<DataSetContainer> containers =objectMapper.readValue(content, javaType);
        System.out.println(collections);
        String containerId = containers.get(0).getContainerId();
        mockMvc.perform(delete(baseUrl + "/" + collectionId + "/"+containerId)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

}