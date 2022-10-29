package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程部署表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_re_deployment")
public class ReDeployment extends BaseEntity {
    /**
     * 流程key
     */
    @TableField(value = "key")
    private String key;

    /**
     * 流程名称
     */
    @TableField(value = "name")
    private String name;
}