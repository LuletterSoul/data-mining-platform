package com.dm.org.dao.impl;


import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetCollection_;
import com.dm.org.model.DataSetContainer;
import com.dm.org.model.DataSetContainer_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 23:42 2017/7/5.
 * @description
 * @modified by:
 */

@Repository
@SuppressWarnings("unchecked")
public class DataSetContainerDao extends BaseDao<DataSetContainer, String>
{
    public DataSetContainerDao()
    {
        super(DataSetContainer.class);
    }

    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
        CriteriaQuery<DataSetContainer> query = buildCriteriaQuery();
        Predicate wherePredicate = baseBuilder.equal(
            baseRoot.get(DataSetContainer_.dataSetCollection).get(DataSetCollection_.collectionId),
            collectionId);
        query.where(wherePredicate);
        return getSession().createQuery(query).getResultList();
    }

    public DataSetCollection fetchDataSetCollection(String containerId)
    {
        buildCriteriaQuery();
        baseRoot.fetch(DataSetContainer_.dataSetCollection, JoinType.LEFT);
        criteriaQuery.where(
            baseBuilder.equal(baseRoot.get(DataSetContainer_.containerId), containerId));
        DataSetContainer container = getSession().createQuery(criteriaQuery).getSingleResult();
        return container.getDataSetCollection();
    }

    public List<String> getContainerIds() {
        String hqlString = "select c.containerId from DataSetContainer c";
        return getSession().createQuery(hqlString).getResultList();

    }

    public List<String> getContainerFileNames()
    {
        String hqlString = "select c.fileName from DataSetContainer c";
        return getSession().createQuery(hqlString).getResultList();
    }

    public List<String> getContainerNames() {
        String hqlString = "select c.containerName from DataSetContainer c";
        return getSession().createQuery(hqlString).getResultList();
    }

    public DataSetContainer getContainerByName(String containerName)
    {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot).where(
            baseBuilder.equal(baseRoot.get(DataSetContainer_.containerName), containerName));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public DataSetContainer getContainerByFileName(String fileName)
    {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot).where(
                baseBuilder.equal(baseRoot.get(DataSetContainer_.fileName), fileName));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public String linkPath(String containerId,String containerName,String fileName,String filePath) {
        String hqlString = "UPDATE DataSetContainer c " +
                "set c.containerName = :containerName,c.fileName= :fileName,c.filePath = :filePath " +
                "where c.containerId= :containerId";
        getSession().createQuery(hqlString)
                .setParameter("containerId",containerId)
                .setParameter("containerName", containerName)
                .setParameter("fileName", fileName)
                .setParameter("filePath", filePath)
                .executeUpdate();
        return filePath;
    }

    public String getDataSetPath(String containerId) {
        String hqlString = "select c.filePath from DataSetContainer c where containerId = :containerId";
        return (String) getSession().createQuery(hqlString).setParameter("containerId", containerId).getSingleResult();
    }

    public String getFileName(String containerId) {
//        CriteriaBuilder builder =  getSession().getCriteriaBuilder();
//        CriteriaQuery<String> stringCriteriaQuery = builder.createQuery(String.class);
//        stringCriteriaQuery.select(baseRoot.get(DataSetContainer_.fileName))
//                .where(builder.equal(baseRoot.get(DataSetContainer_.containerId), containerId));
        String hqlString = "select c.fileName from DataSetContainer c where c.containerId = :containerId";
        return (String) getSession().createQuery(hqlString)
                .setParameter("containerId", containerId).getSingleResult();
    }

    public List<DataSetContainer> fetchContainers(List<String> containerIds) {
        String hqlString = "from DataSetContainer c where c.containerId in :containerIds";
        return getSession().createQuery(hqlString)
                            .setParameter("containerIds",containerIds).getResultList();
    }



}
