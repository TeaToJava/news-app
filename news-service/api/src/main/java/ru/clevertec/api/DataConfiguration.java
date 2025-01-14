package ru.clevertec.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import ru.clevertec.cachestarter.cache.CacheManagerImpl;
import ru.clevertec.cachestarter.cache.CacheType;

import java.time.Duration;

@Configuration
@EnableCaching
@EnableJpaRepositories
public class DataConfiguration {

    @Value("${spring.cache.size}")
    private int size;
    @Bean
    @Profile("lfu")
    public CacheManager lfuCacheManager() {
        return new CacheManagerImpl(CacheType.LFU, size);
    }
    @Bean
    @Profile("lru")
    public CacheManager lruCacheManager() {
        return new CacheManagerImpl(CacheType.LRU, size);
    }

    @Bean
    @Profile("redis")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }

    @Bean
    @Profile("redis")
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
