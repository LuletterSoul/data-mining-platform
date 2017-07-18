package com.dm.org.dao.impl;

import com.dm.org.model.DataSetCollection_;
import com.dm.org.model.DataSetContainer;
import com.dm.org.model.DataSetContainer_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:42 2017/7/5.
 * @description
 * @modified by:
 */

@Repository
public class DataSetContainerDao extends BaseDao<DataSetContainer, String>
{
    public DataSetContainerDao()
    {
        super(DataSetContainer.class);
    }


    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
        CriteriaQuery<DataSetContainer> query = buildCriteriaQuery();
        Predicate wherePredicate = baseBuilder
                                    .equal(baseRoot.get(DataSetContainer_.dataSetCollection)
                                            .get(DataSetCollection_.collectionId), collectionId);
        query.where(wherePredicate);
        return getSession().createQuery(query).getResultList();
    }

}
