package com.service.navigator.ui.request;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.service.navigator.ui.request.component.ClickLabelTextField;
import com.service.navigator.ui.request.component.ClickableLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class RequestCotent extends JPanel {
    
    private final Project project;

    private final String[] options = {"GET", "POST", "PUT"};
    
    public RequestCotent(Project project) {
        this.project = project;
        
        setLayout(new MigLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new MigLayout());
        topPanel.add(new ClickableLabel("Home"), "gap 1"); // 第一个面包屑链接，使用 "gap 10" 设置间距
        topPanel.add(new JLabel(">"), "gap 1"); // 分隔符，可以使用 JLabel 或文本
        topPanel.add(new ClickableLabel("Products"), "gap 1");
        topPanel.add(new JLabel(">"), "gap 1");
        topPanel.add(new ClickLabelTextField("Category"), "gap 1");

//        add(topPanel, "wrap"); // "wrap" 表示换行，将面包屑放在最顶端
        add(topPanel, "cell 0 0, span");
        
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.addItem("GET");
        comboBox.addItem("POST");
        add(comboBox, "cell 0 1, span");
    }
    
    
}
