//import cn.hutool.core.lang.tree.Tree;
//import cn.hutool.core.lang.tree.TreeNodeConfig;
//import cn.hutool.core.lang.tree.TreeUtil;
//import com.wj.core.tools.tree.ReflectionFieldName;
//import com.wj.core.tools.tree.TreeObj;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TreeTest {
//    @Test
//    public static void main(String[] args) {
//        List<TreeObj> list = new ArrayList<>();
//        for (int i = 0; i < 100000; i++) {
//            TreeObj o = new TreeObj();
//            o.setId(i + "");
//            o.setName(i + "");
//            o.setPid("0");
//            list.add(o);
//        }
//        // 配置
//        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
//        treeNodeConfig.setWeightKey(ReflectionFieldName.getFieldName(TreeObj::getSort));
//        // 自定义属性名 都要默认值的
//        treeNodeConfig.setIdKey(ReflectionFieldName.getFieldName(TreeObj::getId));
//        //转换器
//        List<Tree<String>> treeNodes = TreeUtil.build(list, "0", treeNodeConfig,
//                (treeNode, tree) -> {
//                    tree.setId(treeNode.getId());
//                    tree.setParentId(treeNode.getPid());
//                    tree.setWeight(treeNode.getSort());
//                    // 扩展属性 ...
//                    tree.putExtra("name", treeNode.getName());
//                });
//    }
////    /**
////     * 效率太低，不适用
////     *
////     * @return
////     */
////    public static <T> List<Tree<String>> formatTrees(List<T> list, PropertyFunc<T, ?> idFunc,
////                                                     PropertyFunc<T, ?> pidFunc, PropertyFunc<T, ?> sortFunc,
////                                                     Map<String, String> extraAttr) {
////        if (CollectionUtils.isEmpty(list)) {
////            return Collections.emptyList();
////        }
////        //配置
////        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
////        treeNodeConfig.setWeightKey(ReflectionFieldName.getFieldName(sortFunc));
////        // 自定义属性名 都要默认值的
////        treeNodeConfig.setIdKey(ReflectionFieldName.getFieldName(idFunc));
////        //转换器
////        List<Tree<String>> treeNodes = TreeUtil.build(list, "0", treeNodeConfig,
////                (treeNode, tree) -> {
////                    tree.setId(ReflectionFieldName.getFiledValue(idFunc, treeNode));
////                    tree.setParentId(ReflectionFieldName.getFiledValue(pidFunc, treeNode));
////                    tree.setWeight(ReflectionFieldName.getFiledValue(sortFunc, treeNode));
////                    // 扩展属性 ...
////                    for (Map.Entry<String, String> map : extraAttr.entrySet()) {
////                        tree.putExtra(map.getKey(),
////                                ReflectionFieldName.getFiledValue(map.getValue(), treeNode));
////                    }
////                });
////        return treeNodes;
////    }
//}
