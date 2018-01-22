package com.vero.dm.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.service.DataSetCollectionService;

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
