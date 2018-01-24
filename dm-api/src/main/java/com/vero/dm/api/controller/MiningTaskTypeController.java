package com.vero.dm.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.model.AssociatedTask;
import com.vero.dm.service.MiningTaskTypeService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION + "/miningTaskTypes")
public class MiningTaskTypeController
{

    private MiningTaskTypeService miningTaskTypeService;

    @Autowired
    public void setMiningTaskTypeService(MiningTaskTypeService miningTaskTypeService)
    {
        this.miningTaskTypeService = miningTaskTypeService;
    }

    @GetMapping
    List<AssociatedTask> getOptionnalMiningTaskTypes()
    {
        return miningTaskTypeService.findAll();
    }
}
