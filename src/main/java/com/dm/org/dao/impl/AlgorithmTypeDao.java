package com.dm.org.dao.impl;

import com.dm.org.model.AlgorithmType;
import org.springframework.stereotype.Repository;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class AlgorithmTypeDao extends BaseDao<AlgorithmType, Integer> {
    protected AlgorithmTypeDao() {
        super(AlgorithmType.class);
    }
}
