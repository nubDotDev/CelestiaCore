package me.nubdotdev.celestia.hook;

import me.nubdotdev.celestia.CelestiaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Hook {

    private String pluginName;

    public Hook(CelestiaPlugin plugin, String pluginName) {
        this.pluginName = pluginName;
        if (!isProvided())
            plugin.getLog().warning(pluginName + " dependency not found");
    }

    public boolean isProvided() {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null;
    }

    public Plugin getPlugin() throws IllegalStateException {
        if (!isProvided())
            throw new IllegalStateException(pluginName + " dependency not found");
        return Bukkit.getPluginManager().getPlugin(pluginName);
    }

    public String getPluginName() {
        return pluginName;
    }

}
