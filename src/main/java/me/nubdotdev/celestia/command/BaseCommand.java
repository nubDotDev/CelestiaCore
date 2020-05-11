package me.nubdotdev.celestia.command;

import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommand extends SimpleCommand {

    private final List<SimpleCommand> subCommands = new ArrayList<>();

    public BaseCommand(
            String name,
            String description,
            String usage,
            List<String> aliases,
            String permission,
            int minArgs,
            boolean playersOnly
    ) {
        super(name, description, usage, aliases, permission, minArgs, playersOnly);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0)
            if (cmd.getName().equalsIgnoreCase(getName()))
                for (SimpleCommand sub : subCommands)
                    if (args[0].equalsIgnoreCase(sub.getName()) || StringUtils.containsIgnoreCase(sub.getAliases(), args[0]))
                        return sub.onCommand(sender, cmd, label, args);
        if (getUsage() == null) {
            sender.sendMessage(StringUtils.buildHelp(this).replaceAll("%label%", label));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(getName())) {
            if (args.length == 0 || subCommands.size() == 0)
                return null;
            List<String> completions = new ArrayList<>();
            if (args.length == 1) {
                for (SimpleCommand sub : subCommands) {
                    if (StringUtil.startsWithIgnoreCase(sub.getName(), args[0]))
                        completions.add(sub.getName());
                    for (String s : sub.getAliases())
                        if (StringUtil.startsWithIgnoreCase(args[0], s))
                            completions.add(s);
                }
            } else {
                for (SimpleCommand sub : subCommands)
                    if (args[0].equalsIgnoreCase(sub.getName()) || StringUtils.containsIgnoreCase(sub.getAliases(), args[0]))
                        return sub.onTabComplete(sender, cmd, label, args);
            }
            return completions;
        }
        return null;
    }

    public List<SimpleCommand> getSubCommands() {
        return subCommands;
    }

}
