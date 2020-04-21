package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.utils.CelestiaLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CommandHandler {

    private static Set<CelestiaCommand> commands = new HashSet<>();
    private static SimpleCommandMap commandMap;
    private static Map<String, Command> knownCommands;

    static {
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

    public static boolean register(CelestiaCommand command) {
        if (commandMap.register(command.getName(), command)) {
            commands.add(command);
            return true;
        }
        return false;
    }

    public static boolean unregister(CelestiaCommand command) {
        try {
            knownCommands.remove(command.getName());
            commands.remove(command);
            return true;
        } catch (Exception e) {
            CelestiaLogger.warning("Failed to register command '" + command.getName() + "'");
            e.printStackTrace();
            return false;
        }
    }

    public static Set<CelestiaCommand> getCommands() {
        return commands;
    }

}
