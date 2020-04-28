package me.nubdotdev.celestia.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICelestiaCommand {

    boolean onCommand(final CommandSender sender, final String[] args);

    default boolean hasPermission(final CommandSender sender) {
        if (getPermission() == null || sender.hasPermission(getPermission()))
            return true;
        for (int i = getPermission().length() - 1; i >= 0; i--)
            if (getPermission().charAt(i) == '.' && sender.hasPermission(getPermission().substring(0, i)))
                return true;
        return false;
    }

    default void reload() {
        setHelp(buildHelp());
    }

    String buildHelp();

    String getHelp();

    void setHelp(String help);

    List<CelestiaSubCommand> getSubCommands();

    void addSubCommand(CelestiaSubCommand subCommand);

    void removeSubCommand(CelestiaSubCommand subCommand);

    String getPermission();

    int getMinArgs();

    void setMinArgs(int minArgs);

    boolean requiresPlayer();

    void setRequiresPlayer(boolean needPlayer);

}
