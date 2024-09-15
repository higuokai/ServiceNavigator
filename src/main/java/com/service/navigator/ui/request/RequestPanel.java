package com.service.navigator.ui.request;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.service.navigator.action.apis.RefreshAction;

public class RequestPanel extends SimpleToolWindowPanel {

    public RequestPanel(Project project) {
        super(true);

        // 工具条
        ActionToolbar actionToolbar = initActionToolbar();
        actionToolbar.setTargetComponent(this);
        setToolbar(actionToolbar.getComponent());

        RequestCotent content = new RequestCotent(project);
        setContent(content);
    }

    private ActionToolbar initActionToolbar() {
        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new RefreshAction());
        return actionManager.createActionToolbar(ActionPlaces.TOOLWINDOW_TOOLBAR_BAR,
                actionGroup,
                true);
    }
}
