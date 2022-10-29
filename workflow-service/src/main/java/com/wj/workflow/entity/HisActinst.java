package com.wj.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.core.tools.mybatisplus.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 历史节点表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "workflow_his_actinst")
public class HisActinst extends BaseEntity {
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
     * 执行实例id
     */
    @TableField(value = "execute_id")
    private Long executeId;

    /**
     * 节点定义id
     */
    @TableField(value = "act_id")
    private String actId;

    /**
     * 任务实例id，start和end节点为空
     */
    @TableField(value = "task_id")
    private Long taskId;

    /**
     * 调用外部流程的流程实例id，备用字段
     */
    @TableField(value = "call_proc_inst_id")
    private Long callProcInstId;

    /**
     * 节点名称
     */
    @TableField(value = "act_name")
    private String actName;

    /**
     * 节点类型  startEvent；userTask；endEvent；各种gateway
     */
    @TableField(value = "act_type")
    private String actType;

    /**
     * 节点签收人
     */
    @TableField(value = "assignee")
    private String assignee;

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