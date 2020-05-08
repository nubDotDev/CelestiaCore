package me.nubdotdev.celestia.addon;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.command.CommandHandler;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;

public abstract class ModuleManager<T extends Module> {

    private final Set<T> modules;

    protected ModuleManager() {
        this.modules = new HashSet<>();
    }

    public void registerModule(T module) {
        modules.add(module);
        for (CommandHandler command : module.getCommands())
            if (!CelestiaCore.getCommandManager().register(command))
                Bukkit.getLogger().warning("Failed to register command '" + command.getName() + "'");
    }

    public void unregisterModule(T module) {
        modules.remove(module);
        for (CommandHandler command : module.getCommands())
            if (CelestiaCore.getCommandManager().unregister(command))
                Bukkit.getLogger().warning("Failed to unregister command '" + command.getName() + "'");
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
