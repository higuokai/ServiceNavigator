package com.service.navigator.action.apis;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.service.navigator.service.MyProjectService;
import com.service.navigator.constant.Icons;
import com.service.navigator.constant.TreeDataKey;
import com.service.navigator.model.ApiService;
import com.service.navigator.utils.ApplicationContext;
import com.service.navigator.utils.IdeaUtils;
import com.service.navigator.utils.PathUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CopyFullUrlAction extends DumbAwareAction {

    public CopyFullUrlAction() {
        super("Copy Full Url", "", Icons.System.CopyFull);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }
        List<ApiService> serviceItems = TreeDataKey.SELECTED_SERVICE.getData(e.getDataContext());
        if (CollectionUtils.isEmpty(serviceItems)) {
            return;
        }
        ApiService apiService = serviceItems.get(0);
        // 获取模块名
        String moduleName = apiService.getModuleName();

        MyProjectService configuration = ApplicationContext.getConfiguration(project);

        Integer port = configuration.getModulePort(moduleName);
        String context = configuration.getModuleContext(moduleName);

        String url = PathUtil.buildUrl("http", port, context, apiService.getPath());
        IdeaUtils.copyToClipboard(url);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
    }
}
