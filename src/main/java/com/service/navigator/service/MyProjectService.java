package com.service.navigator.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Setter
@Getter
@State(
        name = "RestfulApis",
        storages = @Storage("service_navigator/apis_options.xml"),
        category = SettingsCategory.CODE
)
@Service(Service.Level.PROJECT)
public class MyProjectService implements PersistentStateComponent<MyProjectService> {

    private Map<String, Integer> modulePortMap = Maps.newHashMap();

    private Map<String, String> moduleContextMap = Maps.newHashMap();

    private Set<String> disabledModules = Sets.newHashSet();

    private boolean scanServiceWithLib = false;

    public Integer getModulePort(String moduleName) {
        return modulePortMap.get(moduleName);
    }

    public String getModuleContext(String moduleName) {
        return moduleContextMap.get(moduleName);
    }

    public void setModulePort(String moduleName, Integer port) {
        modulePortMap.put(moduleName, port);
    }

    public void setModuleContext(String moduleName, String moduleContext) {
        moduleContextMap.put(moduleName, moduleContext);
    }

    /**
     * 模块过滤
     */
    private final ModuleFilter moduleFilter = new ModuleFilter();

    public class ModuleFilter {
        private final Set<String> allModules = new HashSet<>();

        public List<String> getAllModules() {
            return new ArrayList<>(allModules);
        }

        public boolean isVisible(String module) {
            return !disabledModules.contains(module);
        }

        public void setModuleVisible(String element, boolean isMarked) {
            if (isMarked) {
                disabledModules.remove(element);
            } else {
                disabledModules.add(element);
            }
        }

        public void setAllModules(List<String> allModules) {
            this.allModules.clear();
            this.allModules.addAll(allModules);
        }
    }

    @Override
    public @Nullable MyProjectService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MyProjectService state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
