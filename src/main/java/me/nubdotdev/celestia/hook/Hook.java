package me.nubdotdev.celestia.hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Represents a hook into another plugin
 */
public class Hook {

    private final String pluginName;

    /**
     * Creates a new hook into another plugin from its name
     *
     * @param pluginName  name of plugin
     */
    public Hook(String pluginName) {
        this.pluginName = pluginName;
    }

    /**
     * Checks if the plugin is provided
     *
     * @return  true if the plugin is loaded on the server
     */
    public boolean isProvided() {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null;
    }

    /**
     * @throws IllegalStateException  if the plugin is not provided as per {@link #isProvided()}
     */
    public Plugin getPlugin() throws IllegalStateException {
        if (!isProvided())
            throw new IllegalStateException(pluginName + " dependency not found");
        return Bukkit.getPluginManager().getPlugin(pluginName);
    }

    public String getPluginName() {
        return pluginName;
    }

}
