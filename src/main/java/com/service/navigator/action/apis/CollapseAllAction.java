package com.service.navigator.action.apis;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.service.navigator.utils.ApplicationContext;
import com.service.navigator.ui.apis.ApiTree;
import org.jetbrains.annotations.NotNull;

public class CollapseAllAction extends DumbAwareAction {

    public CollapseAllAction() {
        super("Collapse All", "", AllIcons.Actions.Collapseall);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        ApiTree apiTree = ApplicationContext.getApiTree(project);
        ToolWindow toolWindow = ApplicationContext.getToolWindow(project);
        if (!toolWindow.isDisposed()) {
            apiTree.expandAll(false);
            return;
        }
        toolWindow.activate(() -> apiTree.expandAll(false));
    }
}
