package me.nubdotdev.celestia.data.yaml;

import me.nubdotdev.celestia.CelestiaCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlConfig {

    private File file;
    private FileConfiguration config;

    public YamlConfig(File file) {
        this.file = file;
        this.config = new YamlConfiguration();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            CelestiaCore.getInst().saveResource(file.getPath(), false);
        }
        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public YamlConfig(String path) {
        this(new File(CelestiaCore.getInst().getDataFolder(), path));
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
