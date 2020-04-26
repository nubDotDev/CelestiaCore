package me.nubdotdev.celestia.hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Hook {

    private String pluginName;

    public Hook(String pluginName) {
        this.pluginName = pluginName;
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
