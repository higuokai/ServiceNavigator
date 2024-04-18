package com.service.navigator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApiModule {

    private String moduleName;

    private List<ApiService> apiServices;

    public ApiModule(String moduleName, List<ApiService> apiServices) {
        this.moduleName = moduleName;
        this.apiServices = apiServices;
    }

    @Override
    public String toString() {
        return moduleName;
    }
}
