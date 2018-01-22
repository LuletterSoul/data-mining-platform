package com.vero.dm.repository.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.vero.dm.model.MiningTaskType;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Repository
@SuppressWarnings("unchecked")
public class MiningTaskTypeDao extends BaseDao<MiningTaskType, Integer>
{
    public MiningTaskTypeDao()
    {
        super(MiningTaskType.class);
    }

    public List<MiningTaskType> getTaskTypes(List<Integer> taskIds)
    {
        String hqlString = "select distinct t from MiningTaskType t where t.typeId in :taskIds";
        return getSession().createQuery(hqlString).setParameterList("taskIds",
            taskIds).getResultList();
    }
}
