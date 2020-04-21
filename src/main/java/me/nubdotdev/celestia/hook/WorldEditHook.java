package me.nubdotdev.celestia.hook;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.nubdotdev.celestia.CelestiaPlugin;

public class WorldEditHook extends Hook {

    public WorldEditHook(CelestiaPlugin plugin) {
        super(plugin, "WorldEdit");
    }

    public WorldEditPlugin getWorldEditPlugin() {
        return (WorldEditPlugin) getPlugin();
    }

}
