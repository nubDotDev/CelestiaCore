package me.nubdotdev.celestia.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SimpleCommand implements CommandHandler {

    private final String name, description, usage, permission;
    private final List<String> aliases;
    private final int minArgs;
    private final boolean playersOnly;

    public SimpleCommand(
            String name,
            String description,
            String usage,
            List<String> aliases,
            String permission,
            int minArgs,
            boolean playersOnly
    ) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.aliases = aliases;
        this.minArgs = minArgs;
        this.playersOnly = playersOnly;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public int getMinArgs() {
        return minArgs;
    }

    @Override
    public boolean isPlayersOnly() {
        return playersOnly;
    }

}
