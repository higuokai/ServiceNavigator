package com.service.navigator.utils;

import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.service.navigator.config.NavigatorConfiguration;
import com.service.navigator.constant.Constant;
import com.service.navigator.view.apis.ApiTree;

import java.util.Map;
import java.util.Objects;

public class ApplicationContext {

    private static final Map<String, ApiTree> TREE_MAP = Maps.newHashMap();

    public static ToolWindow getToolWindow(Project project) {
        return Objects.requireNonNull(ToolWindowManager.getInstance(project).getToolWindow(Constant.TOOLWINDOW_ID));
    }

    public static NavigatorConfiguration getConfiguration(Project project) {
        return project.getService(NavigatorConfiguration.class);
    }

    public static void setApiTree(Project project, ApiTree apiTree) {
        TREE_MAP.put(IdeaUtils.getProjectKey(project), apiTree);
    }

    public static ApiTree getApiTree(Project project) {
        return TREE_MAP.get(IdeaUtils.getProjectKey(project));
    }
}
