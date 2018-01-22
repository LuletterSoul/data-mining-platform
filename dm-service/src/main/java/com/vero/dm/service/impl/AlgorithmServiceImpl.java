package com.vero.dm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vero.dm.model.Algorithm;
import com.vero.dm.service.AlgorithmService;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Service
public class AlgorithmServiceImpl extends AbstractBaseServiceImpl<Algorithm,String> implements AlgorithmService {
    @Override
    public List<String> fetchAlgorithmNames() {
        return this.algorithmDao.fetchAlgorithmNames();
    }
}
