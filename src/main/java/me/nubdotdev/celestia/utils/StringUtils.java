package me.nubdotdev.celestia.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nubdotdev.celestia.CelestiaCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class StringUtils {

    public static String cc(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String setPlaceholders(Player p, String s) {
        if (CelestiaCore.getPapiHook().isProvided())
            return PlaceholderAPI.setPlaceholders(p, s);
        return s;
    }

    public static String ccSetPlaceholders(Player p, String s) {
        return cc(setPlaceholders(p, s));
    }

    public static boolean containsIgnoreCase(List<String> stringList, String string) {
        for (String s : stringList)
            if (s.equalsIgnoreCase(string))
                return true;
        return false;
    }

}
