package com.wj.limit.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.wj.core.tools.redis.Executor;
import com.wj.core.tools.redis.RedisLimitHelper;
import com.wj.core.tools.redis.RedisLockHelper;
import com.wj.core.tools.restful.result.R;
import com.wj.core.tools.util.RateLimiterUtil;
import com.wj.limit.entity.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class TestController {
    private static final String limitIpKey = "limit:ip";

    @Autowired
    private RedisLimitHelper redisLimitHelper;
    @Autowired
    private RedisLockHelper redisLockHelper;

    /**
     * 每秒生成2个令牌
     */
    private RateLimiter rateLimiter = RateLimiterUtil.create(2);

    @GetMapping("/test")
    public R test() {
        List<Stock> test = redisLockHelper.getCacheList("test", new Executor() {
            @Override
            public List getDbList() {
                List<Stock> stockList = new ArrayList<>();
                Stock s1 = new Stock();
                Stock s2 = new Stock();
                Stock s3 = new Stock();
                Stock s4 = new Stock();
                s1.setCount(1L);
                s2.setCount(2L);
                s3.setCount(3L);
                s4.setCount(4L);
                stockList.add(s1);
                stockList.add(s2);
                stockList.add(s3);
                stockList.add(s4);
                return stockList;
            }
        });
        return R.data(test);
    }

    @GetMapping("/limit")
    public R limit(HttpServletRequest request) {
        String msg = "success";
        String ipKey = limitIpKey + ":" + request.getRemoteAddr();
        // 1. ip限流 1s访问一次
        Boolean b = redisLimitHelper.limit(ipKey, "1000", "1");
        if (b) {
            msg = "您的访问频率过快，请休息一下再尝试。";
        }
        // 2. 接口限流，接口限流表示只能处理N个并发。
        // 消耗1个令牌
        b = rateLimiter.tryAcquire();
        if (!b) {
            msg = "活动太火爆啦，请休息一下再尝试。";
        }
        return R.data(msg);
    }
}
