package io.github.wj0410.core.tools.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * redis限流工具
 */
@Component
public class RedisLimitHelper {
    @Autowired
    RedisTemplate redisTemplate;

    // redis+lua限流 利用lua脚本做到原子性。
    // redisTemplate序列化必须使用String

    /**
     * 接口访问限流
     * @param key 键
     * @param millisecond 多少毫秒内
     * @param frequency 可以访问的次数
     * @return boolean
     */
    public boolean limit(String key,String millisecond,String frequency) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<Long>();
        String lua = "local key = KEYS[1]" +
                " local period = ARGV[1]" +
                " local limit= ARGV[2]" +
                " local times = redis.call('incr',key)" +
                " if times == 1 then" +
                " redis.call('expire',KEYS[1], period/1000)" +
                " end" +
                " if times > tonumber(limit) then" +
                " return 0" +
                " end" +
                " return 1";
        redisScript.setScriptText(lua);
        redisScript.setResultType(Long.class);
        //表示 second 秒内最多访问 frequency 次
        //key [key ...]，被操作的key，可以多个，在lua脚本中通过KEYS[1], KEYS[2]获取
        //arg [arg ...]，参数，可以多个，在lua脚本中通过ARGV[1], ARGV[2]获取。
        //0: 超出限制，else：正常请求
        Long count = (Long) redisTemplate.execute(redisScript, new GenericToStringSerializer(Object.class),
                new GenericToStringSerializer(Object.class),Arrays.asList(key), millisecond,frequency);
        //0:超出范围
        return count == 0;
    }
}
