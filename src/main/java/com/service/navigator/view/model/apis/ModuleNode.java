package com.service.navigator.view.model.apis;

import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.ui.treeStructure.SimpleNode;
import com.service.navigator.constant.Icons;
import com.service.navigator.model.ApiModule;
import com.service.navigator.model.ApiService;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class ModuleNode extends BaseNode{

    private ApiModule apiModule;

    protected ModuleNode(SimpleNode aParent, ApiModule apiModule) {
        super(aParent);
        this.apiModule = apiModule;

        getTemplatePresentation().setIcon(Icons.Node.ModuleNode);

        List<ApiService> apiServices = apiModule.getApiServices();

        // 提示太多, 去掉
//        getTemplatePresentation().setTooltip(String.valueOf(apiServices));

        this.childrenNodes.clear();
        this.childrenNodes = apiServices.stream().map(e -> new ServiceNode(this, e)).collect(Collectors.toList());
        SimpleNode parent = getParent();
        if (parent != null) {
            ((BaseNode) parent).cleanUpCache();
        }
        updateFrom(parent);
        childrenChanged();
        updateUpTo(this);
    }

    @Override
    public String getMenuId() {
        return "apis.moduleMenu";
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return childrenNodes.toArray(new SimpleNode[0]);
    }

    @Override
    public String getName() {
        return apiModule.getModuleName();
    }

    @Override
    public List<ApiService> getApiServices() {
        if (childrenNodes.isEmpty()) {
            return Collections.emptyList();
        } else {
            return childrenNodes.stream().map(e -> (ApiService)((ServiceNode)e).getApiService()).toList();
        }
    }

}
