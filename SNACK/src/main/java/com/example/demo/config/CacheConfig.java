//package com.example.demo.config;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.caffeine.CaffeineCache;
//import org.springframework.cache.support.SimpleCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
//
//    @Bean
//    public CacheManager cacheManager() {
//        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
//                .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
//                                .expireAfterWrite(cache.getExpiredAfterWrite(), TimeUnit.SECONDS) // 초,분,시,일...등 가능
//                                .maximumSize(cache.getMaximumSize())
//                                .build()
//                        )
//                )
//                .collect(Collectors.toList());
//        
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(caches);
//        return cacheManager;
//    }
//
//}
//
