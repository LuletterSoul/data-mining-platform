package com.vero.dm.api.controller;


import java.util.List;
import java.util.Map;

import com.vero.dm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.service.DataSetCollectionService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION + "/options")
public class MultipleOptionsController
{
    private DataSetCollectionService collectionService;

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService)
    {
        this.collectionService = collectionService;
    }

    @Cacheable(cacheNames = "dataSetCollectionOptionsCache")
    @GetMapping(value = "/data_set")
    public Map<String, List<?>> getDataSetOptions()
    {
        return collectionService.getOptions();
    }

    @GetMapping(value = "/student")
    public Map<String, List<?>> getStudentOptions()
    {
        return studentService.getStudentPropertiesOptions();
    }
}
