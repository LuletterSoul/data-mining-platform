package com.dm.org.controller;

import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@RestController
@RequestMapping(value = "/dataSetContainer",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                                            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataSetContainerController {
    private DataSetContainerService containerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSetCollectionController.class);

    @Autowired
    public void setContainerService(DataSetContainerService containerService) {
        this.containerService = containerService;
    }

    @RequestMapping(value = "/{collectionId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataSetContainer> delete(@PathVariable("collectionId") String collectionId) {
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public DataSetContainer update(@RequestBody DataSetContainer setContainer) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataSetContainer> create(@RequestBody DataSetContainer setContainer) {
        return null;
    }

    @RequestMapping(value = "/{containerId}/uploadSetData", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestPart(name = "file") MultipartFile file, @PathVariable("containerId") String containerId) {
        return null;
    }

}
