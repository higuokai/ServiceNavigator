package com.service.navigator.action.apis;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.service.navigator.constant.Icons;
import com.service.navigator.utils.ApplicationContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RefreshAction extends DumbAwareAction {

    public RefreshAction() {
        super("Refresh", "", Icons.System.Refresh);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (Objects.isNull(project)) {
            return;
        }
        // 调用刷新按钮
        ApplicationContext.getApiTree(project).refreshApiTreeLater();
    }
}
