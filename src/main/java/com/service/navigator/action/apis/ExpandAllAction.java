package com.service.navigator.action.apis;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.service.navigator.utils.ApplicationContext;
import com.service.navigator.ui.apis.ApiTree;
import org.jetbrains.annotations.NotNull;

public class ExpandAllAction extends DumbAwareAction {

    public ExpandAllAction() {
        super("Expand All", "", AllIcons.Actions.Expandall);
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
            apiTree.expandAll(true);
            return;
        }
        toolWindow.activate(() -> apiTree.expandAll(true));
    }
}
