package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.CelestiaPlugin;
import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CelestiaBaseCommand extends CelestiaCommand {

    private String help;
    private List<CelestiaSubCommand> subCommands = new ArrayList<>();

    protected CelestiaBaseCommand(CelestiaPlugin plugin, String name) {
        this(plugin, name, name + " base command", "/" + name, new ArrayList<>());
    }

    protected CelestiaBaseCommand(CelestiaPlugin plugin, String name, String description, String usage, List<String> aliases) {
        super(plugin, name, description, usage, aliases);
        this.help = buildHelp();
    }

    protected CelestiaBaseCommand(CelestiaPlugin plugin, String name, String description, String usage, List<String> aliases, String permission) {
        this(plugin, name, description, usage, aliases);
        setPermission(permission);
    }

    @Override
    public boolean execute(final CommandSender sender, final String label, final String[] args) {
        if (hasPermission(sender)) {
            if (onCommand(sender, args))
                return true;
        } else {
            sender.sendMessage(getPlugin().getMessages().getMessage("no-perms"));
            return false;
        }
        if (args.length > 0)
            for (CelestiaSubCommand sub : subCommands)
                if (args[0].equalsIgnoreCase(sub.getName()) || StringUtils.containsIgnoreCase(sub.getAliases(), args[0]))
                    return sub.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equals("?")) {
            String help = getHelp();
            if (help != null)
                sender.sendMessage(help);
        } else if (getUsage() != null) {
            sender.sendMessage(getPlugin().getMessages().getMessage("usage")
                    .replaceAll("%usage%", getUsage())
            );
        }
        return false;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final String[] args) {
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
    public void reloadConfig() {
        setHelp(buildHelp());
    }

    public String buildHelp() {
        StringBuilder helpMsg = new StringBuilder();
        helpMsg.append(getPlugin().getMessages().getMessage("base-command-help.header") + "\n");
        // TODO EXPLAIN PLACEHOLDERS IN CONFIG
        if (subCommands != null) {
            for (CelestiaSubCommand sub : subCommands) {
                helpMsg.append((getPlugin().getMessages().getMessage("base-command-help.body") + "\n")
                        .replaceAll("%name%", sub.getName())
                        .replaceAll("%description%", sub.getDescription())
                        .replaceAll("%usage%", sub.getUsage())
                        .replaceAll("%aliases%", String.join(", ", sub.getAliases()))
                        .replaceAll("%permission%", getPermission())
                );
            }
        }
        helpMsg.append(getPlugin().getMessages().getMessage("base-command-help.footer"));
        return StringUtils.cc(helpMsg.toString())
                .replaceAll("%title%", getName());
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public List<CelestiaSubCommand> getSubCommands() {
        return subCommands;
    }

    public void setSubCommands(List<CelestiaSubCommand> subCommands) {
        this.subCommands = subCommands;
        this.help = buildHelp();
    }

    public void addSubCommand(CelestiaSubCommand subCommand) {
        if (subCommand != null) {
            subCommands.add(subCommand);
            subCommand.setUsage(getUsage() + " " + subCommand.getUsage());
            this.help = buildHelp();
        }
    }

    public void removeSubCommand(CelestiaSubCommand subCommand) {
        if (subCommand != null) {
            subCommands.remove(subCommand);
            this.help = buildHelp();
        }
    }

}
