package me.nubdotdev.celestia.data.yaml;

import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class MessageConfig extends YamlConfig {

    private Map<String, String> messages = new HashMap<>();

    public MessageConfig(Plugin plugin) {
        super("/messages.yml", plugin);
        readMessages();
    }

    public void reload() {
        save();
        readMessages();
    }

    public void readMessages() {
        messages.clear();
        if (getConfig() == null)
            return;
        for (Map.Entry<String, Object> entry : getConfig().getValues(false).entrySet()) {
            try {
                messages.put(entry.getKey(), StringUtils.cc((String) entry.getValue()));
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    public String getMessage(String name) {
        return messages.getOrDefault(name, "Unknown message '" + name + "'");
    }

}
