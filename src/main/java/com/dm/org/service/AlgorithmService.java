package com.dm.org.service;

import com.dm.org.model.Algorithm;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public interface AlgorithmService extends BaseService<Algorithm,String> {
    List<String> fetchAlgorithmNames();
}
