package com.chess.card.api.utils.tree;

import java.util.List;

/**
 *     title: 'parent '
 *     key: '0-0'
 *     children:
 */
public interface TreeNode {
    public String getKey();
    public String getParentId();

    public <T  extends TreeNode> List<T> getChildren();

    public void setChildren(List<TreeNode> children);
}
