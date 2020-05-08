package me.nubdotdev.celestia;

import me.nubdotdev.celestia.command.CommandManager;
import me.nubdotdev.celestia.command.commands.CelestiaCoreCommand;
import me.nubdotdev.celestia.data.yaml.MessageConfig;
import me.nubdotdev.celestia.event.EventManager;
import me.nubdotdev.celestia.event.listeners.GuiListener;
import me.nubdotdev.celestia.hook.Hook;
import me.nubdotdev.celestia.hook.VaultHook;
import me.nubdotdev.celestia.hook.WorldEditHook;
import org.bukkit.plugin.java.JavaPlugin;

public class CelestiaCore extends JavaPlugin {

    private static CelestiaCore inst;

    private static CommandManager commandManager;
    private static EventManager eventManager;

    private static MessageConfig messages;

    private static final Hook papiHook = new Hook("PlaceholderAPI");
    private static final VaultHook vaultHook = new VaultHook();
    private static final WorldEditHook worldEditHook = new WorldEditHook();

    @Override
    public void onEnable() {
        super.onEnable();
        inst = this;

        messages = new MessageConfig(this);

        eventManager = new EventManager(this);
        eventManager.registerEvents(new GuiListener());

        commandManager = new CommandManager(this);
        commandManager.register(new CelestiaCoreCommand(this));
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static EventManager getEventManager() {
        return eventManager;
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
