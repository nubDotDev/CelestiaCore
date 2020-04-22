package me.nubdotdev.celestia.data.yaml;

import me.nubdotdev.celestia.CelestiaPlugin;
import me.nubdotdev.celestia.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MessageConfig extends YamlConfig {

    private Map<String, String> messages = new HashMap<>();

    public MessageConfig(CelestiaPlugin plugin) {
        super(plugin, "messages.yml");
        readMessages();
    }

    public void reload() {
        save();
        readMessages();
    }

    public void readMessages() {
        messages.clear();
        // Default messages needed for core functionality
        messages.put("no-perms", "&cInsufficient permissions!");
        messages.put("usage", "&cUsage: %usage%");
        messages.put("not-player", "&cOnly players can use that command!");
        messages.put("base-command-help-header", "&8&m----------&b&l%type% Help&8&m----------");
        messages.put("base-command-help-body", "&b%usage% &8- &7%description%");
        messages.put("base-command-help-footer", "&8&m--------------------");
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

    public Map<String, String> getMessages() {
        return messages;
    }

}
