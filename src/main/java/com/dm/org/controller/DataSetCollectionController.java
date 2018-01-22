package com.dm.org.controller;


import com.dm.org.dto.CollectionDTO;
import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = "/dataSets",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataSetCollectionController {
    private DataSetCollectionService collectionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            DataSetCollectionController.class);


    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService) {
        this.collectionService = collectionService;
    }


    /**
     *
     * @param pageable 分页参数
     * @return  当前页的集合信息
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page<DataSetCollection> getPageable(@PageableDefault Pageable pageable) {
        return collectionService.getPageableCollection(pageable);
    }

    /**
     * 根据数据集名删除
     * @param collectionName 数据集名
     * @return
     */
//    @RequestMapping(value = "/{collectionName}", method = RequestMethod.GET)
//    public DataSetCollection getByCollectionName(@PathVariable("collectionName") String collectionName) {
//        return collectionService.getCollectionByName(collectionName);
//    }


    @RequestMapping(value = "/{collectionId}",method = RequestMethod.GET)
    public DataSetCollection getById(@PathVariable("collectionId") String collectionId) {
        return collectionService.findById(collectionId);
    }

    /**
     * 根据数据集Id删除该集
     * @param collectionId 数据集Id
     * @return
     */
    @RequestMapping(value = "/{collectionId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataSetCollection> delete(@PathVariable("collectionId") String collectionId) {
        DataSetCollection collection = collectionService.deleteByCollectionId(collectionId);
        return new ResponseEntity<DataSetCollection>(collection, HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/deleteWithArray", method = RequestMethod.DELETE)
    public ResponseEntity<List<DataSetCollection>> deleteBatch(@RequestBody List<String> collectionIds) {
        return new ResponseEntity<List<DataSetCollection>>(collectionService.deleteBatch(collectionIds), HttpStatus.NO_CONTENT);
    }


    /**
     * 更新数据集信息
     * @param setCollection 前台传入的数据集信息
     * @return 更新后的数据集信息
     */
    @RequestMapping(method = RequestMethod.PUT)
    public DataSetCollection update(@RequestBody CollectionDTO setCollection) {
        return collectionService.updateCollection(setCollection);
    }

    /**
     * 创建一个数据集
     * @param setCollection 前台传入具备基本信息的数据
     * @return 创建成功的返回体
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataSetCollection> create(@RequestBody CollectionDTO collectionDTO) {
        return new ResponseEntity<DataSetCollection>(
                collectionService.saveCollection(collectionDTO), HttpStatus.CREATED);
    }

    /**
     * 为数据集增加一个集合容器
     * @param collectionId
     * @return
     */
    @RequestMapping(value = "/{collectionId}", method = RequestMethod.POST)
    public ResponseEntity<DataSetContainer> addContainer(@RequestBody DataSetContainer container
                                        , @PathVariable("collectionId") String collectionId) {
        return new ResponseEntity<DataSetContainer>(collectionService.addDataSetContainer(collectionId, container), HttpStatus.CREATED);
    }

    /**
     *
     * @param collectionId
     * @param containerId
     * @return
     */
    @RequestMapping(value = "/{collectionId}/{containerId}", method = RequestMethod.DELETE)
    public DataSetContainer removeContainer(@PathVariable("collectionId") String collectionId
            , @PathVariable("containerId") String containerId) {
        return collectionService.removeDataSetContainer(collectionId, containerId);
    }

    /**
     * 获取该数据集下的所有数据容器信息
     * @param collectionId
     * @return
     */
    @RequestMapping(value = "/{collectionId}/container",method = RequestMethod.GET)
    public List<DataSetContainer> getContainer(@PathVariable("collectionId") String collectionId) {
        return collectionService.getContainers(collectionId);
    }

    @RequestMapping(value = "{collectionId}/{containerId}",method = RequestMethod.POST)
    public DataSetContainer relateContainer(@PathVariable("collectionId") String collectionId,
                                                  @PathVariable("containerId") String containerId) {
        return collectionService.relateContainer(collectionId, containerId);
    }
}


