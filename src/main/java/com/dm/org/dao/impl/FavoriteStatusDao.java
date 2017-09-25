package com.dm.org.dao.impl;

import com.dm.org.model.DataSetCollection;
import com.dm.org.model.FavoriteStatus;
import org.springframework.stereotype.Repository;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
public class FavoriteStatusDao extends BaseDao<FavoriteStatus, Integer> {
    public FavoriteStatusDao() {
        super(FavoriteStatus.class);
    }
}
