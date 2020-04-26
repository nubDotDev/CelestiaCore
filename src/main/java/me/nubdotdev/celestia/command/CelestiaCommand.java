package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CelestiaCommand extends Command implements ICelestiaCommand {

    private int minArgs;
    private boolean requirePlayer;

    protected CelestiaCommand(String name) {
        this(name, "", "/", new ArrayList<>());
    }

    protected CelestiaCommand(String name, String description, String usage, List<String> aliases) {
        super(name, description, usage, aliases);
    }

    protected CelestiaCommand(String name, String description, String usage, List<String> aliases, String permission) {
        this(name, description, usage, aliases);
        setPermission(permission);
    }

    @Override
    public boolean execute(final CommandSender sender, final String label, final String[] args) {
        if (requirePlayer && !(sender instanceof Player)) {
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

    public void reloadConfig() {}

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
        return requirePlayer;
    }

    @Override
    public void setRequirePlayer(boolean requirePlayer) {
        this.requirePlayer = requirePlayer;
    }


    private static Set<CelestiaCommand> instances = new HashSet<>();

    public static Set<CelestiaCommand> getInstances() {
        return instances;
    }

    public static void addInstance(CelestiaCommand command) {
        instances.add(command);
    }

}
