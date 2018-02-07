package com.vero.dm.importer.core;


import java.io.File;
import java.util.List;


/**
 * Excel导出器
 * 根据输入的文件导出Excel数据
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:26 2018/2/6.
 * @since data-mining-platform
 */

public interface ExcelImporter<E>
{
    /**
     *
     * @param file
     * @return
     */
    List<E> importFromExcel(File file);
}
