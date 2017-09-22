package com.dm.org.dao.impl;

import com.dm.org.model.Algorithm;
import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;
import com.dm.org.model.DataSetContainer;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class MiningTaskDao extends BaseDao<DataMiningTask, String> {
    public MiningTaskDao() {
        super(DataMiningTask.class);
    }


    public List<DataMiningGroup> fetchInvolvedGroups(String taskId) {
        String hqlString = "select t.groups from DataMiningTask t " +
                            "left join fetch t.groups g " +
                            "left join fetch g.groupMembers m " +
                            "where t.taskId = :taskId";
        return getSession().createQuery(hqlString)
                            .setParameter("taskId",taskId)
                            .getResultList();
    }

    public List<DataMiningGroup> fetchPartInvolvedGroups(String taskId, List<String> groupIds) {
        String hqlString = "select t.groups from DataMiningTask t " +
                "left join fetch t.groups g " +
                "left join fetch g.groupMembers m " +
                "where t.taskId = :taskId and g.groupId in :groupIds";
        return getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .setParameter("groupIds", groupIds).getResultList();
    }

    public List<DataSetContainer> fetchRefContainers(String taskId) {
        String hqlString = "select c from DataMiningTask t left join fetch t.dataSetContainers c";
        return getSession().createQuery(hqlString).setParameter("taskId", taskId).getResultList();
    }

    public int removeInvolvedGroups(String taskId, List<String> groupIds) {
        String hqlString = "delete DataMiningTask.groups g " +
                            "where DataMiningTask.taskId = :taskId and g.groupId in :groupIds";
        return getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .setParameter("groupIds", groupIds).executeUpdate();
    }

    public Algorithm getConfiguredAlgorithm(String taskId, String algorithmId) {
        String hqlString = "select a from DataMiningTask t " +
                "left join fetch t.algorithms a " +
                "where t.taskId = :taskId and a.algorithmId = :algorithmId";
        return (Algorithm) getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .setParameter("algorithmId", algorithmId)
                .getSingleResult();
    }


    public List<Algorithm> getConfiguredAlgorithms(String taskId) {
        String hqlString = "select a from DataMiningTask t " +
                "left join fetch t.algorithms a " +
                "where t.taskId = :taskId";
        return  getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .getResultList();
    }

    public int removeAllInvolvedAlgorithm(String taskId) {
        String hqlString = "delete DataMiningTask.algorithms where DataMiningTask .taskId = :taskId";
        return getSession().createQuery(hqlString)
                            .setParameter("taskId", taskId)
                            .executeUpdate();
    }

    public int removePartInvolvedAlgorithms(String taskId, List<String> algorithmIds) {
        String hqlString = "delete DataMiningTask.algorithms a " +
                            "where a.algorithmId " +
                            "in :algorithmIds and DataMiningTask.taskId =:taskId";
        return getSession().createQuery(hqlString)
                .setParameter("algorithmIds", algorithmIds)
                .setParameter("taskId", taskId).executeUpdate();
    }

    public int removeRefSets(String taskId, List<String> containerIds) {
        String hqlString = "delete DataMiningTask .dataSetContainers c " +
                            "where DataMiningTask .taskId =:taskId " +
                            "and c.containerId in :containerIds";
        return getSession().createQuery(hqlString).setParameter("taskId", taskId)
                .setParameter("containerIds", containerIds).executeUpdate();
    }

    public int removeAllRefSet(String taskId) {
        String hqlString = "delete DataMiningTask .dataSetContainers where DataMiningTask .taskId = :taskId";
        return getSession().createQuery(hqlString)
                            .setParameter("taskId", taskId)
                            .executeUpdate();
    }

    public List<DataSetContainer> fetchPartRefSets(String taskId, List<String> containerIds) {
        String hqlString = "select c from DataMiningTask t " +
                                "left join fetch t.dataSetContainers c " +
                                    "where c.containerId in :containerIds and t.taskId = :taskId";
        return getSession().createQuery(hqlString).setParameter("taskId", taskId)
                .setParameter("containerIds", containerIds).getResultList();
    }



}
