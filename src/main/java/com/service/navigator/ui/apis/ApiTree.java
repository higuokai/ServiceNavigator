package com.service.navigator.ui.apis;

import com.intellij.ide.util.treeView.AbstractTreeStructure;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.AppUIUtil;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import com.intellij.util.ui.tree.TreeUtil;
import com.service.navigator.constant.TreeDataKey;
import com.service.navigator.model.ApiModule;
import com.service.navigator.model.ApiService;
import com.service.navigator.utils.ApiServiceUtil;
import com.service.navigator.utils.ApplicationContext;
import com.service.navigator.utils.IdeaUtils;
import com.service.navigator.ui.apis.model.BaseNode;
import com.service.navigator.ui.apis.model.ModuleNode;
import com.service.navigator.ui.apis.model.RootNode;
import com.service.navigator.ui.apis.model.ServiceNode;
import lombok.Getter;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ApiTree extends JPanel implements DataProvider, Disposable {

    private final Project project;

    private final SimpleTree apisTree;

    @Getter
    private final StructureTreeModel<AbstractTreeStructure> treeModel;

    private final RootNode rootNode;

    public ApiTree(Project project) {
        this.project = project;

        rootNode = new RootNode(project, null);

        treeModel = new StructureTreeModel<>(new SimpleTreeStructure() {
            @Override
            public @NotNull Object getRootElement() {
                return rootNode;
            }
        }, null, this);

        apisTree = new SimpleTree(new AsyncTreeModel(treeModel, this));
        apisTree.setRootVisible(true);
        apisTree.setShowsRootHandles(true);
        apisTree.getEmptyText().clear();
        apisTree.setBorder(BorderFactory.createEmptyBorder());
        apisTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        // 布局
        setLayout(new BorderLayout());
        // 带滚动条的panel
        add(ScrollPaneFactory.createScrollPane(apisTree), BorderLayout.CENTER);

        // 树列表点击事件
        initEvent();

        ApplicationContext.setApiTree(project, this);

        this.refreshApiTreeLater();
    }

    public void refreshApiTreeLater() {
        // 更新api树
        IdeaUtils.smartInvokeLater(project, () -> {
            ToolWindow toolWindow = ApplicationContext.getToolWindow(project);
            if (toolWindow.isDisposed() || !toolWindow.isVisible()) {
                toolWindow.show(this::refreshApiTree);
            } else {
                refreshApiTree();
            }
        });
    }

    /**
     * 更新树列表
     */
    public void refreshApiTree() {
        Runnable backgroundTask = () -> {
            List<ApiModule> restModules = IdeaUtils.runRead(project, () -> {
                Map<String, List<ApiService>> apiServices = ApiServiceUtil.getApis(project);
                return apiServices
                        .entrySet()
                        .stream()
                        .map(entry -> new ApiModule(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
            });
            // 重新渲染树
            AppUIUtil.invokeOnEdt(() -> rootNode.updateModuleNodes(restModules));
        };
        IdeaUtils.backBackgroundTask("ApiNavigator Search Restful Apis...", project, backgroundTask);
    }


    /**
     * 点击事件
     */
    private void initEvent() {
        Function<Collection<? extends BaseNode>, String> getMenuIdFunction = nodes -> {
            return Optional.ofNullable(nodes)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(e -> StringUtils.isNotBlank(e.getMenuId()))
                    .findFirst()
                    .map(BaseNode::getMenuId)
                    .orElse(null);
        };

        apisTree.addMouseListener(new PopupHandler() {
            @Override
            public void invokePopup(Component comp, int x, int y) {
                String menuId = getMenuIdFunction.apply(getSelectedNodes());
                if (menuId != null) {
                    final ActionManager actionManager = ActionManager.getInstance();
                    final ActionGroup actionGroup = (ActionGroup) actionManager.getAction(menuId);
                    if (actionGroup != null) {
                        JPopupMenu component = actionManager.createActionPopupMenu(ActionPlaces.TOOLWINDOW_CONTENT, actionGroup).getComponent();
                        component.show(comp, x, y);
                    }
                }
            }
        });
    }

    /**
     * 返回节点数据
     */
    @Override
    public @Nullable Object getData(@NotNull @NonNls String dataId) {
        if (TreeDataKey.ALL_SERVICE.is(dataId)) {
            return rootNode.getChildrenNodes();
        }
        if (TreeDataKey.ALL_MODULE.is(dataId)) {
            return rootNode.getChildrenNodes().stream()
                    .map(moduleNode -> ((ModuleNode)moduleNode).getApiModule().getModuleName())
                    .distinct()
                    .collect(Collectors.toList());
        }
        // getMenu保证了不能选择不同类型节点
        if (TreeDataKey.SELECTED_SERVICE.is(dataId)) {
            List<ApiService> list = Lists.newArrayList();
            for (BaseNode node : getSelectedNodes()) {
                if (node instanceof RootNode) {
                    return rootNode.getApiServices();
                } else if (node instanceof ModuleNode) {
                    list.addAll(node.getApiServices());
//                    return node;
                } else if (node instanceof ServiceNode) {
                    list.add(((ServiceNode)node).getApiService());
                }
            }
            return list;
        }
        if (TreeDataKey.SELECTED_MODULE_SERVICE.is(dataId)) {
            return getSelectedNodes().stream()
                    .filter(node -> node instanceof ModuleNode)
                    .map(node -> ((ModuleNode)node).getApiModule())
                    .collect(Collectors.toList());
        }
        return null;
    }



    private java.util.List<BaseNode> getSelectedNodes() {
        final List<BaseNode> filtered = new ArrayList<>();
        TreePath[] treePaths = apisTree.getSelectionPaths();
        if (treePaths != null) {
            for (TreePath treePath : treePaths) {
                SimpleNode nodeFor = apisTree.getNodeFor(treePath);
                if (!(nodeFor instanceof BaseNode)) {
                    filtered.clear();
                    break;
                }
                filtered.add((BaseNode) nodeFor);
            }
        }
        return filtered;
    }

    @Override
    public void dispose() {
        // 销毁事件
        ApplicationContext.close(this.project);
    }

    public void expandAll(boolean expand) {
        if (expand) {
            TreeUtil.expandAll(apisTree);
        } else {
            TreeUtil.collapseAll(apisTree, false, 1);
        }
    }

}
