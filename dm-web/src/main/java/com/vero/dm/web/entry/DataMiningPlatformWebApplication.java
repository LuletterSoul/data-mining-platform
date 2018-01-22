package com.vero.dm.web.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  15:41 2018/1/22.
 * @since data-minning-platform
 */

@ComponentScan(basePackages = {"com.vero.dm"}) // 扫描该包路径下的所有spring组件
@EntityScan({"com.vero.dm.model"})
@SpringBootApplication
public class DataMiningPlatformWebApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DataMiningPlatformWebApplication.class, args);
    }
}
