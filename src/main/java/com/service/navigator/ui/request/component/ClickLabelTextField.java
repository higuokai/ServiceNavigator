package com.service.navigator.ui.request.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickLabelTextField extends JPanel implements MouseListener {
    private JLabel label;
    private JTextField textField;
    private boolean isEditable;

    public ClickLabelTextField(String text) {
        setLayout(new CardLayout());
        label = new JLabel(text);
        label.addMouseListener(this); // 添加鼠标监听器
        add(label, "label");

        textField = new JTextField(text);
        textField.addMouseListener(this); // 添加鼠标监听器
        textField.setVisible(false); // 最开始设置为不可见
        add(textField, "text");
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        label.setVisible(false); // 点击后隐藏 label
        textField.setText(label.getText()); // 设置输入框的文本为 label 的文本
        textField.setVisible(true); // 显示输入框
        textField.requestFocus(); // 让输入框获得焦点
        textField.selectAll(); // 选择输入框中的所有文本
    }

    public void handleOutsideClick() {
        if (textField.isVisible()) {
            label.setVisible(true);
            textField.setVisible(false);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
