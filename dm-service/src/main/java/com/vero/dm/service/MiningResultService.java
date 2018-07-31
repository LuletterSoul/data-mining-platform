package com.vero.dm.service;


import com.vero.dm.model.MiningResult;
import com.vero.dm.model.ResultRecord;
import com.vero.dm.model.enums.ResultState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:32 2018/7/24.
 * @since data-mining-platform
 */

public interface MiningResultService extends BaseService<MiningResult, Integer>
{

    MiningResult saveResult(MiningResult result);

    Page<MiningResult> findResults(String taskId, Integer stageId, Pageable pageable, String submitterId, ResultState state, boolean all);

    ResultRecord uploadResult(Integer resultId, MultipartFile resultFile);

    void downloadResults(List<Integer> recordIds, HttpServletResponse response);



}
