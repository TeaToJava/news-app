package ru.clevertec.cachestarter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import ru.clevertec.cachestarter.cache.CacheManagerImpl;
import ru.clevertec.cachestarter.cache.CacheType;

@AutoConfiguration
@EnableConfigurationProperties(CacheStarterProperties.class)
@ConditionalOnProperty(name = "spring.cache.type", matchIfMissing = true, havingValue = "none")
public class CacheStarterAutoConfiguration {
    @Bean
    @ConditionalOnProperty(value = "spring.cache.custom", havingValue = "lfu")
    public CacheManager lfuCacheManager(CacheStarterProperties cacheStarterProperties) {
        return new CacheManagerImpl(CacheType.LFU, cacheStarterProperties.getSize());
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cache.custom", havingValue = "lru")
    public CacheManager lruCacheManager(CacheStarterProperties cacheStarterProperties) {
        return new CacheManagerImpl(CacheType.LRU, cacheStarterProperties.getSize());
    }

}
