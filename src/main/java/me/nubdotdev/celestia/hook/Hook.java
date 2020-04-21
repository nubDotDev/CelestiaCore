package me.nubdotdev.celestia.hook;

import me.nubdotdev.celestia.utils.CelestiaLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Hook {

    private String pluginName;

    public Hook(String pluginName) {
        this.pluginName = pluginName;
        if (!isProvided())
            CelestiaLogger.warning("WorldEdit dependency not found");
    }

    public boolean isProvided() {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null;
    }

    public Plugin getPlugin() {
        if (!isProvided())
            throw new IllegalStateException("WorldEdit dependency not found");
        return Bukkit.getPluginManager().getPlugin(pluginName);
    }

    public String getPluginName() {
        return pluginName;
    }

}
