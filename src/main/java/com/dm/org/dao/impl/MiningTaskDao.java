package com.dm.org.dao.impl;

import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class MiningTaskDao extends BaseDao<DataMiningTask, String> {
    public MiningTaskDao() {
        super(DataMiningTask.class);
    }

    public List<DataMiningGroup> fetchInvolvedGroups(String taskId) {
        String hqlString = "select t.groups from DataMiningTask t " +
                            "left join t.groups g " +
                            "left join g.groupInfos i " +
                            "left join i.student where t.taskId = :taskId";
        return getSession().createQuery(hqlString)
                            .setParameter("taskId",taskId)
                            .getResultList();
    }
}
