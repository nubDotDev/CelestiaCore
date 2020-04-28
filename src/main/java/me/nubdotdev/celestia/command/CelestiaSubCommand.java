package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CelestiaSubCommand implements ICelestiaCommand {

    private String name, description, usage, permission, help;
    private List<String> aliases;
    private int minArgs;
    private boolean requiresPlayer;
    private List<CelestiaSubCommand> subCommands = new ArrayList<>();

    protected CelestiaSubCommand(String name, String description, String usage, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
        this.help = buildHelp();
    }

    protected CelestiaSubCommand(String name, String description, String usage, List<String> aliases, String permission) {
        this(name, description, usage, aliases);
        this.permission = permission;
    }

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

    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();
        int lastIndex = args.length - 1;
        for (CelestiaSubCommand sub : subCommands)
            if (StringUtil.startsWithIgnoreCase(sub.getName(), args[lastIndex]) || StringUtils.containsIgnoreCase(sub.getAliases(), args[lastIndex]))
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
    public void setRequiresPlayer(boolean needPlayer) {
        this.requiresPlayer = needPlayer;
    }

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

}
