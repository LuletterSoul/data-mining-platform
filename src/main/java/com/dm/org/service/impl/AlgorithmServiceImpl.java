package com.dm.org.service.impl;

import com.dm.org.model.Algorithm;
import com.dm.org.service.AlgorithmService;
import org.springframework.stereotype.Service;

import java.util.List;

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
