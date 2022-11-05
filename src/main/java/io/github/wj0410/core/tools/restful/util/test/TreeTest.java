package io.github.wj0410.core.tools.restful.util.test;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import io.github.wj0410.core.tools.tree.ReflectionFieldName;
import io.github.wj0410.core.tools.tree.TreeObj;

import java.util.ArrayList;
import java.util.List;

public class TreeTest {
    public static void main(String[] args) {
        List<TreeObj> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            TreeObj o = new TreeObj();
            o.setId(i + "");
            o.setName(i + "");
            o.setPid("0");
            list.add(o);
        }
        // 配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey(ReflectionFieldName.getFieldName(TreeObj::getSort));
        // 自定义属性名 都要默认值的
        treeNodeConfig.setIdKey(ReflectionFieldName.getFieldName(TreeObj::getId));
        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(list, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getPid());
                    tree.setWeight(treeNode.getSort());
                    // 扩展属性 ...
                    tree.putExtra("name", treeNode.getName());
                });
    }
}
