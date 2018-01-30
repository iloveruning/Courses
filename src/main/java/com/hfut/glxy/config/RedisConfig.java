//package com.hfut.glxy.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.cache.interceptor.SimpleKeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author chenliangliang
// * @date 2017/12/25
// */
//@Configuration
//@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport{
//
//    @Bean("myRedisTemplate")
//    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
//
//        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
//
//        return redisTemplate;
//    }
//
//    @Override
//    public KeyGenerator keyGenerator(){
//        return new SimpleKeyGenerator();
//    }
//
//
//    @Bean
//    public CacheManager cacheManager(@Qualifier("myRedisTemplate") RedisTemplate redisTemplate) {
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
//        List<String> caches=new ArrayList<>(12);
//        caches.add("readComments");
//        redisCacheManager.setCacheNames(caches);
//        redisCacheManager.setDefaultExpiration(7200);
//        Map<String,Long> map=new HashMap<>(12);
//        map.put("readComments",3600L);
//        redisCacheManager.setExpires(map);
//        return redisCacheManager;
//    }
//
//
//
//}
