/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql-3306
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 127.0.0.1:3306
 Source Schema         : workflow

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 26/11/2021 10:29:46
*/
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for workflow_his_actinst
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_his_actinst`;
CREATE TABLE `workflow_his_actinst`
(
    `id`                BIGINT    NOT NULL COMMENT '主键',
    `proc_def_id`       BIGINT    NOT NULL COMMENT '流程定义id',
    `proc_inst_id`      BIGINT    NOT NULL COMMENT '流程实例id',
    `execute_id`        BIGINT     DEFAULT NULL COMMENT '执行实例id',
    `act_id`            VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '节点定义id',
    `task_id`           BIGINT     DEFAULT NULL COMMENT '任务实例id，start和end节点为空',
    `call_proc_inst_id` BIGINT     DEFAULT NULL COMMENT '调用外部流程的流程实例id，备用字段',
    `act_name`          VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '节点名称',
    `act_type`          VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '节点类型  startEvent；userTask；endEvent；各种gateway',
    `assignee`          VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin  DEFAULT NULL COMMENT '节点签收人',
    `start_time`        datetime  NOT NULL COMMENT '开始时间',
    `end_time`          datetime   DEFAULT NULL COMMENT '结束时间',
    `create_by`         BIGINT(20) DEFAULT NULL COMMENT '创建人',
    `create_time`       datetime   DEFAULT NULL COMMENT '创建时间',
    `update_by`         BIGINT(20) DEFAULT NULL COMMENT '更改人',
    `update_time`       datetime   DEFAULT NULL COMMENT '更新时间',
    `is_delete`         TINYINT(4) DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '历史节点表';
-- ----------------------------
-- Table structure for workflow_his_taskinst
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_his_taskinst`;
CREATE TABLE `workflow_his_taskinst`
(
    `id`            BIGINT        NOT NULL COMMENT '主键',
    `proc_def_id`   BIGINT        NOT NULL COMMENT '流程定义id',
    `proc_inst_id`  BIGINT        NOT NULL COMMENT '流程实例id',
    `task_id`       BIGINT        DEFAULT NULL COMMENT '任务id',
    `task_def_id`   VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '任务节点定义id',
    `task_def_name` VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '任务节点定义名称',
    `execution_id`  BIGINT        DEFAULT NULL COMMENT '执行实例ID',
    `parent_id`     BIGINT        DEFAULT NULL COMMENT '父节点id，不知道干什么用的',
    `client`        VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '委托人',
    `assignee`      VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '签收人',
    `passed`        INT           DEFAULT NULL COMMENT '是否通过 0驳回 1通过 2撤销',
    `message`       VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '审批意见',
    `start_time`    datetime      NOT NULL COMMENT '开始时间',
    `end_time`      datetime      DEFAULT NULL COMMENT '结束时间',
    `create_by`     BIGINT(20)    DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime      DEFAULT NULL COMMENT '创建时间',
    `update_by`     BIGINT(20)    DEFAULT NULL COMMENT '更改人',
    `update_time`   datetime      DEFAULT NULL COMMENT '更新时间',
    `is_delete`     TINYINT(4)    DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '历史任务实例表';
-- ----------------------------
-- Table structure for workflow_re_deployment
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_re_deployment`;
CREATE TABLE `workflow_re_deployment`
(
    `id`          BIGINT          NOT NULL COMMENT '流程部署id主键',
    `key`         VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '流程key',
    `name`        VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '流程名称',
    `create_by`   BIGINT(20) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime   DEFAULT NULL COMMENT '创建时间',
    `update_by`   BIGINT(20) DEFAULT NULL COMMENT '更改人',
    `update_time` datetime   DEFAULT NULL COMMENT '更新时间',
    `is_delete`   TINYINT(4) DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '流程部署表';
-- ----------------------------
-- Table structure for workflow_re_procdef
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_re_procdef`;
CREATE TABLE `workflow_re_procdef`
(
    `id`            BIGINT        NOT NULL COMMENT '流程定义id主键',
    `deployment_id` BIGINT        NOT NULL COMMENT '流程部署id',
    `key`           VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '流程部署key',
    `version`       VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '版本号',
    `meta_info`     LONGTEXT CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '元数据，流程配置JSON',
    `active`        INT           NOT NULL COMMENT '是否为默认生效版本',
    `create_by`     BIGINT(20) DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime   DEFAULT NULL COMMENT '创建时间',
    `update_by`     BIGINT(20) DEFAULT NULL COMMENT '更改人',
    `update_time`   datetime   DEFAULT NULL COMMENT '更新时间',
    `is_delete`     TINYINT(4) DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '流程定义表';
-- ----------------------------
-- Table structure for workflow_run_execution
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_run_execution`;
CREATE TABLE `workflow_run_execution`
(
    `id`            BIGINT NOT NULL COMMENT '执行实例id主键',
    `rev`           INT           DEFAULT NULL COMMENT '乐观锁',
    `proc_inst_id`  BIGINT        DEFAULT NULL COMMENT '流程实例id',
    `business_key`  VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务主键ID',
    `parent_id`     BIGINT        DEFAULT NULL COMMENT '父执行实例id,子执行实例时不为空',
    `proc_def_id`   BIGINT NOT NULL COMMENT '流程定义id',
    `act_id`        BIGINT        DEFAULT NULL COMMENT '节点实例id  包括gateway、userTask..、',
    `active`        INT    NOT NULL COMMENT '是否存活 0挂起 ，1存活 ，2结束',
    `is_concurrent` INT    NOT NULL COMMENT '是否并行',
    `is_scope`      INT           DEFAULT NULL COMMENT '主实例为1，子实例为0',
    `create_by`     BIGINT(20)    DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime      DEFAULT NULL COMMENT '创建时间',
    `update_by`     BIGINT(20)    DEFAULT NULL COMMENT '更改人',
    `update_time`   datetime      DEFAULT NULL COMMENT '更新时间',
    `is_delete`     TINYINT(4)    DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_actId` (`act_id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '运行时流程执行实例表 并行任务时，一个流程实例会产生多个执行实例';
-- ----------------------------
-- Table structure for workflow_run_identitylink
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_run_identitylink`;
CREATE TABLE `workflow_run_identitylink`
(
    `id`           BIGINT NOT NULL COMMENT '主键',
    `rev`          INT            DEFAULT NULL COMMENT '乐观锁',
    `user_id`      VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户',
    `task_id`      BIGINT NOT NULL COMMENT '任务实例id',
    `proc_inst_id` BIGINT NOT NULL COMMENT '流程实例id',
    `proc_def_id`  BIGINT NOT NULL COMMENT '流程定义id',
    `create_by`    BIGINT(20)     DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime       DEFAULT NULL COMMENT '创建时间',
    `update_by`    BIGINT(20)     DEFAULT NULL COMMENT '更改人',
    `update_time`  datetime       DEFAULT NULL COMMENT '更新时间',
    `is_delete`    TINYINT(4)     DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '运行时流程人员表';
-- ----------------------------
-- Table structure for workflow_run_task
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_run_task`;
CREATE TABLE `workflow_run_task`
(
    `id`           BIGINT         NOT NULL COMMENT '主键',
    `rev`          INT            DEFAULT NULL COMMENT '乐观锁字段',
    `execution_id` BIGINT         NOT NULL COMMENT '执行实例id',
    `proc_inst_id` BIGINT         NOT NULL COMMENT '流程实例id',
    `proc_def_id`  BIGINT         NOT NULL COMMENT '流程定义id',
    `business_key` VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务主键',
    `name`         VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '节点定义名称',
    `parent_id`    BIGINT         DEFAULT NULL COMMENT '父节点任务实例id，一般为空，暂时不知道有什么用',
    `task_def_id`  VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '任务定义的ID',
    `client`       VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '委托人（一般情况下为空，只有在委托时才有值）',
    `assignee`     VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '签收人',
    `due_days`     INT            DEFAULT NULL COMMENT '过期天数',
    `create_by`    BIGINT(20)     DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime       DEFAULT NULL COMMENT '创建时间',
    `update_by`    BIGINT(20)     DEFAULT NULL COMMENT '更改人',
    `update_time`  datetime       DEFAULT NULL COMMENT '更新时间',
    `is_delete`    TINYINT(4)     DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '运行时任务节点表,此表里的数据都是未完成的任务';
-- ----------------------------
-- Table structure for workflow_run_variable
-- ----------------------------
DROP TABLE
    IF
        EXISTS `workflow_run_variable`;
CREATE TABLE `workflow_run_variable`
(
    `id`           BIGINT         NOT NULL COMMENT '主键',
    `rev`          INT            DEFAULT NULL COMMENT '乐观锁字段，实体类加@Version注解',
    `name`         VARCHAR(255) CHARACTER
        SET utf8 COLLATE utf8_bin NOT NULL COMMENT '变量名称',
    `proc_inst_id` BIGINT         NOT NULL COMMENT '流程实例id',
    `task_id`      BIGINT         DEFAULT NULL COMMENT '任务id',
    `type_double`  FLOAT          DEFAULT NULL COMMENT '存储变量类型为Double',
    `type_long`    DECIMAL(19, 0) DEFAULT NULL COMMENT '存储变量类型为long',
    `type_string`  LONGTEXT CHARACTER
        SET utf8 COLLATE utf8_bin COMMENT '存储变量值类型为String',
    `create_by`    BIGINT(20)     DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime       DEFAULT NULL COMMENT '创建时间',
    `update_by`    BIGINT(20)     DEFAULT NULL COMMENT '更改人',
    `update_time`  datetime       DEFAULT NULL COMMENT '更新时间',
    `is_delete`    TINYINT(4)     DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT = '运行时流程变量数据表';

SET FOREIGN_KEY_CHECKS = 1;