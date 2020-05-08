package me.nubdotdev.celestia.data.yaml;

import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class MessageConfig extends YamlConfig {

    private Map<String, String> messages;

    public MessageConfig(Plugin plugin) {
        super("/messages.yml", plugin);
        reloadMessages();
    }

    public void reload() {
        super.reload();
        reloadMessages();
    }

    public void reloadMessages() {
        messages = new HashMap<>();
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
