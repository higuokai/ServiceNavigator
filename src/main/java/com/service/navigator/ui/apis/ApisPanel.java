package com.service.navigator.ui.apis;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.service.navigator.action.apis.*;

public class ApisPanel extends SimpleToolWindowPanel {

    public ApisPanel(Project project) {
        super(true);

        // 工具条
        ActionToolbar actionToolbar = initActionToolbar();
        setToolbar(actionToolbar.getComponent());
        // api树
        ApiTree apiTree = new ApiTree(project);
        actionToolbar.setTargetComponent(apiTree);
        setContent(apiTree);
    }

    private ActionToolbar initActionToolbar() {
        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup actionGroup = new DefaultActionGroup();

        // actions
        actionGroup.addAction(new RefreshAction());
        actionGroup.addAction(new SearchEverywhereAction());
        actionGroup.addSeparator();

        actionGroup.addAction(new ExpandAllAction());
        actionGroup.add(new CollapseAllAction());
        actionGroup.addSeparator();

        actionGroup.addAction(new ModuleFilterAction());
//        actionGroup.addAction(new ApiTypeFilterAction());
        actionGroup.addAction(new EnableLibraryAction());
//        actionGroup.addAction(new SearchHistoryAction());

        return actionManager.createActionToolbar(ActionPlaces.TOOLWINDOW_TOOLBAR_BAR,
                actionGroup,
                true);
    }
}
