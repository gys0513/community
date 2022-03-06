package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean //连接数据库要创建连接，需要连接工厂
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置key的序列化方式(数据转换的方式)
        template.setKeySerializer(RedisSerializer.string());

        //普通的value的序列化方法
        template.setValueSerializer(RedisSerializer.json());

        //设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());

        //设置hash的value的序列化方法
        template.setHashValueSerializer(RedisSerializer.json());

        //生效
        template.afterPropertiesSet();

        return template;
    }
}
