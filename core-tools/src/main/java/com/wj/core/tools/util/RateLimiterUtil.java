package com.wj.core.tools.util;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 令牌桶算法限流
 * 生成令牌的速度是恒定的，而请求去拿令牌是没有速度限制的。这意味，面对瞬时大流量，该算法可以在短时间内请求拿到大量令牌，而且拿令牌的过程并不是消耗很大的事情
 * RateLimiter在并发环境下使用是安全的：它将限制所有线程调用的总速率。注意，它不保证公平调用。
 * Rate limiter（直译为：速度限制器）经常被用来限制一些物理或者逻辑资源的访问速率。这和java.util.concurrent.Semaphore正好形成对照。
 * 一个RateLimiter主要定义了发放permits的速率。如果没有额外的配置，permits将以固定的速度分配，单位是每秒多少permits。默认情况下，Permits将会被稳定的平缓的发放。
 * 漏桶的出水速度是恒定的，那么意味着如果瞬时大流量的话，将有大部分请求被丢弃掉（也就是所谓的溢出）。
 */
public class RateLimiterUtil {

    public static RateLimiter create(double permitsPerSecond) {
        return RateLimiter.create(permitsPerSecond);
    }
}
