package com.vero.dm.web.entry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:41 2018/1/22.
 * @since data-minning-platform
 */

@ComponentScan(basePackages = {"com.vero.dm"}) // 扫描该包路径下的所有spring组件
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class DataMiningPlatformWebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DataMiningPlatformWebApplication.class, args);
    }
}
