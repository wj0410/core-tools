package com.wj.dictionary.dto;

import com.wj.core.tools.restful.annotation.NotBlank;
import com.wj.core.tools.restful.annotation.Operation;
import com.wj.core.tools.restful.annotation.Unique;
import com.wj.core.tools.restful.dto.BaseDTO;
import com.wj.dictionary.entity.Dictionary;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 字典表
 */
@Data
public class DictionaryDTO extends BaseDTO {
    /**
     * 父ID
     */
    @NotBlank(operation = {Operation.SAVE})
    @Unique
    private Long pid;

    /**
     * 编码
     */
    @NotBlank(operation = {Operation.SAVE})
    @Unique
    private String code;

    /**
     * 显示值
     */
    @NotBlank(operation = {Operation.SAVE})
    private String value;

    /**
     * 排序
     */
    @NotBlank(operation = {Operation.SAVE})
    private Integer sort;

    @Override
    public Dictionary buildEntity() {
        Dictionary entity = new Dictionary();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}