package com.wj.dictionary.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "`dictionary`")
public class Dictionary extends BaseEntity {
    /**
     * 父ID
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 显示值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;
}