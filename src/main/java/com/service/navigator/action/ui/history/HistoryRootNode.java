package com.service.navigator.action.ui.history;

import com.intellij.ui.treeStructure.SimpleNode;
import com.service.navigator.view.model.apis.BaseNode;

public class HistoryRootNode extends BaseNode {

    public HistoryRootNode(SimpleNode parent) {
        super(parent);
    }

    @Override
    public String getMenuId() {
        return "history.root.node";
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return childrenNodes.toArray(new SimpleNode[0]);
    }
}
