package com.dm.org.dao.impl;

import com.dm.org.dao.MiningTaskRepository;
import com.dm.org.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class MiningTaskDao extends BaseDao<DataMiningTask, String> implements MiningTaskRepository {
    public MiningTaskDao() {
        super(DataMiningTask.class);
    }


    @Override
    public List<DataMiningGroup> fetchInvolvedGroups(String taskId) {
        String hqlString = "select t.groups from DataMiningTask t " +
                            "left join fetch t.groups g " +
                            "left join fetch g.groupMembers m " +
                            "where t.taskId = :taskId";
        return getSession().createQuery(hqlString)
                            .setParameter("taskId",taskId)
                            .getResultList();
    }

    @Override
    public List<DataMiningGroup> fetchPartInvolvedGroups(String taskId, List<String> groupIds) {
        String hqlString = "select t.groups from DataMiningTask t " +
                "left join fetch t.groups g " +
                "left join fetch g.groupMembers m " +
                "where t.taskId = :taskId and g.groupId in :groupIds";
        return getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .setParameter("groupIds", groupIds).getResultList();
    }

    @Override
    public List<DataSetCollection> fetchRefCollections(String taskId) {
        String hqlString = "select c from DataMiningTask t left join t.collections c";
        return getSession().createQuery(hqlString).setParameter("taskId", taskId).getResultList();
    }

    @Override
    public int removeInvolvedGroups(String taskId, List<String> groupIds) {
        String hqlString = "delete DataMiningTask.groups g " +
                            "where DataMiningTask.taskId = :taskId and g.groupId in :groupIds";
        return getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .setParameter("groupIds", groupIds).executeUpdate();
    }

    @Override
    public Algorithm getConfiguredAlgorithm(String taskId, String algorithmId) {
        String hqlString = "select a from DataMiningTask t " +
                "left join fetch t.algorithms a " +
                "where t.taskId = :taskId and a.algorithmId = :algorithmId";
        return (Algorithm) getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .setParameter("algorithmId", algorithmId)
                .getSingleResult();
    }


    @Override
    public List<Algorithm> getConfiguredAlgorithms(String taskId) {
        String hqlString = "select a from DataMiningTask t " +
                "left join fetch t.algorithms a " +
                "where t.taskId = :taskId";
        return  getSession().createQuery(hqlString)
                .setParameter("taskId", taskId)
                .getResultList();
    }

    @Override
    public int removeAllInvolvedAlgorithm(String taskId) {
        String hqlString = "delete DataMiningTask.algorithms where DataMiningTask .taskId = :taskId";
        return getSession().createQuery(hqlString)
                            .setParameter("taskId", taskId)
                            .executeUpdate();
    }

    @Override
    public int removePartInvolvedAlgorithms(String taskId, List<String> algorithmIds) {
        String hqlString = "delete DataMiningTask.algorithms a " +
                            "where a.algorithmId " +
                            "in :algorithmIds and DataMiningTask.taskId =:taskId";
        return getSession().createQuery(hqlString)
                .setParameter("algorithmIds", algorithmIds)
                .setParameter("taskId", taskId).executeUpdate();
    }

    @Override
    public int removeRefSets(String taskId, List<String> containerIds) {
        String hqlString = "delete DataMiningTask .collections c " +
                            "where DataMiningTask .taskId =:taskId " +
                            "and c.containerId in :containerIds";
        return getSession().createQuery(hqlString).setParameter("taskId", taskId)
                .setParameter("containerIds", containerIds).executeUpdate();
    }

    @Override
    public int removeAllRefSet(String taskId) {
        String hqlString = "delete DataMiningTask .collections where DataMiningTask .taskId = :taskId";
        return getSession().createQuery(hqlString)
                            .setParameter("taskId", taskId)
                            .executeUpdate();
    }

    @Override
    public List<DataSetCollection> fetchPartRefSets(String taskId, List<String> containerIds) {
        String hqlString = "select c from DataMiningTask t " +
                                "left join fetch  t.collections c";
        return getSession().createQuery(hqlString).setParameter("taskId", taskId)
                .setParameter("containerIds", containerIds).getResultList();
    }



}
