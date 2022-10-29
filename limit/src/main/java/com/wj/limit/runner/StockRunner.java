package com.wj.limit.runner;

import com.wj.limit.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 商品预热
 */
@Component
@Slf4j
public class StockRunner implements CommandLineRunner {
    @Autowired
    StockService stockService;

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> 商品预热 ");
        stockService.reFlushStock();
    }
}
