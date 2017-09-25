package com.dm.org.dao.impl;

import com.dm.org.model.MiningTaskType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class MiningTaskTypeDao extends BaseDao<MiningTaskType, Integer> {
    public MiningTaskTypeDao() {
        super(MiningTaskType.class);
    }

    public List<MiningTaskType> fetchOptionalTaskType() {
        String hqlString = "from MiningTaskType ";
        return getSession().createQuery(hqlString).getResultList();
    }
}
