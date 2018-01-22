package com.vero.dm.repository.impl;


import org.springframework.stereotype.Repository;

import com.vero.dm.model.FavoriteStatus;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Repository
public class FavoriteStatusDao extends BaseDao<FavoriteStatus, Integer>
{
    public FavoriteStatusDao()
    {
        super(FavoriteStatus.class);
    }
}
