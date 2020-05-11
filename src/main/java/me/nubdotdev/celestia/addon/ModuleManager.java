package me.nubdotdev.celestia.addon;

import java.util.HashSet;
import java.util.Set;

public abstract class ModuleManager<T extends Module> {

    private final Set<T> modules;

    protected ModuleManager() {
        this.modules = new HashSet<>();
    }

    public void registerModule(T module) {
        modules.add(module);
    }

    public void unregisterModule(T module) {
        modules.remove(module);
    }

    public void unregisterModules() {
        for (T module : modules)
            unregisterModule(module);
    }

    public void reloadModule(T module) {
        unregisterModule(module);
        registerModule(module);
    }

    public void reloadModules() {
        for (T module : modules)
            reloadModule(module);
    }

}
