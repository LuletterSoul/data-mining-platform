package com.dm.org.controller;

import com.dm.org.model.Algorithm;
import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.MiningTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@RestController
@RequestMapping(value = "/tasks")
public class MiningTaskController
{
    private MiningTaskService miningTaskService;

    @Autowired
    public void setMiningTaskService(MiningTaskService miningTaskService) {
        this.miningTaskService = miningTaskService;
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataMiningTask> deleteById( @PathVariable("taskId") String taskId)
    {
        return new ResponseEntity<DataMiningTask>(miningTaskService.deleteByTaskId(taskId), HttpStatus.NO_CONTENT);
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<DataMiningTask> getList(@PageableDefault Pageable pageable)
    {
        return miningTaskService.get(pageable);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public DataMiningTask update(@RequestBody DataMiningTask dataMiningTask)
    {
        miningTaskService.update(dataMiningTask);
        return dataMiningTask;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataMiningTask> create(@RequestBody DataMiningTask dataMiningTask)
    {
        miningTaskService.save(dataMiningTask);
        return new ResponseEntity<DataMiningTask>(dataMiningTask, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/groups", method = RequestMethod.GET)
    public List<DataMiningGroup> groups(@PathVariable("taskId") String taskId) {
        return miningTaskService.fetchInvolvedGroups(taskId);
    }

    @RequestMapping(value = "/{taskId}/groups/{groupId}",method = RequestMethod.GET)
    public DataMiningGroup getInvolvedGroup(@PathVariable("taskId") String taskId, @PathVariable("groupId") String groupId) {
        return miningTaskService.getInvolvedGroup(taskId, groupId);
    }

    @RequestMapping(value = "/{taskId}/groups/{groupId}",method = RequestMethod.DELETE)
    public ResponseEntity<DataMiningGroup> removeGroup(@PathVariable("taskId") String taskId, @PathVariable("groupId") String groupId) {
        return new ResponseEntity<DataMiningGroup>(miningTaskService.removeInvolvedGroup(taskId, groupId), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{taskId}/groups",method = RequestMethod.POST)
    public ResponseEntity<DataMiningGroup> involveGroup(@PathVariable("taskId") String taskId,@RequestBody String groupId) {
        return new ResponseEntity<DataMiningGroup>(miningTaskService.involveGroup(taskId, groupId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/groups/involveWithArray", method = RequestMethod.POST)
    public ResponseEntity<List<DataMiningGroup>> involveGroups(@PathVariable("taskId") String taskId, @RequestBody List<String> groupIds) {
        return new ResponseEntity<List<DataMiningGroup>>(miningTaskService.involveGroups(taskId, groupIds), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/groups/removeWithArray", method = RequestMethod.DELETE)
    public ResponseEntity<List<DataMiningGroup>> removeGroups(@PathVariable("taskId") String taskId,@RequestBody List<String> groupIds) {
        return new ResponseEntity<List<DataMiningGroup>>(miningTaskService.removeInvolvedGroups(taskId, groupIds), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{taskId}/algorithms/{algorithmId}",method = RequestMethod.GET)
    public Algorithm getAlgorithm(@PathVariable("taskId") String taskId, @PathVariable("algorithmId") String algorithmId) {
        return miningTaskService.getAlgorithm(taskId, algorithmId);
    }

    @RequestMapping(value = "/{taskId}/algorithms",method = RequestMethod.GET)
    public List<Algorithm> getAlgorithms(@PathVariable("taskId") String taskId) {
        return miningTaskService.fetchConfiguredAlgorithms(taskId);
    }


    @RequestMapping(value = "/{taskId}/algorithms/configWithArray",method = RequestMethod.POST)
    public ResponseEntity<List<Algorithm>> configAlgorithms(@PathVariable("taskId") String taskId,
                                            @RequestBody List<String> algorithmIds) {
        return new ResponseEntity<List<Algorithm>>(miningTaskService.configureAlgorithms(taskId, algorithmIds),HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/algorithms/{algorithmId}",method = RequestMethod.DELETE)
    public ResponseEntity<Algorithm> cancelAlgorithm(@PathVariable("taskId") String taskId,@PathVariable("algorithmId") String algorithmId) {
        return new ResponseEntity<Algorithm>(miningTaskService.configureAlgorithm(taskId, algorithmId)
                , HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/algorithms/deleteWithArray", method = RequestMethod.DELETE)
    public ResponseEntity<List<Algorithm>> cancelAlgorithms(@PathVariable("taskId") String taskId,@RequestBody List<String> algorithmIds) {
        return new ResponseEntity<List<Algorithm>>(miningTaskService.removeAlgorithms(taskId, algorithmIds),HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{taskId}/containers", method = RequestMethod.POST)
    public ResponseEntity<DataSetContainer> arrangeMiningSet(@PathVariable("taskId") String taskId, @RequestBody String containerId) {
        return new ResponseEntity<DataSetContainer>(miningTaskService.arrangeMiningSet(taskId, containerId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/containers/addSetsWithArray", method = RequestMethod.POST)
    public ResponseEntity<List<DataSetContainer>> arrangeMiningSets(@PathVariable("taskId") String taskId, @RequestBody List<String> containerIds) {
        return new ResponseEntity<List<DataSetContainer>>(miningTaskService.arrangeMiningSets(taskId, containerIds), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/containers",method = RequestMethod.GET)
    public List<DataSetContainer> getInvolvedSets(@PathVariable("taskId") String taskId) {
        return miningTaskService.fetchRefContainers(taskId);
    }

    @RequestMapping(value = "/{taskId}/containers",method = RequestMethod.DELETE)
    public ResponseEntity<List<DataSetContainer>> deleteAllRelContainers(@PathVariable("taskId") String taskId) {
        return new ResponseEntity<List<DataSetContainer>>(miningTaskService.removeAllMiningSets(taskId),HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{taskId}/containers/deleteBathWithArray",method = RequestMethod.DELETE)
    public ResponseEntity<List<DataSetContainer>> deleteContainersWithArray(@PathVariable("taskId") String taskId, @RequestBody List<String> containerIds) {
        return new ResponseEntity<List<DataSetContainer>>(miningTaskService.removeMiningSets(taskId, containerIds), HttpStatus.NO_CONTENT);
    }




}
