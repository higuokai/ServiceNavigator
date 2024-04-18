package com.service.navigator.action.apis;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.Project;
import com.service.navigator.utils.ApplicationContext;
import org.jetbrains.annotations.NotNull;

public class EnableLibraryAction extends ToggleAction {

    public EnableLibraryAction() {
        super("Enable Library", "", AllIcons.ObjectBrowser.ShowLibraryContents);
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project == null) {
            return false;
        }
        return ApplicationContext.getConfiguration(project).isScanServiceWithLib();
    }

    @Override
    public void setSelected(@NotNull AnActionEvent anActionEvent, boolean state) {
        Project project = anActionEvent.getProject();
        if (project == null) {
            return;
        }
        ApplicationContext.getConfiguration(project).setScanServiceWithLib(state);
        ApplicationContext.getApiTree(project).refreshApiTree();
    }
}
