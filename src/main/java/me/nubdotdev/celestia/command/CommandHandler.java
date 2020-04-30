package me.nubdotdev.celestia.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CommandHandler {

    private Set<CelestiaCommand> commands = new HashSet<>();
    private SimpleCommandMap commandMap;
    private Map<String, Command> knownCommands;

    public CommandHandler() {
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
    }

    public boolean register(CelestiaCommand command) {
        if (commandMap.register(command.getName(), command)) {
            commands.add(command);
            return true;
        }
        return false;
    }

    public boolean unregister(CelestiaCommand command) {
        try {
            knownCommands.remove(command.getName());
            commands.remove(command);
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to unregister command '" + command.getName() + "'");
            e.printStackTrace();
            return false;
        }
    }

    public Set<CelestiaCommand> getCommands() {
        return commands;
    }

}
