package com.vero.dm.web.entry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vero.dm.importer.annotations.ExcelModelScan;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:41 2018/1/22.
 * @since data-minning-platform
 */
@Configuration
@ExcelModelScan(basePackages = {"com.vero.dm.model"})
@ComponentScan(basePackages = {"com.vero.dm"}) // 扫描该包路径下的所有spring组件
@SpringBootApplication
@EntityScan("com.vero.dm.model")
@EnableJpaRepositories("com.vero.dm.repository")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class DataMiningPlatformWebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DataMiningPlatformWebApplication.class, args);
    }
}
