package me.nubdotdev.celestia;

import me.nubdotdev.celestia.command.CommandHandler;
import me.nubdotdev.celestia.data.yaml.MessageConfig;
import me.nubdotdev.celestia.event.listeners.GuiListener;
import me.nubdotdev.celestia.hook.Hook;
import me.nubdotdev.celestia.hook.VaultHook;
import me.nubdotdev.celestia.hook.WorldEditHook;
import org.bukkit.plugin.java.JavaPlugin;

public class CelestiaCore extends JavaPlugin {

    private static CelestiaCore inst;

    private static final CommandHandler commandHandler = new CommandHandler();

    private static MessageConfig messages;

    private static final Hook papiHook = new Hook("PlaceholderAPI");
    private static final VaultHook vaultHook = new VaultHook();
    private static final WorldEditHook worldEditHook = new WorldEditHook();

    @Override
    public void onEnable() {
        super.onEnable();
        inst = this;

        messages = new MessageConfig(this);

        getServer().getPluginManager().registerEvents(new GuiListener(), this);
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static MessageConfig getMessages() {
        return messages;
    }

    public static Hook getPapiHook() {
        return papiHook;
    }

    public static VaultHook getVaultHook() {
        return vaultHook;
    }

    public static WorldEditHook getWorldEditHook() {
        return worldEditHook;
    }

    public static CelestiaCore getInst() {
        return inst;
    }

}
