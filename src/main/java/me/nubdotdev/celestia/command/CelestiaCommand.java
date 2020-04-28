package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public abstract class CelestiaCommand extends Command implements ICelestiaCommand {

    private int minArgs;
    private boolean requiresPlayer;
    private String help;
    private List<CelestiaSubCommand> subCommands = new ArrayList<>();

    protected CelestiaCommand(String name, String description, String usage, List<String> aliases) {
        super(name, description, usage, aliases);
    }

    protected CelestiaCommand(String name, String description, String usage, List<String> aliases, String permission) {
        this(name, description, usage, aliases);
        setPermission(permission);
    }

    @Override
    public boolean execute(final CommandSender sender, final String label, final String[] args) {
        if (requiresPlayer() && !(sender instanceof Player)) {
            sender.sendMessage(CelestiaCore.getMessages().getMessage("not-player"));
            return false;
        }
        if (!hasPermission(sender)) {
            sender.sendMessage(CelestiaCore.getMessages().getMessage("no-perms"));
            return false;
        }
        if (subCommands.size() > 0) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equals("?")) {
                if (help != null)
                    sender.sendMessage(help.replaceAll("%label%", label));
                else
                    sender.sendMessage(CelestiaCore.getMessages().getMessage("usage")
                            .replaceAll("%usage%", getUsage())
                    );
                return false;
            }
            for (CelestiaSubCommand sub : subCommands)
                if (args[0].equalsIgnoreCase(sub.getName()) || StringUtils.containsIgnoreCase(sub.getAliases(), args[0]))
                    return sub.execute(sender, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (args.length >= minArgs && onCommand(sender, args)) {
            return true;
        }
        sender.sendMessage(CelestiaCore.getMessages().getMessage("usage")
                .replaceAll("%usage%", getUsage())
        );
        return false;
    }

    @Override
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        if (args.length != 1 || subCommands.size() == 0)
            return super.tabComplete(sender, alias, args);
        List<String> completions = new ArrayList<>();
        for (CelestiaSubCommand sub : subCommands)
            if (StringUtil.startsWithIgnoreCase(sub.getName(), args[0]) || StringUtils.containsIgnoreCase(sub.getAliases(), args[0]))
                completions.add(sub.getName());
        return completions;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final String[] args) {
        return false;
    }

    @Override
    public String buildHelp() {
        StringBuilder helpMsg = new StringBuilder();
        helpMsg.append(CelestiaCore.getMessages().getMessage("base-command-help-header")).append("\n");
        if (subCommands != null) {
            for (CelestiaSubCommand sub : subCommands) {
                helpMsg.append((CelestiaCore.getMessages().getMessage("base-command-help-body") + "\n")
                        .replaceAll("%name%", sub.getName())
                        .replaceAll("%description%", sub.getDescription())
                        .replaceAll("%usage%", sub.getUsage())
                        .replaceAll("%aliases%", String.join(", ", sub.getAliases()))
                        .replaceAll("%permission%", getPermission())
                );
            }
        }
        helpMsg.append(CelestiaCore.getMessages().getMessage("base-command-help-footer"));
        return StringUtils.cc(helpMsg.toString())
                .replaceAll("%title%", getName());
    }

    @Override
    public String getHelp() {
        return help;
    }

    @Override
    public void setHelp(String help) {
        this.help = help;
    }

    @Override
    public List<CelestiaSubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public void addSubCommand(CelestiaSubCommand subCommand) {
        if (subCommand != null) {
            subCommands.add(subCommand);
            subCommand.setUsage(getUsage() + " " + subCommand.getUsage());
            this.help = buildHelp();
        }
    }

    @Override
    public void removeSubCommand(CelestiaSubCommand subCommand) {
        if (subCommand != null) {
            subCommands.remove(subCommand);
            this.help = buildHelp();
        }
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
    public boolean requiresPlayer() {
        return requiresPlayer;
    }

    @Override
    public void setRequiresPlayer(boolean requiresPlayer) {
        this.requiresPlayer = requiresPlayer;
    }


    private static Set<CelestiaCommand> instances = new HashSet<>();

    public static Set<CelestiaCommand> getInstances() {
        return instances;
    }

    public static void addInstance(CelestiaCommand command) {
        instances.add(command);
    }

}
