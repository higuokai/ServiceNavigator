package com.service.navigator.action.apis;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.service.navigator.constant.Icons;
import org.jetbrains.annotations.NotNull;

public class ApiTypeFilterAction extends ToggleAction {

    public ApiTypeFilterAction() {
        super("Api Type Filter", "", Icons.System.ApiTypeFilter);
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
        return false;
    }

    @Override
    public void setSelected(@NotNull AnActionEvent anActionEvent, boolean b) {

    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread();
    }
}
