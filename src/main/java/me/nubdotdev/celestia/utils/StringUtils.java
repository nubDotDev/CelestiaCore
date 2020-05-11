package me.nubdotdev.celestia.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.command.BaseCommand;
import me.nubdotdev.celestia.command.SimpleCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class StringUtils {

    /**
     * Translates all color codes in a string to their corresponding values
     *
     * @param s  string to translate
     * @return   translated string
     */
    public static String cc(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Translates all placeholders in a string
     *
     * @param p  player whose placeholders to use
     * @param s  string to translate
     * @return   translated string
     */
    public static String setPlaceholders(Player p, String s) {
        if (CelestiaCore.getPapiHook().isProvided())
            return PlaceholderAPI.setPlaceholders(p, s);
        return s;
    }

    /**
     * Translates all placeholders and color codes in a string to their corresponding values
     *
     * @param p  player whose placeholders are to be used
     * @param s  string to translate
     * @return   translated string
     */
    public static String ccSetPlaceholders(Player p, String s) {
        return cc(setPlaceholders(p, s));
    }

    /**
     * Checks if a list of strings contains a string, ignoring case
     *
     * @param list  string list to check against
     * @param s     string for whose presence to check
     * @return      true if the list contains the string, ignoring case
     */
    public static boolean containsIgnoreCase(List<String> list, String s) {
        for (String s1 : list)
            if (s1.equalsIgnoreCase(s))
                return true;
        return false;
    }

    /**
     * Creates a help message for a {@link BaseCommand} based on its sub-commands
     *
     * @param command  base command whose information to use
     * @return         help message
     */
    public static String buildHelp(BaseCommand command) {
        StringBuilder helpMsg = new StringBuilder();
        helpMsg.append(CelestiaCore.getMessages().getMessage("command-help-header")
                .replaceAll("%name%", command.getName())
        ).append("\n");
        if (command.getSubCommands() != null) {
            for (SimpleCommand sub : command.getSubCommands()) {
                helpMsg.append((CelestiaCore.getMessages().getMessage("command-help-body") + "\n")
                        .replaceAll("%name%", sub.getName())
                        .replaceAll("%description%", sub.getDescription())
                        .replaceAll("%usage%", sub.getUsage())
                        .replaceAll("%aliases%", String.join(", ", sub.getAliases()))
                        .replaceAll("%permission%", sub.getPermission())
                );
            }
        }
        helpMsg.append(CelestiaCore.getMessages().getMessage("command-help-footer")
                .replaceAll("%name%", command.getName())
        );
        return StringUtils.cc(helpMsg.toString());
    }

}
