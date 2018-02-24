package com.vero.dm.api.controller;


import static com.vero.dm.service.constant.ResourcePath.COLLECTION_PATH;

import java.util.List;

import com.vero.dm.repository.dto.CollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
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
@RequestMapping(value = ApiVersion.API_VERSION + COLLECTION_PATH)
public class DataSetCollectionController
{
    private DataSetCollectionService collectionService;

    @Autowired
    public void setCollectionService(DataSetCollectionService collectionService)
    {
        this.collectionService = collectionService;
    }

    @Cacheable(cacheNames = "dataSetCollectionCache")
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


    @CacheEvict(cacheNames = "dataSetCollectionCache",allEntries = true)
    @ApiOperation(value = "根据id删除数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @DeleteMapping(value = "/{collectionId}")
    public ResponseEntity<DataSetCollection> delete(@PathVariable("collectionId") String collectionId)
    {
        DataSetCollection collection = collectionService.deleteByCollectionId(collectionId);
        return new ResponseEntity<>(collection, HttpStatus.NO_CONTENT);
    }


    @CacheEvict(cacheNames = "dataSetCollectionCache",allEntries = true)
    @ApiOperation(value = "批量删除数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionIds", value = "数据集编号", dataType = "List", paramType = "body", required = true)})
    @DeleteMapping
    public ResponseEntity<List<DataSetCollection>> deleteBatch(@RequestBody List<String> collectionIds)
    {
        return new ResponseEntity<>(collectionService.deleteBatch(collectionIds),
            HttpStatus.NO_CONTENT);
    }


    @CacheEvict(cacheNames = "dataSetCollectionCache",allEntries = true)
    @ApiOperation(value = "更新数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "setCollection", value = "数据集编号", dataType = "CollectionDto", paramType = "body", required = true)})
    @PutMapping
    public ResponseEntity<DataSetCollection> update(@RequestBody DataSetCollection dataSetCollection)
    {
        return new ResponseEntity<>(collectionService.updateCollection(dataSetCollection),
            HttpStatus.OK);
    }

    @CacheEvict(cacheNames = "dataSetCollectionCache",allEntries = true)
    @ApiOperation(value = "创建数据集")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionDto", value = "数据集DTO", dataType = "CollectionDto", paramType = "body", required = true)})
    @PostMapping
    public ResponseEntity<DataSetCollection> create(@RequestBody CollectionDto collectionDto)
    {
        return new ResponseEntity<>(collectionService.saveCollection(collectionDto),
            HttpStatus.CREATED);
    }

    @ApiOperation(value = "上传数据集文件")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @PostMapping(value = "/{collectionId}/containers")
    public ResponseEntity<DataSetContainer> addContainer(@RequestPart MultipartFile file,
                                                         @PathVariable("collectionId") String collectionId)
    {
        return new ResponseEntity<>(collectionService.addDataSetContainer(collectionId, file),
            HttpStatus.CREATED);
    }


    @ApiOperation(value = "获取所有数据集文件信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "collectionId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @GetMapping(value = "/{collectionId}/containers")
    public ResponseEntity<Page<DataSetContainer>> getContainer(@PageableDefault Pageable pageable,@PathVariable("collectionId") String collectionId)
    {
         return new ResponseEntity<>(collectionService.getContainers(collectionId,pageable),HttpStatus.OK);
    }
}
