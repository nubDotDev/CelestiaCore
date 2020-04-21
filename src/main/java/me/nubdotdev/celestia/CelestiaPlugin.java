package me.nubdotdev.celestia;

import me.nubdotdev.celestia.command.CommandHandler;
import me.nubdotdev.celestia.data.yaml.MessageConfig;
import me.nubdotdev.celestia.event.listeners.GuiListener;
import me.nubdotdev.celestia.hook.VaultHook;
import me.nubdotdev.celestia.hook.WorldEditHook;
import me.nubdotdev.celestia.utils.CelestiaLogger;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CelestiaPlugin extends JavaPlugin {

    private CelestiaLogger log = new CelestiaLogger();
    private MessageConfig messages = new MessageConfig(this, "messages.yml");
    private CommandHandler commandHandler = new CommandHandler(this);
    private WorldEditHook worldEditHook;
    private VaultHook vaultHook;

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public CelestiaLogger getLog() {
        return log;
    }

    public MessageConfig getMessages() {
        return messages;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public VaultHook getVaultHook() {
        if (vaultHook == null)
            vaultHook = new VaultHook(this);
        return vaultHook;
    }

    public WorldEditHook getWorldEditHook() {
        if (worldEditHook == null)
            worldEditHook = new WorldEditHook(this);
        return worldEditHook;
    }



    public void setupGui() {
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
    }

}
