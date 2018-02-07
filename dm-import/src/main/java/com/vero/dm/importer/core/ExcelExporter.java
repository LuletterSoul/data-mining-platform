package com.vero.dm.importer.core;


import java.io.File;

import javax.servlet.http.HttpServletResponse;


/**
 * Excel导出器
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:39 2018/2/7.
 * @since data-mining-platform
 */

public interface ExcelExporter<E>
{
    /**
     * @param file
     *            系统中已经存在Excel文件
     * @param response
     *            将要写入文件的响应体
     */
    void exportExcelModule(File file, HttpServletResponse response);
}
