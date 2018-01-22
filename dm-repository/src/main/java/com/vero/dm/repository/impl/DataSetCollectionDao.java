package com.vero.dm.repository.impl;


import java.util.List;

import com.vero.dm.model.DataSetCollection_;
import org.springframework.stereotype.Repository;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 23:40 2017/7/5.
 * @description
 * @modified by:
 */
@Repository
@SuppressWarnings("unchecked")
public class DataSetCollectionDao extends BaseDao<DataSetCollection, String>
{
    public DataSetCollectionDao()
    {
        super(DataSetCollection.class);
    }

    public List<String> getSetNames()
    {
        String hqlString = "select s.collectionName from DataSetCollection s";
        return getSession().createQuery(hqlString).getResultList();
    }

    public int deleteByName(String collectionName)
    {
        String hqlString = "DELETE from DataSetCollection d where d.collectionName = :collectionName";
        return getSession().createQuery(hqlString).setParameter("collectionName",
            collectionName).executeUpdate();
    }

    public int deleteBatch(List<String> collectionIds)
    {
        String hqlString = "DELETE from DataSetCollection  s where s.collectionId in :collectionIds";
        return getSession().createQuery(hqlString).setParameterList("collectionIds",
            collectionIds).executeUpdate();
    }

    public DataSetCollection getCollectionByName(String collectionName)
    {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot).where(
            baseBuilder.equal(baseRoot.get(DataSetCollection_.collectionName), collectionName));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public List<DataSetCollection> getCollectionByIds(List<String> collectionIds)
    {
        String hqlString = "select distinct s from DataSetCollection s where s.collectionId in :collectionIds";
        return getSession().createQuery(hqlString).setParameterList("collectionIds",
            collectionIds).getResultList();
    }

    public List<DataSetContainer> getContainers(String collectionId)
    {
        String hqlString = "select c from DataSetCollection collect "
                           + "left join fetch collect.dataSets c "
                           + "where collect.collectionId = :collectionId";
        return getSession().createQuery(hqlString).setParameter("collectionId",
            collectionId).getResultList();
    }

    public List<DataSetContainer> fetchRefContainers(String taskId)
    {
        String hqlString = "select c from DataMiningTask t left join t.collections c";
        return getSession().createQuery(hqlString).setParameter("taskId", taskId).getResultList();
    }
}
