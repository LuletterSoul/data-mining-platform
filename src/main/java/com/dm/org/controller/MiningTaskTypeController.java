package com.dm.org.controller;

import com.dm.org.dao.impl.MiningTaskTypeDao;
import com.dm.org.model.MiningTaskType;
import com.dm.org.service.MiningTaskService;
import com.dm.org.service.MiningTaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@RestController
@RequestMapping(value = "/miningTaskTypes")
public class MiningTaskTypeController {

    private MiningTaskTypeService miningTaskTypeService;

    @Autowired
    public void setMiningTaskTypeService(MiningTaskTypeService miningTaskTypeService) {
        this.miningTaskTypeService = miningTaskTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<MiningTaskType> getOptionnalMiningTaskTypes() {
        return miningTaskTypeService.findAll();
    }
}
