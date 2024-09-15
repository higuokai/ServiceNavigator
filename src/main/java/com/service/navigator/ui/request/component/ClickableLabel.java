package com.service.navigator.ui.request.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickableLabel extends JLabel implements MouseListener {

    private Color originalColor; // 保存原始颜色
    private Color hoverColor = Color.BLUE; // 鼠标悬停时的颜色
    
    public ClickableLabel(String text) {
        super(text);
        this.originalColor = this.getForeground(); // 保存原始颜色
        this.addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 在这里处理鼠标点击事件，比如跳转到相应页面等
        JOptionPane.showMessageDialog(null, "Clicked on " + this.getText());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setForeground(hoverColor); // 鼠标进入时改变颜色
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setForeground(originalColor); // 鼠标退出时恢复原始颜色
    }
}
