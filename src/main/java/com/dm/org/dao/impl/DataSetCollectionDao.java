package com.dm.org.dao.impl;


import com.dm.org.model.DataSetCollection;

import com.dm.org.model.DataSetCollection_;
import com.dm.org.model.DataSetContainer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


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

    public DataSetCollection getCollectionByName(String collectionName)
    {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot).where(
            baseBuilder.equal(baseRoot.get(DataSetCollection_.collectionName), collectionName));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public List<DataSetContainer> getContainers(String collectionId)
    {
        String hqlString = "select c from DataSetCollection collect " +
                            "left join collect.dataSets c " +
                            "where collect.collectionId = :collectionId";
        return getSession().createQuery(hqlString).setParameter("collectionId", collectionId).getResultList();
    }

}
