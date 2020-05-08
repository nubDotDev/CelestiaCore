package me.nubdotdev.celestia.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import java.util.List;

public interface CommandHandler extends CommandExecutor, TabCompleter {

    String getName();

    String getDescription();

    String getUsage();

    String getPermission();

    List<String> getAliases();

    int getMinArgs();

    boolean isPlayersOnly();

}
