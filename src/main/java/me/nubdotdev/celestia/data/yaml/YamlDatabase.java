package me.nubdotdev.celestia.data.yaml;

import me.nubdotdev.celestia.CelestiaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class YamlDatabase {

    private CelestiaPlugin plugin;
    private File file;
    private List<YamlConfig> configs = new ArrayList<>();

    public YamlDatabase(CelestiaPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        if (file.exists()) {
            for (File f : Objects.requireNonNull(file.listFiles()))
                if (f.getName().endsWith(".yml"))
                    configs.add(new YamlConfig(plugin, f));
        } else {
            file.getParentFile().mkdirs();
            plugin.saveResource(file.getPath(), false);
        }
    }

    public Map<String, Object> get(String name) {
        YamlConfig config = getConfigByName(name);
        if (config == null)
            return null;
        return config.getConfig().getValues(true);
    }

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> all = new ArrayList<>();
        for (YamlConfig config : configs)
            all.add(config.getConfig().getValues(true));
        return all;
    }

    public void save(String name, YamlSerializable serializable) {
        YamlConfig config = getConfigByName(name);
        if (config == null) {
            config = new YamlConfig(plugin, name);
            configs.add(config);
        }
        Map<String, Object> serialized = serializable.serialize();
        for (Map.Entry<String, Object> entry : serialized.entrySet())
            config.getConfig().set(entry.getKey(), entry.getValue());
        config.save();
    }

    public boolean delete(String name) {
        YamlConfig config = getConfigByName(name);
        if (config == null)
            return false;
        return config.getFile().delete();
    }

    public File getFile() {
        return file;
    }

    public List<YamlConfig> getConfigs() {
        return configs;
    }

    public YamlConfig getConfigByName(String name) {
        for (YamlConfig config : configs)
            if (config.getFile().getName().equalsIgnoreCase(name + ".yml"))
                return config;
        return null;
    }

}
