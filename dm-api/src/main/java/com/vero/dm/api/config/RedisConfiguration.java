package com.vero.dm.api.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:05 2018/2/1.
 * @since data-mining-platform
 */

@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport
{
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    // 自定义缓存key生成策略
    // @Bean
    // public KeyGenerator keyGenerator() {
    // return new KeyGenerator(){
    // @Override
    // public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
    // StringBuffer sb = new StringBuffer();
    // sb.append(target.getClass().getName());
    // sb.append(method.getName());
    // for(Object obj:params){
    // sb.append(obj.toString());
    // }
    // return sb.toString();
    // }
    // };
    // }

    // 缓存管理器
    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate)
    {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // 设置缓存过期时间
        cacheManager.setDefaultExpiration(10000);
        return cacheManager;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory)
    {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
            Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        setSerializer(template);
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(RedisTemplate<String, Object> template)
    {
        @SuppressWarnings({"rawtypes", "unchecked"})
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
            Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
    }
}
