package com.service.navigator.utils;

import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.service.navigator.model.ApiService;
import com.service.navigator.service.MyProjectService;
import com.service.navigator.constant.Constant;
import com.service.navigator.ui.apis.ApiTree;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApplicationContext {

    private static final Map<String, ApiTree> TREE_MAP = Maps.newHashMap();

    private static final Map<String, Map<String, List<ApiService>>> API_CACHE = Maps.newHashMap();

    public static ToolWindow getToolWindow(Project project) {
        return Objects.requireNonNull(ToolWindowManager.getInstance(project).getToolWindow(Constant.TOOLWINDOW_ID));
    }

    public static MyProjectService getConfiguration(Project project) {
        return project.getService(MyProjectService.class);
    }

    public static void setApiTree(Project project, ApiTree apiTree) {
        TREE_MAP.put(IdeaUtils.getProjectKey(project), apiTree);
    }

    public static ApiTree getApiTree(Project project) {
        return TREE_MAP.get(IdeaUtils.getProjectKey(project));
    }

    public static void close(Project project) {
        String projectKey = IdeaUtils.getProjectKey(project);
        TREE_MAP.remove(projectKey);
        API_CACHE.remove(projectKey);
    }

    public static void removeApiCache(Project project) {
        API_CACHE.remove(IdeaUtils.getProjectKey(project));
    }
    
    public static Map<String, List<ApiService>> getApiCache(Project project) {
        return API_CACHE.get(IdeaUtils.getProjectKey(project));
    }

    public static void setApiCache(Project project, Map<String, List<ApiService>> cacheVal) {
        API_CACHE.put(IdeaUtils.getProjectKey(project), cacheVal);
    }
    
}
