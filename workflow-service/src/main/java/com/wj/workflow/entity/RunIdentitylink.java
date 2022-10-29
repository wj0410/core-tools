package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运行时流程人员表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_run_identitylink")
public class RunIdentitylink extends BaseEntity {
    /**
     * 乐观锁
     */
    @TableField(value = "rev")
    private Integer rev;

    /**
     * 用户
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 任务实例id
     */
    @TableField(value = "task_id")
    private Long taskId;

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
}