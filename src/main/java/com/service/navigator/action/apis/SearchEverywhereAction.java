package com.service.navigator.action.apis;

import com.intellij.featureStatistics.FeatureUsageTracker;
import com.intellij.icons.AllIcons;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.service.navigator.action.search.GotoRequestContributor;
import com.service.navigator.action.search.GotoRequestProvider;
import com.service.navigator.action.search.RequestFilteringGotoByModel;
import com.service.navigator.action.search.RestSearchItem;
import com.service.navigator.constant.HttpMethod;
import org.jetbrains.annotations.NotNull;

public class SearchEverywhereAction extends GotoActionBase implements DumbAware {

    public SearchEverywhereAction() {
        getTemplatePresentation().setText("Search");
        getTemplatePresentation().setDescription("");
        getTemplatePresentation().setIcon(AllIcons.Actions.Search);
    }

    @Override
    protected void gotoActionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project == null) {
            return;
        }
        // 显示tips
        FeatureUsageTracker.getInstance().triggerFeatureUsed("navigation.popup.service");

        ChooseByNameContributor[] contributors = {
                new GotoRequestContributor(anActionEvent.getData(LangDataKeys.MODULE)),
        };

        RequestFilteringGotoByModel model = new RequestFilteringGotoByModel(project, contributors);

        GotoActionCallback<HttpMethod> callback = new GotoActionCallback<HttpMethod>() {

            @Override
            public void elementChosen(ChooseByNamePopup popup, Object element) {
                if (element instanceof RestSearchItem navigationItem) {
                    if (navigationItem.canNavigate()) {
                        navigationItem.navigate(true);
                    }
                }
            }
        };

        GotoRequestProvider provider = new GotoRequestProvider(getPsiContext(anActionEvent));
        showNavigationPopup(
                anActionEvent, model, callback,
                "Request Mapping Url matching pattern...",
                true,
                true,
                (ChooseByNameItemProvider) provider
        );
    }

}
