package com.service.navigator.model.xxl;

import com.intellij.psi.NavigatablePsiElement;
import com.service.navigator.constant.ApiType;
import com.service.navigator.model.ApiService;

public class XxlApiService extends ApiService {

    private String executorHandler;

    public XxlApiService(NavigatablePsiElement psiElement) {
        super(ApiType.Xxl, psiElement);
    }

    @Override
    public String getName() {
        return executorHandler;
    }

    @Override
    public String getPath() {
        return executorHandler;
    }
}
