package me.nubdotdev.celestia.event.listeners;

import me.nubdotdev.celestia.gui.GuiPage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class GuiListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof GuiPage))
            return;
        GuiPage page = (GuiPage) e.getInventory().getHolder();
        if (page.getButtons().containsKey(e.getSlot()))
            page.getButtons().get(e.getSlot()).onClick(e);
        else
            e.setCancelled(true);
    }

}
