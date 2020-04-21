package me.nubdotdev.celestia;

import me.nubdotdev.celestia.event.listeners.GuiListener;
import me.nubdotdev.celestia.hook.WorldEditHook;
import me.nubdotdev.celestia.utils.ConfigUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class CelestiaCore extends JavaPlugin {

    public WorldEditHook worldEditHook;

    @Override
    public void onEnable() {
        inst = this;
        this.worldEditHook = new WorldEditHook();

        ConfigUtils.init();

        // TODO MOVE EVENT REGISTRY TO SOMEWHERE ELSE
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
    }

    @Override
    public void onDisable() {
    }


    private static CelestiaCore inst;

    public static CelestiaCore getInst() {
        return inst;
    }

}
