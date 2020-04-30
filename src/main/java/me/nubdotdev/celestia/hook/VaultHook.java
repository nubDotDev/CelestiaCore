package me.nubdotdev.celestia.hook;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook extends Hook {

    private Economy eco = null;
    private Chat chat = null;
    private Permission perms = null;

    public VaultHook() {
        super("Vault");
        if (isProvided()) {
            if (setupEconomy())
                Bukkit.getLogger().warning("Successfully setup Vault economy");
            else
                Bukkit.getLogger().warning("Failed to setup Vault economy");
            if (setupChat())
                Bukkit.getLogger().warning("Successfully setup Vault chat");
            else
                Bukkit.getLogger().warning("Failed to setup Vault chat");
            if (setupPermissions())
                Bukkit.getLogger().warning("Successfully setup Vault permissions");
            else
                Bukkit.getLogger().warning("Failed to setup Vault permissions");
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        eco = rsp.getProvider();
        return eco != null;
    }

    private boolean setupChat() {
        try {
            RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
            chat = rsp.getProvider();
            return chat != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean setupPermissions() {
        try {
            RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
            perms = rsp.getProvider();
            return perms != null;
        } catch (NullPointerException e) {
            return false;
        }
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
