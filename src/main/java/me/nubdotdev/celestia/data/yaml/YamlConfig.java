package me.nubdotdev.celestia.data.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class YamlConfig {

    private final File file;
    private final FileConfiguration config;
    private final Plugin plugin;

    public YamlConfig(File file, Plugin plugin) {
        this.file = file;
        this.config = new YamlConfiguration();
        this.plugin = plugin;
        reload();
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void reload() {
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

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
