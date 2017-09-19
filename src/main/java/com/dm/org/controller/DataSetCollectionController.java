package com.dm.org.controller;

import com.dm.org.model.DataSetCollection;
import com.dm.org.service.DataSetCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */


@RestController
@RequestMapping(value = "/dataSet"
        ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE
        ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataSetCollectionController
{
    private DataSetCollectionService collectionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSetCollectionController.class);

    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @RequestMapping(value = "/{collectionName}", method = RequestMethod.GET)
    public DataSetCollection get(@PathVariable("collectionName") String collectionName) {
        return null;
    }

    @RequestMapping(value = "{collectionId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataSetCollection> delete(@PathVariable("collectionId") String collectionId) {
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public DataSetCollection update(@RequestBody DataSetCollection setCollection) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataSetCollection> create(@RequestBody DataSetCollection setCollection) {
        return null;
    }

}
