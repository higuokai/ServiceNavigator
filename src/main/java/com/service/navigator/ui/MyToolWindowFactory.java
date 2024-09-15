package com.service.navigator.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.service.navigator.ui.apis.ApisPanel;
import org.jetbrains.annotations.NotNull;

/**
 * 方法入口
 */
public class MyToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.getInstance();
        ContentManager contentManager = toolWindow.getContentManager();

        // 根据配置, 加载不同的窗口(先加载api)
        ApisPanel apiPanel = new ApisPanel(project);
        Content apisContent = contentFactory.createContent(apiPanel, "", false);
//        apisContent.setIcon(Icons.System.Spring);
//        apisContent.putUserData(ToolWindow.SHOW_CONTENT_ICON, true);
        contentManager.addContent(apisContent);

//        RequestPanel requestPanel = new RequestPanel(project);
//        Content requestContent = contentFactory.createContent(requestPanel, "Post", true);
//        requestContent.setIcon(Icons.System.Xxl);
//        requestContent.putUserData(ToolWindow.SHOW_CONTENT_ICON, true);
//        contentManager.addContent(requestContent);
    }
}
