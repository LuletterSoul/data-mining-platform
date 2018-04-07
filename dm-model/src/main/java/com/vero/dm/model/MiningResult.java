package com.vero.dm.model;

import javax.persistence.Entity;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:53 2018/3/29.
 * @since data-mining-platform
 */


public class MiningResult
{
    private Integer resultId;

    private String comment;

    /**
     * 数据挖掘的分析结果的文件路径
     */
    private String path;

}
