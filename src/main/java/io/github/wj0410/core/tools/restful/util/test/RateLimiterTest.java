package io.github.wj0410.core.tools.restful.util.test;

import com.google.common.util.concurrent.RateLimiter;
import io.github.wj0410.core.tools.util.RateLimiterUtil;

public class RateLimiterTest {
    public static void main(String[] args) {
        // 每秒生成1个令牌
        RateLimiter rateLimiter = RateLimiterUtil.create(1);
        // 每次请求获取1个令牌
        boolean b = rateLimiter.tryAcquire(1);
        System.out.println(b);
    }
}
