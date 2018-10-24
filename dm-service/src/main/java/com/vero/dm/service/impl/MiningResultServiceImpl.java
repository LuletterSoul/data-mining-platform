package com.vero.dm.service.impl;


import static com.vero.dm.repository.specifications.ResultSpecifications.resultsSpec;
import static com.vero.dm.util.DownloadUtils.bigFileDownload;
import static com.vero.dm.util.DownloadUtils.generateTimestampZipFileName;
import static com.vero.dm.util.PathUtils.concat;
import static com.vero.dm.util.PathUtils.getAbsolutePath;
import static org.apache.tomcat.util.http.fileupload.FileUtils.forceDelete;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.vero.dm.util.CompressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.file.SetZipException;
import com.vero.dm.model.MiningResult;
import com.vero.dm.model.ResultRecord;
import com.vero.dm.model.Student;
import com.vero.dm.model.enums.ResultState;
import com.vero.dm.repository.dto.MiningResultDto;
import com.vero.dm.service.MiningResultService;
import com.vero.dm.service.constant.ResourcePath;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:39 2018/7/24.
 * @since data-mining-platform
 */

@Service
@Slf4j
public class MiningResultServiceImpl extends AbstractBaseServiceImpl<MiningResult, Integer> implements MiningResultService
{

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public void setThreadPoolExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor)
    {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public MiningResult saveResult(MiningResult result)
    {
        return miningResultRepository.save(result);
    }

    @Override
    public Page<MiningResultDto> findResults(String taskId, Integer stageId, Pageable pageable,
                                             List<String> submitterIds, ResultState state,
                                             boolean all)
    {
        if (all)
        {
            List<MiningResult> results = miningResultRepository.findAll(
                resultsSpec(taskId, stageId, submitterIds, state));
            return new PageImpl<>(MiningResultDto.build(results), pageable, results.size());
        }
        Page<MiningResult> page = miningResultRepository.findAll(
            resultsSpec(taskId, stageId, submitterIds, state), pageable);
        List<MiningResult> results = page.getContent();
        return new PageImpl<>(MiningResultDto.build(results), pageable, page.getTotalElements());
    }

    @Override
    public ResultRecord uploadResult(Integer resultId, MultipartFile resultFile)
    {
        MiningResult result = miningResultRepository.findOne(resultId);
        if (Objects.isNull(result.getSubmitter()) || Objects.isNull(result.getStage())
            || Objects.isNull(result.getStage().getTask()))
        {
            return null;
        }
        Student submitter = result.getSubmitter();
        String userPath = submitter.getStudentId() + '_' + submitter.getStudentName();
        String taskName = result.getStage().getTask().getTaskName();
        Integer stageId = result.getStage().getOrderId();
        String fileName;
        if (StringUtils.isEmpty(resultFile.getOriginalFilename()))
        {
            fileName = resultFile.getName();
        }
        else
        {
            fileName = resultFile.getOriginalFilename();
        }
        String absolutePath = getAbsolutePath(
            concat(ResourcePath.STUDENT_PATH, userPath, taskName, String.valueOf(stageId)),
            fileName);
        log.info("[{}]开始上传文件，文件位于[{}]", submitter.getUsername(), absolutePath);
        threadPoolTaskExecutor.execute(() -> {
            try
            {
                File located = new File(Objects.requireNonNull(absolutePath));
                resultFile.transferTo(located);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        ResultRecord resultRecord = new ResultRecord();
        resultRecord.setChecked(false);
        resultRecord.setFileName(fileName);
        // resultRecord.setResult(result);
        resultRecord.setPath(absolutePath);
        resultRecord.setSize(resultFile.getSize());
        resultRecord.setSubmittedDate(new Date());
        resultRecordRepository.save(resultRecord);
        //当前为状态为为提交的结果，置状态位为已提交
        result.setState(ResultState.submitted);
        Set<ResultRecord> resultRecords  = result.getRecords();
        if(Objects.isNull(resultRecords)){
            resultRecords = new LinkedHashSet<>();
        }
        resultRecords.add(resultRecord);
        result.setRecords(resultRecords);
        miningResultRepository.save(result);
        log.info(submitter.getUsername() + "上传了数据挖掘结果,位于[{}]", resultRecord.getPath());
        return resultRecord;
    }

    @Override
    public void downloadResults(List<Integer> recordIds, HttpServletResponse response)
    {
        List<ResultRecord> records = resultRecordRepository.findAll(recordIds);
        String dir = getAbsolutePath(ResourcePath.STUDENT_PATH);
        List<String> filePaths = new ArrayList<>();
        records.forEach(d -> filePaths.add(d.getPath()));
        ArrayList<File> files = new ArrayList<>();
        records.forEach(d -> files.add(new File(d.getPath())));
        String zipPath = getAbsolutePath(concat(ResourcePath.ZIP_PATH, ResourcePath.STUDENT_PATH));
        // 生成临时压缩文件
        String zipFileName = UUID.randomUUID().toString() + ".zip";
        // 输出的压缩文件路径
        String zipFilePath = concat(zipPath, zipFileName);
        try
        {
            // 进行文件压缩
            // ZipUtil.zip(dir, zipPath, zipFileName, filePaths);
//            ZipCompressor compressor = new ZipCompressor(zipFilePath, dir);
//            compressor.zip(filePaths);
            CompressUtil.zip(dir, zipFilePath,files,null, true );
            bigFileDownload(response, zipFilePath, generateTimestampZipFileName("results_"));
            threadPoolTaskExecutor.execute(() -> {
                try
                {
                    // 删除临时创建的压缩文件
                    forceDelete(new File(zipFilePath));
                    log.info("删除临时压缩文件:[{}]", zipFilePath);
                }
                catch (IOException e)
                {
                    throw new SetZipException("Could not delete temp zip files.",
                        ExceptionCode.ZipSetError);
                }
            });
            List<MiningResult> results = miningResultRepository.findResultByRecords(recordIds);
            //更新状态
            results.forEach(r -> r.setState(ResultState.downloaded));
            miningResultRepository.save(results);
        }
        catch (Exception e)
        {
            throw new SetZipException("Process zip operation error.", ExceptionCode.ZipSetError);
        }
    }
}
