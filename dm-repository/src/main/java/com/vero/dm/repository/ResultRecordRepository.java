package com.vero.dm.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vero.dm.model.ResultRecord;
import com.vero.dm.model.enums.ResultState;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:41 2018/7/25.
 * @since data-mining-platform
 */

public interface ResultRecordRepository extends JpaRepository<ResultRecord, Integer>, JpaSpecificationExecutor<ResultRecord>
{
    @Query(value = "select re from DataMiningTask t left join t.stages s "
                   + "left join s.results r left join r.records re where t.taskId = :taskId and r.submitter.userId in :submitterIds and r.state = :state")
    List<ResultRecord> findAllResultRecords(@Param("taskId") String taskId,
                                         @Param("submitterIds") List<Integer> submitterIds,
                                         @Param("state") ResultState state, Pageable pageable);
}
