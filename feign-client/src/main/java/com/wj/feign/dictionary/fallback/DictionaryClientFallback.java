package com.wj.feign.dictionary.fallback;

import cn.hutool.core.lang.tree.Tree;
import com.wj.core.tools.restful.result.R;
import com.wj.feign.dictionary.DictionaryClient;

import java.util.List;

public class DictionaryClientFallback implements DictionaryClient {
    @Override
    public R<List<Tree<String>>> dicTree(String code) {
        return R.fail("远程调用 dicTree 失败！");
    }

    @Override
    public R<String> getDicValue(String pcode, String code) {
        return R.fail("远程调用 getDicValue 失败！");
    }
}
