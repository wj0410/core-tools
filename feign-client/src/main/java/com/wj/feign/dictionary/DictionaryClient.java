package com.wj.feign.dictionary;

import cn.hutool.core.lang.tree.Tree;
import com.wj.common.constants.CommonConstants;
import com.wj.core.tools.restful.result.R;
import com.wj.feign.dictionary.fallback.DictionaryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        value = CommonConstants.DICTIONARY_APPLICATION_NAME,
        fallback = DictionaryClientFallback.class
)
public interface DictionaryClient {
    String API_PREFIX = "/dictionary";
    String DIC_TREE = API_PREFIX + "/tree";
    String GET_DIC_VALUE = API_PREFIX + "/getDicValue";

    /**
     * 字典树
     *
     * @param code
     * @return
     */
    @GetMapping(DIC_TREE)
    R<List<Tree<String>>> dicTree(@RequestParam("code") String code);

    /**
     * 根据父code,获取子code的字典值
     *
     * @param pcode 父code
     * @param code  子code
     * @return 子code字典值
     */
    @GetMapping(GET_DIC_VALUE)
    R<String> getDicValue(@RequestParam("pcode") String pcode, @RequestParam("code") String code);

}
