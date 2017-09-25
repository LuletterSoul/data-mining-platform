package com.dm.org.controller;

import com.dm.org.model.DataSetCharacteristic;
import com.dm.org.service.CollectionCharService;
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
@RequestMapping(value = "/collectionChars")
public class CollectionCharacteristicController {

    private CollectionCharService collectionCharService;

    @Autowired
    public void setCollectionCharService(CollectionCharService collectionCharService) {
        this.collectionCharService = collectionCharService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DataSetCharacteristic> getOptionalList() {
        return collectionCharService.findAll();
    }
}
