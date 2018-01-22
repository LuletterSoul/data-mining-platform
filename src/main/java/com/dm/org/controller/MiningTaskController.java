package com.dm.org.controller;

import com.dm.org.dto.MiningTaskDTO;
import com.dm.org.service.MiningTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<DataMiningTask> getList(@PageableDefault Pageable pageable)
    {
        return miningTaskService.fetchTaskList(pageable);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public DataMiningTask update(@RequestBody DataMiningTask dataMiningTask)
    {
        miningTaskService.update(dataMiningTask);
        return dataMiningTask;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataMiningTask> create(@RequestBody MiningTaskDTO miningTaskDTO)
    {
        return new ResponseEntity<DataMiningTask>(miningTaskService.saveMiningTask(miningTaskDTO), HttpStatus.CREATED);
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

    @RequestMapping(value = "/{taskId}/collections", method = RequestMethod.POST)
    public ResponseEntity<DataSetCollection> arrangeMiningSet(@PathVariable("taskId") String taskId, @RequestBody String collectionId) {
        return new ResponseEntity<DataSetCollection>(miningTaskService.arrangeMiningSet(taskId, collectionId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/collections/addSetsWithArray", method = RequestMethod.POST)
    public ResponseEntity<List<DataSetCollection>> arrangeMiningSets(@PathVariable("taskId") String taskId, @RequestBody List<String> collectionIds) {
        return new ResponseEntity<List<DataSetCollection>>(miningTaskService.arrangeMiningSets(taskId, collectionIds), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/collections",method = RequestMethod.GET)
    public List<DataSetCollection> getInvolvedSets(@PathVariable("taskId") String taskId) {
        return miningTaskService.fetchRefCollections(taskId);
    }

    @RequestMapping(value = "/{taskId}/collections",method = RequestMethod.DELETE)
    public ResponseEntity<List<DataSetCollection>> deleteAllRelContainers(@PathVariable("taskId") String taskId) {
        return new ResponseEntity<List<DataSetCollection>>(miningTaskService.removeAllMiningSets(taskId),HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{taskId}/collections/deleteBathWithArray",method = RequestMethod.DELETE)
    public ResponseEntity<List<DataSetCollection>> deleteContainersWithArray(@PathVariable("taskId") String taskId, @RequestBody List<String> collectionIds) {
        return new ResponseEntity<List<DataSetCollection>>(miningTaskService.removeMiningSets(taskId, collectionIds), HttpStatus.NO_CONTENT);
    }




}
