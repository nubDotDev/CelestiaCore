package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaCore;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CelestiaCommand extends Command implements PluginIdentifiableCommand {

    private final int minArgs;
    private final boolean playersOnly;
    private final CommandHandler handler;
    private final Plugin plugin;

    @SuppressWarnings("unchecked")
    public CelestiaCommand(CommandHandler handler, Plugin plugin) {
        super(
                Objects.requireNonNull(handler.getName()),
                (String) ObjectUtils.defaultIfNull(handler.getDescription(), ""),
                (String) ObjectUtils.defaultIfNull(handler.getUsage(), "/" + handler.getName()),
                (List<String>) ObjectUtils.defaultIfNull(handler.getAliases(), Collections.emptyList())
        );
        setPermission(handler.getPermission());
        this.minArgs = handler.getMinArgs();
        this.playersOnly = handler.isPlayersOnly();
        this.handler = handler;
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (playersOnly && !(sender instanceof Player)) {
            sender.sendMessage(CelestiaCore.getMessages().getMessage("not-player"));
        } else if (getPermission() != null && !sender.hasPermission(getPermission())) {
            sender.sendMessage(CelestiaCore.getMessages().getMessage("no-perms"));
        } else if (args.length < minArgs || !handler.onCommand(sender, this, label, args)) {
            sender.sendMessage(CelestiaCore.getMessages().getMessage("usage")
                    .replaceAll("%usage%", getUsage())
                    .replaceAll("%label%", label)
            );
        } else {
            return true;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> completions = handler.onTabComplete(sender, this, alias, args);
        return completions == null ? super.tabComplete(sender, alias, args) : completions;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

}
