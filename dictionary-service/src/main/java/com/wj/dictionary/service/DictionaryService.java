package com.wj.dictionary.service;

import cn.hutool.core.lang.tree.Tree;
import com.wj.dictionary.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DictionaryService extends IService<Dictionary> {
    /**
     * 字典树
     *
     * @param code 字典编码，为空查所有
     * @return
     */
    List<Tree<String>> dicTree(String code);

    /**
     * 根据字典编码查询值
     *
     * @param pcode
     * @param code
     * @return
     */
    String getValue(String pcode, String code);
}
