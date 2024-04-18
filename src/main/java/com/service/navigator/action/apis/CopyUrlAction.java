package com.service.navigator.action.apis;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.service.navigator.constant.Icons;
import com.service.navigator.constant.TreeDataKey;
import com.service.navigator.model.ApiService;
import com.service.navigator.utils.IdeaUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CopyUrlAction extends DumbAwareAction {

    public CopyUrlAction() {
        super("Copy Url", "", Icons.System.Copy);
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
        IdeaUtils.copyToClipboard(apiService.getPath());
    }
}
