package com.service.navigator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.ui.ClickListener;
import com.intellij.util.ui.JBUI.Borders;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TextFieldAction  extends AnAction implements CustomComponentAction, DumbAware {

    protected JTextField myField;
    private final @NlsActions.ActionDescription String myDescription;
    private final Icon myIcon;

    public TextFieldAction(@NlsActions.ActionText String text, @NlsActions.ActionDescription String description, Icon icon, int initSize) {
        super(text, description, icon);
        this.myDescription = description;
        this.myIcon = icon;
        this.myField = new JTextField(initSize);
        this.myField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    e.consume();
                    TextFieldAction.this.perform();
                }

            }
        });
    }

    public void actionPerformed(@NotNull AnActionEvent e) {
        this.perform();
    }

    public void perform() {
    }

    public @NotNull JComponent createCustomComponent(@NotNull Presentation presentation, @NotNull String place) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(this.myIcon);
        label.setOpaque(true);
        label.setBackground(this.myField.getBackground());
        this.myField.setOpaque(true);
        panel.add(this.myField, "West");
        panel.add(label, "East");
        this.myField.setToolTipText(this.myDescription);
        label.setToolTipText(this.myDescription);
        Border originalBorder;
        if (SystemInfo.isMac) {
            originalBorder = BorderFactory.createLoweredBevelBorder();
        } else {
            originalBorder = this.myField.getBorder();
        }

        panel.setBorder(new CompoundBorder(Borders.empty(4, 0), originalBorder));
        this.myField.setOpaque(true);
        this.myField.setBorder(Borders.empty(0, 5));
        (new ClickListener() {
            public boolean onClick(@NotNull MouseEvent e, int clickCount) {
                TextFieldAction.this.perform();
                return true;
            }
        }).installOn(label);
        myField.setEnabled(false);
        return panel;
    }

    public String getValue() {
        return myField.getText();
    }
}
