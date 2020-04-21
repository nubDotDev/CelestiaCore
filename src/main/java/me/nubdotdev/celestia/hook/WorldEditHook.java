package me.nubdotdev.celestia.hook;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class WorldEditHook extends Hook {

    public WorldEditHook() {
        super("WorldEdit");
    }

    public WorldEditPlugin getWorldEditPlugin() {
        return (WorldEditPlugin) getPlugin();
    }

}
