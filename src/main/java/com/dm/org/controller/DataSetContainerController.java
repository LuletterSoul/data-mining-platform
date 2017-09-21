package com.dm.org.controller;


import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetContainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = "/dataSetContainers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataSetContainerController
{
    private DataSetContainerService containerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(
        DataSetCollectionController.class);

    @Autowired
    public void setContainerService(DataSetContainerService containerService)
    {
        this.containerService = containerService;
    }

    @RequestMapping(value = "/{containerId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataSetContainer> delete(@PathVariable("containerId") String containerId)
    {
        return new ResponseEntity<DataSetContainer>(
            containerService.deleteByContainerId(containerId), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DataSetContainer> getList(@PageableDefault Pageable pageable)
    {
        return containerService.get(pageable);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public DataSetContainer update(@RequestBody DataSetContainer setContainer)
    {
        containerService.update(setContainer);
        return setContainer;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataSetContainer> create(@RequestBody DataSetContainer setContainer)
    {
        containerService.save(setContainer);
        return new ResponseEntity<DataSetContainer>(setContainer, HttpStatus.CREATED);
    }

    /**
     * 上传容器装载的数据
     * 
     * @param file
     * @param containerId
     *            对应的容器Id
     * @return
     */
    @RequestMapping(value = "/{containerId}/uploadSetData", method = RequestMethod.POST,
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file,
                                             @PathVariable("containerId") String containerId)
    {
        return new ResponseEntity<String>(containerService.uploadData(containerId, file),
            HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{containerId}")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filePath") String filePath,
                                               @PathVariable("containerId") String containerId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;filename="+containerService.getFileName(containerId));
        return new ResponseEntity<byte[]>(containerService.downloadData(containerId, filePath),
            HttpStatus.OK);
    }


    /**
     * 返回与固定Id数据容器关联的数据集合信息
     * 
     * @param 数据容器Id
     * @return 与传入参数关联的数据容器
     */
    @RequestMapping(value = "/{containerId}/collection")
    public DataSetCollection getCollection(@PathVariable("containerId") String containerId)
    {
        return containerService.fetchCollectionRef(containerId);
    }

    /**
     * 获取容器的数据路径
     * 
     * @param containerId
     * @return 对应的文件路径
     */
    @RequestMapping(value = "/{containerId}/filePath", method = RequestMethod.GET)
    public String filePath(@PathVariable("containerId") String containerId)
    {
        return containerService.getDataSetPath(containerId);
    }
}
