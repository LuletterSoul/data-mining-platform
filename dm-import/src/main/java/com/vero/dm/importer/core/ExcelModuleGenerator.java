package com.vero.dm.importer.core;

/**
 * 模板生成器
 * 根据{@link com.vero.dm.importer.annotations.ExcelModel}
 * 内的注解类型配置模板信息
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:06 2018/2/6.
 * @since data-mining-platform
 */

public interface ExcelModuleGenerator
{
    /**
     * 约定生成的基础路径
     */
    String outputFilePath = "/excel/modules";

    /**
     * 根据类的信息生成模板
     * 
     * @param clazz
     *            模型类
     * @return 生成的模板路径
     * @throws Exception
     */
    String generate(Class<?> clazz)
        throws Exception;
}
