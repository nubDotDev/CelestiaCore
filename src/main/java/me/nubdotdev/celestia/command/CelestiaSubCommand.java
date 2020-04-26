package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaCore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CelestiaSubCommand implements ICelestiaCommand {

    private String name, description, usage, permission;
    private List<String> aliases;
    private int minArgs;
    boolean needPlayer;

    protected CelestiaSubCommand(String name) {
        this(name, "", "/", new ArrayList<>());
    }

    protected CelestiaSubCommand(String name, String description, String usage, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    protected CelestiaSubCommand(String name, String description, String usage, List<String> aliases, String permission) {
        this(name, description, usage, aliases);
        this.permission = permission;
    }

    public boolean execute(final CommandSender sender, final String[] args) {
        if (needPlayer && !(sender instanceof Player)) {
            sender.sendMessage(CelestiaCore.getMessages().getMessage("not-player"));
            return false;
        }
        if (hasPermission(sender)) {
            if (args.length >= minArgs && onCommand(sender, args)) {
                return true;
            } else {
                if (getUsage() != null)
                    sender.sendMessage(CelestiaCore.getMessages().getMessage("usage")
                            .replaceAll("%usage%", getUsage())
                    );
                return false;
            }
        }
        sender.sendMessage(CelestiaCore.getMessages().getMessage("no-perms"));
        return false;
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
