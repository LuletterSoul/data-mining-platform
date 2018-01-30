package com.vero.dm.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.DataSetContainer;
import com.vero.dm.service.DataSetContainerService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION
                        + ResourcePath.CONTAINER_PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataSetContainerController
{
    private DataSetContainerService containerService;

    @Autowired
    public void setContainerService(DataSetContainerService containerService)
    {
        this.containerService = containerService;
    }

    @ApiOperation(value = "根据Id删除数据集文件")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "containerId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @DeleteMapping(value = "/{containerId}")
    public ResponseEntity<DataSetContainer> delete(@PathVariable("containerId") String containerId)
    {
        return new ResponseEntity<>(containerService.deleteByContainerId(containerId),
            HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "分页查询数据集文件信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "containerId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @GetMapping
    public ResponseEntity<Page<DataSetContainer>> get(@PageableDefault Pageable pageable)
    {
        return new ResponseEntity<>(containerService.getPageableContainers(pageable),
            HttpStatus.OK);
    }

    @PutMapping
    public DataSetContainer update(@RequestBody DataSetContainer setContainer)
    {
        containerService.update(setContainer);
        return setContainer;
    }

    @ApiOperation(value = "获取数据集的数据库路径")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "containerId", value = "数据集编号", dataType = "String", paramType = "path", required = true)})
    @GetMapping(value = "/{containerId}/filePath")
    public String filePath(@PathVariable("containerId") String containerId)
    {

        return containerService.getDataSetPath(containerId);
    }
}
