package com.hfut.glxy.config;//package com.hfut.glxy.config;
//
//import org.apache.ibatis.cache.Cache;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.connection.RedisServerCommands;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
///**
// * @author chenliangliang
// * @date 2017/12/25
// */
//public class RedisCache implements Cache {
//
//    private final Logger logger = LoggerFactory.getLogger(RedisCache.class);
//
//    private final ReadWriteLock lock = new ReentrantReadWriteLock();
//
//    private final String id;
//
//
//    @Autowired
//    @Qualifier("myRedisTemplate")
//    private RedisTemplate<String, Object> redisTemplate;
//
//
//    public RedisCache(final String id) {
//        if (id == null) {
//            throw new IllegalArgumentException("Cache instances require an ID");
//        }
//        logger.info("Redis Cache id " + id);
//        redisTemplate = SpringBeanUtil.getBean("myRedisTemplate");
//        this.id = id;
//    }
//
//    @Override
//    public String getId() {
//        return this.id;
//    }
//
//    @Override
//    public void putObject(Object key, Object value) {
//
//        if (value != null) {
//            redisTemplate.opsForValue().set(key.toString(), value, 2, TimeUnit.HOURS);
//        }
//    }
//
//    @Override
//    public Object getObject(Object key) {
//        if (key != null) {
//            return redisTemplate.opsForValue().get(key.toString());
//        }
//        return null;
//    }
//
//    @Override
//    public Object removeObject(Object key) {
//        if (key!=null){
//            redisTemplate.delete(key.toString());
//        }
//        return null;
//    }
//
//    @Override
//    public void clear() {
//        logger.debug("清空缓存");
//        Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
//        if (!keys.isEmpty()) {
//            redisTemplate.delete(keys);
//        }
//    }
//
//    @Override
//    public int getSize() {
//        Long size = redisTemplate.execute(RedisServerCommands::dbSize);
//        return size.intValue();
//    }
//
//    @Override
//    public ReadWriteLock getReadWriteLock() {
//        return this.lock;
//    }
//}
