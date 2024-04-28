package com.chess.card.api.utils.tree;

import com.chess.card.api.utils.DlBeanUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@UtilityClass
public class TreeUtil {

    public <T  extends TreeNode,V> List<T> buildTree(List<V> dataList, Class<T> clzss, String root){
        try{
            List<T> trees = new ArrayList<>();
            T node;
            for (V goodsLabel :dataList) {
                node = DlBeanUtil.copyPropertiesIgnoreNull(goodsLabel,(T) clzss.newInstance());
                trees.add(node);
            }
            return TreeUtil.build(trees, root);
        }catch (Exception e){
            log.error("buildTree error",e);
            return null;
        }
    }

    public <T extends TreeNode> List<T> build(List<T> treeNodes, String root) {
        List<T> result = new ArrayList<>();
        build(treeNodes,result,root);
        return result;
    }

    public <T extends TreeNode> void build(List<T> treeNodes,List<T> result, String root) {
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                result.add(treeNode);
                String childId = treeNode.getKey();
                List<T> childrenList = treeNode.getChildren();
                build(treeNodes,childrenList,childId);
                if(childrenList.size() <= 0){
                    treeNode.setChildren(null);
                }
            }
        }
    }
}
