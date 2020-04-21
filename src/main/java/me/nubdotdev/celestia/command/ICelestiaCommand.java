package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaPlugin;
import org.bukkit.command.CommandSender;

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

    CelestiaPlugin getPlugin();

    String getPermission();

    int getMinArgs();

    void setMinArgs(int minArgs);

    boolean requirePlayer();

    void setRequirePlayer(boolean needPlayer);

}
