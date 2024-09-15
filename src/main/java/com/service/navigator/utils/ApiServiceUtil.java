package com.service.navigator.utils;

import com.google.common.collect.Maps;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.service.navigator.service.MyProjectService;
import com.service.navigator.model.ApiService;
import com.service.navigator.scanner.ScannerHelper;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ApiServiceUtil {

    public static Map<String, List<ApiService>> getApis(@NotNull Project project) {
        Map<String, List<ApiService>> apiCache = ApplicationContext.getApiCache(project);
        if (apiCache != null) {
            return apiCache;
        }
        apiCache = getApis(project, ModuleManager.getInstance(project).getModules());
        ApplicationContext.setApiCache(project, apiCache);
        return apiCache;
    }

    public static List<ApiService> getApisForEditor(@NotNull Project project) {
        return Arrays.stream(ModuleManager.getInstance(project).getModules())
                .flatMap(module -> getModuleApis(project, module).stream())
                .collect(Collectors.toList());
    }

    public static Map<String, List<ApiService>> getApis(@NotNull Project project, @NotNull Module[] modules) {
        // 初始化模块过滤
        MyProjectService.ModuleFilter moduleFilter = ApplicationContext.getConfiguration(project).getModuleFilter();
        moduleFilter.setAllModules(Arrays.stream(modules).map(Module::getName).collect(Collectors.toList()));

        Map<String, List<ApiService>> resultMap = Maps.newHashMapWithExpectedSize(modules.length);

        for (Module module : modules) {
            if (!moduleFilter.isVisible(module.getName())) {
                continue;
            }
            List<ApiService> moduleApis = getModuleApis(project, module);
            if (CollectionUtils.isEmpty(moduleApis)) {
                continue;
            }
            resultMap.put(module.getName(), moduleApis);
        }
        return resultMap;
    }

    public static List<ApiService> getModuleApis(@NotNull Project project, @NotNull Module module) {
        List<ApiService> resultList = new ArrayList<>();
        List<ScannerHelper> javaHelpers = ScannerHelper.getJavaHelpers();
        for (ScannerHelper javaHelper : javaHelpers) {
            Collection<ApiService> services = javaHelper.getService(project, module);
            if (CollectionUtils.isEmpty(services)) {
                continue;
            }
            resultList.addAll(services);
        }
        return resultList;
    }

    public static String getCombinedPath(@NotNull String typePath, @NotNull String methodPath) {
        if (typePath.isEmpty()) {
            typePath = "/";
        } else if (!typePath.startsWith("/")) {
            typePath = "/".concat(typePath);
        }

        if (!methodPath.isEmpty()) {
            if (!methodPath.startsWith("/") && !typePath.endsWith("/")) {
                methodPath = "/".concat(methodPath);
            }
        }

        return (typePath + methodPath).replace("//", "/");
    }

}
