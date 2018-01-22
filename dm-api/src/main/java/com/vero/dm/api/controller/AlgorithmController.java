package com.vero.dm.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.model.Algorithm;
import com.vero.dm.service.AlgorithmService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = "/algorithms", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AlgorithmController
{

    private AlgorithmService algorithmService;

    @Autowired
    public void setAlgorithmService(AlgorithmService algorithmService)
    {
        this.algorithmService = algorithmService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Algorithm> get()
    {
        return algorithmService.findAll();
    }
}
