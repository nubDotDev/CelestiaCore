package me.nubdotdev.celestia.utils;

import me.nubdotdev.celestia.CelestiaCore;
import me.nubdotdev.celestia.command.CelestiaCommand;
import me.nubdotdev.celestia.command.CommandHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {

    private static CelestiaCore plugin;
    private static FileConfiguration config;
    private static File configFile;

    private static Map<String, String> msgs;

    public static void init() {
        plugin = CelestiaCore.getInst();
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        configFile = new File(plugin.getDataFolder(), "config.yml");
        initMsgs();
    }

    public static void initMsgs() {
        msgs = new HashMap<>();
        ConfigurationSection msgSection = config.getConfigurationSection("messages");
        if (msgSection != null)
            for (Map.Entry<String, Object> entry : msgSection.getValues(true).entrySet())
                if (entry.getValue() instanceof String)
                    msgs.put((entry.getKey()), StringUtils.cc((String) entry.getValue()));
    }

    public static String getMsg(String msg) {
        return msgs.getOrDefault(msg, "Unknown message '" + msg + "'");
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
        initMsgs();
        for (CelestiaCommand cmd : CommandHandler.getCommands())
            cmd.reloadConfig();
    }

    public static void save() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

}
