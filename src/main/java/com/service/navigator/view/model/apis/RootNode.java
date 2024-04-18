package com.service.navigator.view.model.apis;

import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.SimpleNode;
import com.service.navigator.constant.Icons;
import com.service.navigator.model.ApiModule;

import java.util.List;
import java.util.stream.Collectors;

public class RootNode extends BaseNode {

    private int serviceCount;

    public RootNode(Project project, NodeDescriptor<String> nodeDescriptor) {
        super(project, nodeDescriptor);
        getTemplatePresentation().setIcon(Icons.Node.TreeRootNode);
        getTemplatePresentation().setPresentableText(getName());
    }

    @Override
    public String getMenuId() {
        return "apis.rootMenu";
    }

    @Override
    public String getName() {
        return serviceCount > 0 ? String.format("Found %d services", serviceCount) : "No service found";
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return childrenNodes.toArray(new SimpleNode[0]);
    }

    public void updateModuleNodes(List<ApiModule> apiModules) {
        cleanUpCache();
        childrenNodes = apiModules.stream()
                .map(module -> new ModuleNode(this, module))
                .collect(Collectors.toList());

        serviceCount = apiModules.stream().map(apiModule -> apiModule.getApiServices().size()).mapToInt(i -> i).sum();

        updateFrom(getParent());
        childrenChanged();
    }
}
