package com.service.navigator.model.spring;

import com.intellij.psi.NavigatablePsiElement;
import com.service.navigator.constant.HttpMethod;
import com.service.navigator.model.ApiService;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Setter
@Getter
public class SpringApiService extends ApiService {

    private HttpMethod method;

    private String path;

    public SpringApiService(HttpMethod method, String path, NavigatablePsiElement psiElement) {
        super(psiElement);
        this.method = method;
        this.path = path;
    }

    @NotNull
    public SpringApiService copyWithParent(@Nullable SpringApiService parent) {
        SpringApiService apiService = new SpringApiService(this.getMethod(), this.getPath(), this.getPsiElement());
        if (parent != null) {
            apiService.setParent(parent);
        }
        return apiService;
    }

    private void setParent(@NotNull SpringApiService parent) {
        if ((this.getMethod() == null || this.getMethod() == HttpMethod.REQUEST) && parent.getMethod() != null) {
            this.setMethod(parent.getMethod());
        }
        String parentPath = parent.getPath();
        if (parentPath != null && parentPath.endsWith("/")) {
            // 去掉末尾的斜杠
            parentPath = parentPath.substring(0, parentPath.length() - 1);
        }
        this.setPath(parentPath + this.getPath());
    }

    @Override
    public String getName() {
        return path;
    }
}
