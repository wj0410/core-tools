package io.github.wj0410.core.tools.redis;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.github.wj0410.core.tools.restful.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁工具
 */
public class RedisLockHelper {
    //锁名称
    public static final String LOCK_PREFIX = "redis_lock:";
    //加锁失效时间，毫秒
    public static final int LOCK_EXPIRE = 300; // ms
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 最终加强分布式锁
     *
     * @param lockKey key值
     * @return 是否获取到
     */
    public boolean lock(String lockKey) {
        String lock = LOCK_PREFIX + lockKey;
        // 利用lambda表达式
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {

            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());

            if (acquire) {
                return true;
            } else {
                byte[] value = connection.get(lock.getBytes());
                if (Objects.nonNull(value) && value.length > 0) {
                    long expireTime = Long.parseLong(new String(value));
                    // 如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 重新加锁，防止死锁
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    /**
     * 释放锁
     *
     * @param key key
     */
    public void unlock(String key) {
        String lock = LOCK_PREFIX + key;
        redisTemplate.delete(lock);
    }

    /**
     * 获取list缓存
     * @param cacheKey 缓存key
     * @param executor 获取数据库数据方法
     * @param <T> T
     * @return List
     */
    public <T> List getCacheList(String cacheKey, Executor executor) {
        List<T> cacheList = redisTemplate.opsForList().range(cacheKey, 0, -1);
        if (cacheList == null || cacheList.size() <= 0) {
            // 设置缓存
            // 设置锁，防止并发设置缓存，导致缓存数据重复
            boolean lock = lock(cacheKey);
            if (lock) {
                try {
                    // DCL双重校验，防止并发时出现重复push
                    cacheList = redisTemplate.opsForList().range(cacheKey, 0, -1);
                    if (cacheList == null || cacheList.size() <= 0) {
                        // 执行获取数据库数据接口
                        cacheList = executor.getDbList();
                        if(CollectionUtils.isNotEmpty(cacheList)){
                            // 执行逻辑操作
                            // 这里有个坑，如果不把List转换成数组，则会把List当Object，只会有一条数据
                            redisTemplate.opsForList().rightPushAll(cacheKey, cacheList.toArray());
                            // 1天过期
                            redisTemplate.expire(cacheKey, 1, TimeUnit.DAYS);
                        }
                    }
                } finally {
                    // 释放锁
                    unlock(cacheKey);
                }
            } else {
                try {
                    // 同时候的其他线程已经load db并回设到缓存了，这时候重试获取缓存值即可
                    Thread.sleep(50);
                    getCacheList(cacheKey, executor);
                } catch (InterruptedException e) {
                    throw new ServiceException(e.getMessage());
                }
            }
        }
        return cacheList;
    }
}
