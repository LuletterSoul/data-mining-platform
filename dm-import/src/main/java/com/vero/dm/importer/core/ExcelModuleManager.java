package com.vero.dm.importer.core;


import java.util.List;


/**
 * 托管Spring扫描生成的模板文件信息
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:30 2018/2/6.
 * @since data-mining-platform
 */

public interface ExcelModuleManager
{
    void registerExcelModulePath();

    /**
     * @param put
     * @param clazz
     */
    void put(String put, Class<?> clazz);

    String getModulePath(Class<?> clazz);

    String deleteModule(Class<?> clazz);

    List getExcelModuleHeadTitle(Class<?> clazz);
}
