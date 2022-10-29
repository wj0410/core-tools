package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运行时流程执行实例表 并行任务时，一个流程实例会产生多个执行实例
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_run_execution")
public class RunExecution extends BaseEntity {
    /**
     * 乐观锁
     */
    @TableField(value = "rev")
    private Integer rev;

    /**
     * 流程实例id
     */
    @TableField(value = "proc_inst_id")
    private Long procInstId;

    /**
     * 业务主键ID
     */
    @TableField(value = "business_key")
    private String businessKey;

    /**
     * 父执行实例id,子执行实例时不为空
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 流程定义id
     */
    @TableField(value = "proc_def_id")
    private Long procDefId;

    /**
     * 节点实例id  包括gateway、userTask..、
     */
    @TableField(value = "act_id")
    private Long actId;

    /**
     * 是否存活 0挂起 ，1存活 ，2结束
     */
    @TableField(value = "active")
    private Integer active;

    /**
     * 是否并行
     */
    @TableField(value = "is_concurrent")
    private Integer isConcurrent;

    /**
     * 主实例为1，子实例为0
     */
    @TableField(value = "is_scope")
    private Integer isScope;
}