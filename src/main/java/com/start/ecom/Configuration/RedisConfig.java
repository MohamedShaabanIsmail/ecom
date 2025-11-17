package com.start.ecom.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
// import org.springframework.data.redis.cache.RedisCacheManager;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.start.ecom.models.Product;

@Configuration
public class RedisConfig {

    // @Bean
    // public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    //     return RedisCacheManager.builder(redisConnectionFactory)
    //             .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
    //                     .disableCachingNullValues()
    //                     .serializeValuesWith(RedisSerializationContext.SerializationPair
    //                             .fromSerializer(new Jackson2JsonRedisSerializer<>(Product.class))))
    //             .build();
    // }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new Jackson2JsonRedisSerializer<>(Product.class)));
    }
    
}
