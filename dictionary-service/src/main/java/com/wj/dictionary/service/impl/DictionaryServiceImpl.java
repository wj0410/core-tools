package com.wj.dictionary.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wj.core.tools.redis.Executor;
import com.wj.core.tools.redis.RedisLockHelper;
import com.wj.core.tools.tree.ReflectionFieldName;
import com.wj.dictionary.constant.DictionaryConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.dictionary.entity.Dictionary;
import com.wj.dictionary.mapper.DictionaryMapper;
import com.wj.dictionary.service.DictionaryService;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {
    @Autowired
    RedisLockHelper redisLockHelper;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 字典树
     *
     * @param code 字典编码，为空查所有
     * @return
     */
    @Override
    public List<Tree<String>> dicTree(String code) {
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(code)) {
            Dictionary dictionary = baseMapper.selectOne(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getCode, code));
            if (dictionary != null) {
                queryWrapper.eq(Dictionary::getPid, dictionary.getId());
                queryWrapper.or().eq(Dictionary::getCode,code);
            } else {
                return Collections.emptyList();
            }
        }
        List<Dictionary> list = baseMapper.selectList(queryWrapper);
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey(ReflectionFieldName.getFieldName(Dictionary::getSort));
        // 自定义属性名 都要默认值的
        treeNodeConfig.setIdKey(ReflectionFieldName.getFieldName(Dictionary::getId));
        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(list, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId().toString());
                    tree.setParentId(treeNode.getPid().toString());
                    tree.setWeight(treeNode.getSort());
                    // 扩展属性 ...
                    tree.putExtra("code", treeNode.getCode());
                    tree.putExtra("name", treeNode.getValue());
                });
        return treeNodes;
    }

    /**
     * 根据字典编码查询值
     *
     * @param pcode
     * @param code
     * @return
     */
    @Override
    public String getValue(String pcode, String code) {
        if (StringUtils.isBlank(pcode) || StringUtils.isBlank(code)) {
            return null;
        }
        List<Dictionary> cacheList = redisLockHelper.getCacheList(DictionaryConstants.CACHE_DIC_PREFIX + pcode, new Executor() {
            @Override
            public List getDbList() {
                Dictionary parent = baseMapper.selectOne(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getCode, pcode));
                if (parent != null) {
                    return baseMapper.selectList(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getPid, parent.getId()));
                }
                return null;
            }
        });
        if (CollectionUtils.isNotEmpty(cacheList)) {
            List<Dictionary> collect = cacheList.stream().filter(item -> item.getCode().equals(code)).collect(Collectors.toList());
            if (collect.size() == 1) {
                return collect.get(0).getValue();
            }
        }
        return null;
    }
}
