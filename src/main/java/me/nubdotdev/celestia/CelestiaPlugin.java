package me.nubdotdev.celestia;

import me.nubdotdev.celestia.command.CommandHandler;
import me.nubdotdev.celestia.core.CelestiaLogger;
import me.nubdotdev.celestia.data.yaml.MessageConfig;
import me.nubdotdev.celestia.event.listeners.GuiListener;
import me.nubdotdev.celestia.hook.VaultHook;
import me.nubdotdev.celestia.hook.WorldEditHook;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CelestiaPlugin extends JavaPlugin {

    private CelestiaLogger log = new CelestiaLogger();
    private CommandHandler commandHandler = new CommandHandler();
    private MessageConfig messages;
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

    public void setupConfig() {
        saveDefaultConfig();
    }

    public void setupMessages() {
        messages = new MessageConfig(this);
    }

    public void setupGui() {
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
    }

    public void setupVault() {

    }

    public void setupWorldEdit() {

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

}
