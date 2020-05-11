package me.nubdotdev.celestia.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CommandManager {

    private final Set<CommandHandler> commands = new HashSet<>();
    private SimpleCommandMap commandMap;
    private Map<String, Command> knownCommands;
    private final Plugin plugin;

    public CommandManager(Plugin plugin) {
        try {
            final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());
            final Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.plugin = plugin;
    }

    public void register(CommandHandler command) {
        CelestiaCommand cmd = new CelestiaCommand(command, plugin);
        if (!commandMap.register(command.getName(), cmd)) {
            Bukkit.getLogger().warning("Failed to register command '" + command.getName() + "'");
            return;
        }
        commands.add(command);

    }

    public void unregister(CommandHandler command) {
        try {
            knownCommands.remove(command.getName());
            commands.remove(command);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to unregister command '" + command.getName() + "'");
            e.printStackTrace();
        }
    }

    public Set<CommandHandler> getCommands() {
        return commands;
    }

}
