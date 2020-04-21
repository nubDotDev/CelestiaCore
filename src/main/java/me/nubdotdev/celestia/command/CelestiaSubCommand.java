package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CelestiaSubCommand implements ICelestiaCommand {

    private CelestiaPlugin plugin;
    private String name, description, usage, permission;
    private List<String> aliases;
    private int minArgs;
    boolean needPlayer;

    protected CelestiaSubCommand(CelestiaPlugin plugin, String name) {
        this(plugin, name, "", "/", new ArrayList<>());
    }

    protected CelestiaSubCommand(CelestiaPlugin plugin, String name, String description, String usage, List<String> aliases) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    protected CelestiaSubCommand(CelestiaPlugin plugin, String name, String description, String usage, List<String> aliases, String permission) {
        this(plugin, name, description, usage, aliases);
        this.permission = permission;
    }

    public boolean execute(final CommandSender sender, final String[] args) {
        if (needPlayer && !(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessages().getMessage("not-player"));
            return false;
        }
        if (hasPermission(sender)) {
            if (args.length >= minArgs && onCommand(sender, args)) {
                return true;
            } else {
                if (getUsage() != null)
                    sender.sendMessage(plugin.getMessages().getMessage("usage")
                            .replaceAll("%usage%", getUsage())
                    );
                return false;
            }
        }
        sender.sendMessage(plugin.getMessages().getMessage("no-perms"));
        return false;
    }

    @Override
    public CelestiaPlugin getPlugin() {
        return plugin;
    }

    public abstract boolean onCommand(final CommandSender sender, final String[] args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public int getMinArgs() {
        return minArgs;
    }

    @Override
    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    @Override
    public boolean requirePlayer() {
        return needPlayer;
    }

    @Override
    public void setRequirePlayer(boolean needPlayer) {
        this.needPlayer = needPlayer;
    }

}
