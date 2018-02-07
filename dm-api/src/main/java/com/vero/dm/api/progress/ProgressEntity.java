package com.vero.dm.api.progress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:47 2018/2/2.
 * @since data-mining-platform
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressEntity
{

    private long pBytesRead = 0L; // 到目前为止读取文件的比特数

    private long pContentLength = 0L; // 文件总大小

    private int pItems; // 目前正在读取第几个文件
}
