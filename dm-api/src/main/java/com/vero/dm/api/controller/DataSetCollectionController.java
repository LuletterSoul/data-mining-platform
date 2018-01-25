package com.vero.dm.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
import com.vero.dm.repository.dto.CollectionDTO;
import com.vero.dm.service.DataSetCollectionService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@Slf4j
@RequestMapping(value = ApiVersion.API_VERSION + "/dataSets")
public class DataSetCollectionController
{
    private DataSetCollectionService collectionService;

    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService)
    {
        this.collectionService = collectionService;
    }

    @ApiOperation(value = "分页查询数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10"),
        @ApiImplicitParam(name = "sort", value = "按某属性排序", dataType = "String", paramType = "query", defaultValue = "collectionId"),
        @ApiImplicitParam(name = "direction", value = "排序方式", dataType = "String", paramType = "query", defaultValue = "DESC")})
    @GetMapping
    public ResponseEntity<Page<DataSetCollection>> getPageable(@PageableDefault(size = 15, sort = {
        "collectionId"}, direction = Sort.Direction.DESC) Pageable pageable)
    {
        return new ResponseEntity<>(collectionService.getPageableCollection(pageable),
            HttpStatus.OK);
    }

    @ApiOperation(value = "根据id获取数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @GetMapping(value = "/{collectionId}")
    public ResponseEntity<DataSetCollection> getById(@PathVariable("collectionId") String collectionId)
    {
        return new ResponseEntity<>(collectionService.findById(collectionId), HttpStatus.OK);
    }

    @ApiOperation(value = "根据id删除数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @DeleteMapping(value = "/{collectionId}")
    public ResponseEntity<DataSetCollection> delete(@PathVariable("collectionId") String collectionId)
    {
        DataSetCollection collection = collectionService.deleteByCollectionId(collectionId);
        return new ResponseEntity<>(collection, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "批量删除数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionIds", value = "数据集编号", dataType = "List", paramType = "body", required = true)})
    @DeleteMapping
    public ResponseEntity<List<DataSetCollection>> deleteBatch(@RequestBody List<String> collectionIds)
    {
        return new ResponseEntity<>(collectionService.deleteBatch(collectionIds),
            HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "更新数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "setCollection", value = "数据集编号", dataType = "CollectionDTO", paramType = "body", required = true)})
    @PutMapping
    public ResponseEntity<DataSetCollection> update(@RequestBody DataSetCollection dataSetCollection)
    {
        return new ResponseEntity<>(collectionService.updateCollection(dataSetCollection),HttpStatus.OK);
    }

    @ApiOperation(value = "创建数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "setCollection", value = "数据集编号", dataType = "CollectionDTO", paramType = "body", required = true)})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataSetCollection> create(@RequestBody DataSetCollection collection)
    {
        return new ResponseEntity<DataSetCollection>(
            collectionService.saveCollection(collection), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{collectionId}", method = RequestMethod.POST)
    public ResponseEntity<DataSetContainer> addContainer(@RequestBody DataSetContainer container,
                                                         @PathVariable("collectionId") String collectionId)
    {
        return new ResponseEntity<DataSetContainer>(
            collectionService.addDataSetContainer(collectionId, container), HttpStatus.CREATED);
    }

    /**
     * @param collectionId
     * @param containerId
     * @return
     */
    @RequestMapping(value = "/{collectionId}/{containerId}", method = RequestMethod.DELETE)
    public DataSetContainer removeContainer(@PathVariable("collectionId") String collectionId,
                                            @PathVariable("containerId") String containerId)
    {
        return collectionService.removeDataSetContainer(collectionId, containerId);
    }

    /**
     * 获取该数据集下的所有数据容器信息
     * 
     * @param collectionId
     * @return
     */
    @RequestMapping(value = "/{collectionId}/container", method = RequestMethod.GET)
    public List<DataSetContainer> getContainer(@PathVariable("collectionId") String collectionId)
    {
        return collectionService.getContainers(collectionId);
    }

    @RequestMapping(value = "/{collectionId}/{containerId}", method = RequestMethod.POST)
    public DataSetContainer relateContainer(@PathVariable("collectionId") String collectionId,
                                            @PathVariable("containerId") String containerId)
    {
        return collectionService.relateContainer(collectionId, containerId);
    }
}
