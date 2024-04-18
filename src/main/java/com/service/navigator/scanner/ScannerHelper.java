package com.service.navigator.scanner;

import com.google.common.collect.Lists;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.service.navigator.model.ApiService;
import com.service.navigator.utils.ApplicationContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface ScannerHelper {

    static List<ScannerHelper> getJavaHelpers() {
        return Lists.newArrayList(JavaSpringScanner.getInstance());
    }

    Collection<ApiService> getService(@NotNull Project project, @NotNull Module module);

    static GlobalSearchScope getModuleScope(@NotNull Project project, @NotNull Module module) {
        boolean scanServiceWithLib = ApplicationContext.getConfiguration(project).isScanServiceWithLib();
        if (scanServiceWithLib) {
            return module.getModuleWithLibrariesScope();
        } else {
            return module.getModuleScope();
        }
    }

}
