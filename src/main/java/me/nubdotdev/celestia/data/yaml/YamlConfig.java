package me.nubdotdev.celestia.data.yaml;

import me.nubdotdev.celestia.CelestiaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlConfig {

    private CelestiaPlugin plugin;
    private File file;
    private FileConfiguration config;

    public YamlConfig(CelestiaPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        this.config = new YamlConfiguration();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(file.getPath(), false);
        }
        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public YamlConfig(CelestiaPlugin plugin, String path) {
        this(plugin, new File(plugin.getDataFolder(), path));
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
