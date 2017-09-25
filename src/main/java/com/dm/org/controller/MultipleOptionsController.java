package com.dm.org.controller;

import com.dm.org.model.AreaType;
import com.dm.org.model.AttributeType;
import com.dm.org.model.DataSetCharacteristic;
import com.dm.org.model.MiningTaskType;
import com.dm.org.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@RestController
@RequestMapping(value = "/options")
public class MultipleOptionsController {
    private DataSetCollectionService collectionService;

    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, List<?>> getOptions() {
        return collectionService.getOptions();
    }
}
