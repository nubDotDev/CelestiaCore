package me.nubdotdev.celestia.hook;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook extends Hook {

    private Economy eco;
    private Chat chat;
    private Permission perms;

    public VaultHook() {
        super("Vault");
        if (isProvided()) {
            try {
                setupEconomy();
            } catch (Exception e) {
                Bukkit.getLogger().warning("Failed to setup Vault economy");
            }
            try {
                setupChat();
            } catch (Exception e) {
                Bukkit.getLogger().warning("Failed to setup Vault chat");
            }
            try {
                setupPermissions();
            } catch (Exception e) {
                Bukkit.getLogger().warning("Failed to setup Vault permissions");
            }
        }
    }

    private boolean setupEconomy() {
        if (getPlugin().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public Economy getEco() {
        return eco;
    }

    public Chat getChat() {
        return chat;
    }

    public Permission getPerms() {
        return perms;
    }

}
