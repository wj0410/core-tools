package com.wj.limit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wj.limit.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
}