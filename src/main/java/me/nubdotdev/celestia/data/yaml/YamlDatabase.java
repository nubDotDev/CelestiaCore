package me.nubdotdev.celestia.data.yaml;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class YamlDatabase {

    private final File file;
    private final List<YamlConfig> configs = new ArrayList<>();
    private final Plugin plugin;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public YamlDatabase(File file, Plugin plugin) {
        this.file = file;
        this.plugin = plugin;
        if (file.exists()) {
            for (File f : Objects.requireNonNull(file.listFiles()))
                if (f.getName().endsWith(".yml"))
                    configs.add(new YamlConfig(f, plugin));
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
            config = new YamlConfig(name, plugin);
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
