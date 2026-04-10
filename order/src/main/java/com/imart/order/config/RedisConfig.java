package com.imart.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {


    @Bean(name = "vaultRedisTemplate")
    public RedisTemplate<String, Object> vaultRedisTemplate(RedisConnectionFactory factory) {
        var jsonSerializer = GenericJacksonJsonRedisSerializer.builder()
                        .build();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(jsonSerializer);

        return template;
    }
    @Bean(name = "turboRedisTemplate")
    public RedisTemplate<String, Object> turboRedisTemplate(RedisConnectionFactory factory) {
        var jsonSerializer = GenericJacksonJsonRedisSerializer.builder()
                .build();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(jsonSerializer);

        return template;
    }
}