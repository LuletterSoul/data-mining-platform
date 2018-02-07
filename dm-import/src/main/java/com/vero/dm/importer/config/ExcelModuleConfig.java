package com.vero.dm.importer.config;


import org.springframework.context.annotation.Configuration;

import com.vero.dm.importer.annotations.ExcelModelScan;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:59 2018/2/5.
 * @since data-mining-platform
 */

@Configuration
@ExcelModelScan(basePackages = {"com.vero.dm.model"})
public class ExcelModuleConfig
{
}
