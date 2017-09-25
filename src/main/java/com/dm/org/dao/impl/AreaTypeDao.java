package com.dm.org.dao.impl;

import com.dm.org.model.AreaType;
import org.springframework.stereotype.Repository;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class AreaTypeDao extends BaseDao<AreaType, Integer> {
    public AreaTypeDao() {
        super(AreaType.class);
    }
}
