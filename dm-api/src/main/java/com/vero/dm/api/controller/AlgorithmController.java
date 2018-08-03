package com.vero.dm.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.model.Algorithm;
import com.vero.dm.service.AlgorithmService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiOperation;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION
                        + ResourcePath.ALGORITHM_PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AlgorithmController
{
    private AlgorithmService algorithmService;

    @Autowired
    public void setAlgorithmService(AlgorithmService algorithmService)
    {
        this.algorithmService = algorithmService;
    }

    @ApiOperation("查找所有算法名称")
    @GetMapping
    public List<Algorithm> get()
    {
        return algorithmService.findAll();
    }
}
