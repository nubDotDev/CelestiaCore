package me.nubdotdev.celestia.data.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class YamlConfig {

    private File file;
    private FileConfiguration config;

    public YamlConfig(File file, Plugin plugin) {
        this.file = file;
        this.config = new YamlConfiguration();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(file.getName(), false);
        }
        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public YamlConfig(String path, Plugin plugin) {
        this(new File(plugin.getDataFolder(), path), plugin);
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
