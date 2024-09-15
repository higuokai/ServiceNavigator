package com.service.navigator.action.apis;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.service.navigator.service.MyProjectService;
import com.service.navigator.constant.TreeDataKey;
import com.service.navigator.model.ApiModule;
import com.service.navigator.utils.ApplicationContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModuleServiceContextEditAction extends DumbAwareAction {

    public ModuleServiceContextEditAction() {
        super("Edit Service Context", "", AllIcons.Modules.EditFolder);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }
        List<ApiModule> apiModules = TreeDataKey.SELECTED_MODULE_SERVICE.getData(e.getDataContext());
        if (CollectionUtils.isEmpty(apiModules)) {
            return;
        }
        ApiModule apiModule = apiModules.get(0);

        MyProjectService configuration = ApplicationContext.getConfiguration(project);

        String oldContext = configuration.getModuleContext(apiModule.getModuleName());

        String inputContext = Messages.showInputDialog(project, "Input module server.context", "Edit Server Context", null, StringUtils.isEmpty(oldContext)?null:oldContext, new InputValidator() {
            @Override
            public boolean checkInput(String inputString) {
                return StringUtils.isNotBlank(inputString);
            }
            @Override
            public boolean canClose(String inputString) {
                return true;
            }
        });
        if (StringUtils.isEmpty(inputContext)) {
            return;
        }
        configuration.setModuleContext(apiModule.getModuleName(), inputContext);
    }
}
