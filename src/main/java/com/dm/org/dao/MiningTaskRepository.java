package com.dm.org.dao;

import com.dm.org.model.Algorithm;
import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataSetCollection;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in  8:25 2017/10/11.
 * @since data-minning-platform
 */

public interface MiningTaskRepository {
    List<DataMiningGroup> fetchInvolvedGroups(String taskId);

    List<DataMiningGroup> fetchPartInvolvedGroups(String taskId, List<String> groupIds);

    List<DataSetCollection> fetchRefCollections(String taskId);

    int removeInvolvedGroups(String taskId, List<String> groupIds);

    Algorithm getConfiguredAlgorithm(String taskId, String algorithmId);

    List<Algorithm> getConfiguredAlgorithms(String taskId);

    int removeAllInvolvedAlgorithm(String taskId);

    int removePartInvolvedAlgorithms(String taskId, List<String> algorithmIds);

    int removeRefSets(String taskId, List<String> containerIds);

    int removeAllRefSet(String taskId);

    List<DataSetCollection> fetchPartRefSets(String taskId, List<String> containerIds);
}
