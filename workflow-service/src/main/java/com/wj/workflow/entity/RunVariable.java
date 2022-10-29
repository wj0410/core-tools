package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 运行时流程变量数据表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_run_variable")
public class RunVariable extends BaseEntity {
    /**
     * 乐观锁字段，实体类加@Version注解
     */
    @TableField(value = "rev")
    private Integer rev;

    /**
     * 变量名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 流程实例id
     */
    @TableField(value = "proc_inst_id")
    private Long procInstId;

    /**
     * 任务id
     */
    @TableField(value = "task_id")
    private Long taskId;

    /**
     * 存储变量类型为Double
     */
    @TableField(value = "type_double")
    private Double typeDouble;

    /**
     * 存储变量类型为long
     */
    @TableField(value = "type_long")
    private BigDecimal typeLong;

    /**
     * 存储变量值类型为String
     */
    @TableField(value = "type_string")
    private String typeString;
}