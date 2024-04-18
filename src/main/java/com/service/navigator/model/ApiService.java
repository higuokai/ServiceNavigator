package com.service.navigator.model;

import com.intellij.psi.NavigatablePsiElement;
import com.service.navigator.constant.ApiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ApiService {

    private ApiType apiType;

    private NavigatablePsiElement psiElement;

    private String moduleName;

    public ApiService(ApiType apiType, NavigatablePsiElement psiElement) {
        this.apiType = apiType;
        this.psiElement = psiElement;
    }

    public abstract String getName();

    public abstract String getPath();

}
