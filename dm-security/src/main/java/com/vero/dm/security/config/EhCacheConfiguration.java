package com.vero.dm.security.config;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in  22:53 2017/7/19.
 * @since data-mining-platform
 */

@Configuration
@EnableCaching
public class EhCacheConfiguration
{
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager)
    {
        return new EhCacheCacheManager(cacheManager);
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManager()
     {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        ehCacheManagerFactoryBean.afterPropertiesSet();
        return ehCacheManagerFactoryBean;
    }
}
