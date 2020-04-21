package me.nubdotdev.celestia.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class StringUtils {

    public static String cc(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static boolean containsIgnoreCase(List<String> stringList, String string) {
        for (String s : stringList)
            if (s.equalsIgnoreCase(string))
                return true;
        return false;
    }

}
