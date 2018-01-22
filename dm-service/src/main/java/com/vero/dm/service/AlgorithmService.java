package com.vero.dm.service;


import java.util.List;

import com.vero.dm.model.Algorithm;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface AlgorithmService extends BaseService<Algorithm, String>
{
    List<String> fetchAlgorithmNames();
}
