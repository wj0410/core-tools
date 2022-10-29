package com.wj.limit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "stock")
public class Stock {
    @TableField(value = "id")
    private Long id;

    @TableField(value = "count")
    private Long count;
}