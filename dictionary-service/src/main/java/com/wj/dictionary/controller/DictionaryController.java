package com.wj.dictionary.controller;

import cn.hutool.core.lang.tree.Tree;
import com.wj.core.tools.restful.controller.BaseController;
import com.wj.core.tools.restful.result.R;
import com.wj.dictionary.dto.DictionaryDTO;
import com.wj.dictionary.query.DictionaryQuery;
import com.wj.dictionary.service.DictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController<DictionaryService, DictionaryDTO, DictionaryQuery> {

    /**
     * 字典树
     *
     * @param code
     * @return
     */
    @GetMapping("/tree")
    public R<List<Tree<String>>> dicTree(String code) {
        return R.data(baseService.dicTree(code));
    }

    /**
     * 根据父code,获取子code的字典值
     *
     * @param pcode 父code
     * @param code  子code
     * @return 子code字典值
     */
    @GetMapping("/getDicValue")
    public R<String> getDicValue(@RequestParam("pcode") String pcode, @RequestParam("code") String code) {
        return R.data(baseService.getValue(pcode, code));
    }

    @GetMapping("/ignore/test")
    public R<String> ignoreTest() {
        return R.data("绕过网关通过字典服务直接访问：success");
    }
}
