package me.nubdotdev.celestia.command.commands;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.command.BaseCommand;
import me.nubdotdev.celestia.command.SimpleCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Collections;

public class CelestiaCoreCommand extends BaseCommand {

    private final Plugin plugin;

    public CelestiaCoreCommand(Plugin plugin) {
        super("CelestiaCore",
                "Main command for CelestiaCore",
                null,
                null,
                "celestiacore.admin",
                0,
                false
        );
        this.plugin = plugin;
        getSubCommands().add(new SimpleCommand(
                "reload",
                "Reloads config files",
                "/%label% reload",
                Collections.singletonList("rl"),
                null,
                1,
                false
        ) {
            @Override
            public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
                CelestiaCore.getMessages().reload();
                sender.sendMessage(CelestiaCore.getMessages().getMessage("reload"));
                return true;
            }
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("celestiacore") && args.length == 0) {
            sender.sendMessage("CelestiaCore " + plugin.getDescription().getVersion() + " by nubDotDev");
            return true;
        }
        return super.onCommand(sender, cmd, label, args);
    }

}
