package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 历史任务实例表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_his_taskinst")
public class HisTaskinst extends BaseEntity {
    /**
     * 流程定义id
     */
    @TableField(value = "proc_def_id")
    private Long procDefId;

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
     * 任务节点定义id
     */
    @TableField(value = "task_def_id")
    private String taskDefId;

    /**
     * 任务节点定义名称
     */
    @TableField(value = "task_def_name")
    private String taskDefName;

    /**
     * 执行实例ID
     */
    @TableField(value = "execution_id")
    private Long executionId;

    /**
     * 父节点id，不知道干什么用的
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 委托人
     */
    @TableField(value = "client")
    private String client;

    /**
     * 签收人
     */
    @TableField(value = "assignee")
    private String assignee;

    /**
     * 是否通过 0驳回 1通过 2撤销
     */
    @TableField(value = "passed")
    private Integer passed;

    /**
     * 审批意见
     */
    @TableField(value = "message")
    private String message;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;
}