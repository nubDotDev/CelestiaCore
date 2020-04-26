package me.nubdotdev.celestia.addon;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.command.CelestiaCommand;

import java.util.HashSet;
import java.util.Set;

public abstract class ModuleHandler<T extends Module> {

    private Set<T> modules;

    protected ModuleHandler() {
        this.modules = new HashSet<>();
    }

    public void registerModule(T module) {
        modules.add(module);
        for (CelestiaCommand command : module.getCommands())
            CelestiaCore.getCommandHandler().register(command);
    }

    public void unregisterModule(T module) {
        modules.remove(module);
        for (CelestiaCommand command : module.getCommands())
            CelestiaCore.getCommandHandler().unregister(command);
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
