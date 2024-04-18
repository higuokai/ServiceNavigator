package com.service.navigator.view.model.apis;

import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.util.PsiNavigateUtil;
import com.service.navigator.constant.Icons;
import com.service.navigator.model.ApiService;
import com.service.navigator.model.spring.SpringApiService;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.InputEvent;

@Setter
@Getter
public class ServiceNode extends BaseNode{

    private ApiService apiService;

    public ServiceNode(SimpleNode aParent, ApiService apiService) {
        super(aParent);

        this.apiService = apiService;
        Icon icon = Icons.Node.getServiceNodeIcon(apiService);
        getTemplatePresentation().setIcon(icon);

        NavigatablePsiElement psiElement = apiService.getPsiElement();
        if (psiElement instanceof PsiMethod) {
            PsiClass containingClass = ((PsiMethod) psiElement).getContainingClass();
            if (containingClass != null) {
                getTemplatePresentation().setTooltip(containingClass.getName() + "#" + psiElement.getName());
            }
        }
    }

    @Override
    public void handleDoubleClickOrEnter(SimpleTree tree, InputEvent inputEvent) {
        ServiceNode selectedNode = (ServiceNode) tree.getSelectedNode();
        if (selectedNode != null && selectedNode.apiService instanceof SpringApiService) {
            PsiElement psiElement = selectedNode.apiService.getPsiElement();
            if (!psiElement.isValid()) {
//                LOG.info("psiMethod is invalid: " + psiElement);
                return;
            }
            PsiNavigateUtil.navigate(psiElement);
        }
    }

    @Override
    public String getMenuId() {
        return "apis.requestMenu";
    }

    @Override
    public String getName() {
        return apiService.getName();
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return new SimpleNode[0];
    }
}
