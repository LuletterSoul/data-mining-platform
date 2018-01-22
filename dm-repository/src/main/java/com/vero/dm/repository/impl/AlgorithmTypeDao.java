package com.vero.dm.repository.impl;


import org.springframework.stereotype.Repository;

import com.vero.dm.model.AlgorithmType;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Repository
@SuppressWarnings("unchecked")
public class AlgorithmTypeDao extends BaseDao<AlgorithmType, Integer>
{
    protected AlgorithmTypeDao()
    {
        super(AlgorithmType.class);
    }
}
