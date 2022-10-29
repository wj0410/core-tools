package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程定义表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_re_procdef")
public class ReProcdef extends BaseEntity {
    /**
     * 流程部署id
     */
    @TableField(value = "deployment_id")
    private Long deploymentId;

    /**
     * 流程部署key
     */
    @TableField(value = "key")
    private String key;

    /**
     * 版本号
     */
    @TableField(value = "version")
    private String version;

    /**
     * 元数据，流程配置JSON
     */
    @TableField(value = "meta_info")
    private String metaInfo;

    /**
     * 是否为默认生效版本
     */
    @TableField(value = "active")
    private Integer active;
}