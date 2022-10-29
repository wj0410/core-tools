package com.wj.workflow.enums;

/**
 * 历史节点类型
 */
public enum HisActinstType {
    /**
     * 开始节点
     **/
    START_EVENT("startEvent"),
    /**
     * 结束节点
     **/
    END_EVENT("endEvent"),
    /**
     * 任务节点
     **/
    USER_TASK("userTask"),
    /**
     * 排他网关
     **/
    EXCLUSIVE_GATEWAY("exclusiveGateway"),
    /**
     * 并行网关
     **/
    PARALLEL_GATEWAY("parallelGateway"),
    /**
     * 包含网关
     **/
    INCLUSIVE_GATEWAY("inclusiveGateway");

    HisActinstType(String code) {
        this.code = code;
    }

    private final String code;

    public String getCode() {
        return code;
    }

}
