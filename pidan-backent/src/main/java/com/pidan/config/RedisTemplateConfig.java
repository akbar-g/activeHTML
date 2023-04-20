package com.pidan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author 黄大头
 * @date 2023年04月19日 18:59
 */
@Configuration
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        //创建RedisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //设置廉洁工程
        redisTemplate.setConnectionFactory(connectionFactory);
        //设置key的序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        //创建Json序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //设置value的序列化
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        return redisTemplate;

    }
}
