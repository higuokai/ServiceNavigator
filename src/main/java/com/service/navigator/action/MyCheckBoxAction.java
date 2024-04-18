package com.service.navigator.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.util.ui.CheckBox;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MyCheckBoxAction extends ToggleAction implements CustomComponentAction {

    private boolean state;

    @Override
    public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
        return state;
    }

    @Override
    public void setSelected(@NotNull AnActionEvent anActionEvent, boolean state) {
        this.state = state;
    }

    @Override
    public @NotNull JComponent createCustomComponent(@NotNull Presentation presentation, @NotNull String place) {
        JPanel panel = new JPanel(new BorderLayout());
        CheckBox checkBox = new CheckBox("", this, "state");
        panel.add(checkBox);
        return panel;
    }
}
