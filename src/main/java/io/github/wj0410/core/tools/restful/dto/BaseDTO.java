package io.github.wj0410.core.tools.restful.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.wj0410.core.tools.restful.annotation.NotBlank;
import io.github.wj0410.core.tools.restful.annotation.Operation;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjie
 * @version 1.0
 * date 2022年01月10日11时55分
 */
@Data
public abstract class BaseDTO implements Serializable {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotBlank(operation = {Operation.UPDATE,Operation.DELETE})
    private Long id;

    public abstract Serializable buildEntity();
}