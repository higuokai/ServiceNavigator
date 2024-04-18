package com.service.navigator.action.apis;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.util.PsiNavigateUtil;
import com.service.navigator.constant.Icons;
import com.service.navigator.constant.TreeDataKey;
import com.service.navigator.model.ApiService;
import com.service.navigator.model.spring.SpringApiService;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JumpToSourceAction extends DumbAwareAction {

    public JumpToSourceAction() {
        super("Jump to Source", "", Icons.System.Jump);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        List<ApiService> items = TreeDataKey.SELECTED_SERVICE.getData(e.getDataContext());
        boolean match = items != null && items.stream()
                .allMatch(restItem -> restItem instanceof SpringApiService);
        e.getPresentation().setVisible(match);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        List<ApiService> itemList = TreeDataKey.SELECTED_SERVICE.getData(e.getDataContext());
        if (CollectionUtils.isNotEmpty(itemList)) {
            itemList.stream().filter(item -> item instanceof SpringApiService)
                    .findFirst()
                    .ifPresent(item -> PsiNavigateUtil.navigate(((SpringApiService) item).getPsiElement()));
        }
    }
}
