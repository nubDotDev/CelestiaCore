package me.nubdotdev.celestia.data.yaml;

import me.nubdotdev.celestia.CelestiaPlugin;
import me.nubdotdev.celestia.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MessageConfig extends YamlConfig {

    private Map<String, String> messages = new HashMap<>();

    public MessageConfig(CelestiaPlugin plugin, String path) {
        super(plugin, path);
        readMessages();
    }

    public void reload() {
        save();
        readMessages();
    }

    public void readMessages() {
        messages.clear();
        messages.put("no-perms", "&cInsufficient permissions!");
        messages.put("usage", "&cUsage: %usage%");
        messages.put("not-player", "&cOnly players can use that command!");
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

    public Map<String, String> getMessages() {
        return messages;
    }

}
