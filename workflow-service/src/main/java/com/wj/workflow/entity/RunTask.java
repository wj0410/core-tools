package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运行时任务节点表,此表里的数据都是未完成的任务
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_run_task")
public class RunTask extends BaseEntity {
    /**
     * 乐观锁字段
     */
    @TableField(value = "rev")
    private Integer rev;

    /**
     * 执行实例id
     */
    @TableField(value = "execution_id")
    private Long executionId;

    /**
     * 流程实例id
     */
    @TableField(value = "proc_inst_id")
    private Long procInstId;

    /**
     * 流程定义id
     */
    @TableField(value = "proc_def_id")
    private Long procDefId;

    /**
     * 业务主键
     */
    @TableField(value = "business_key")
    private String businessKey;

    /**
     * 节点定义名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 父节点任务实例id，一般为空，暂时不知道有什么用
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 任务定义的ID
     */
    @TableField(value = "task_def_id")
    private String taskDefId;

    /**
     * 委托人（一般情况下为空，只有在委托时才有值）
     */
    @TableField(value = "client")
    private String client;

    /**
     * 签收人
     */
    @TableField(value = "assignee")
    private String assignee;

    /**
     * 过期天数
     */
    @TableField(value = "due_days")
    private Integer dueDays;
}