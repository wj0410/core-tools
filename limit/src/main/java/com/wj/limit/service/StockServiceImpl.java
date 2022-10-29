package com.wj.limit.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.limit.entity.Stock;
import com.wj.limit.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {
    private static final String stockKey = "stock:1";
    Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public Long stockNum() {
        log.info("正在查询商品ID为【1】的库存...");
        Stock stock = baseMapper.selectById(1);
        Long cnt = stock.getCount();
        log.info("查询完毕，商品ID为【1】的库存为：" + cnt);
        return cnt;
    }

    @Override
    public boolean reFlushStock() {
        Long cnt = stockNum();
        cnt = cnt == null ? 0L : cnt;
        redisTemplate.opsForValue().set(stockKey, String.valueOf(cnt));
        return true;
    }
}
