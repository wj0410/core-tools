package com.wj.limit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.limit.entity.Stock;

public interface StockService extends IService<Stock>{

    /**
     * 查询库存
     * @return
     */
    Long stockNum();
    /**
     * 刷新库存
     * @return
     */
    boolean reFlushStock();
}
