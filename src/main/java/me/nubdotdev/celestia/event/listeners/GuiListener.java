package me.nubdotdev.celestia.event.listeners;

import me.nubdotdev.celestia.gui.Gui;
import me.nubdotdev.celestia.gui.GuiPage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class GuiListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof GuiPage))
            return;
        GuiPage page = (GuiPage) holder;
        if (page.getButtons().containsKey(e.getSlot()))
            page.getButtons().get(e.getSlot()).onClick(e);
        else
            e.setCancelled(true);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof GuiPage))
            return;
        Player p = (Player) e.getPlayer();
        ((GuiPage) holder).getViewers().remove(p);
        Gui gui = Gui.getInstances().get(p);
        if (gui != null)
            gui.getViewers().remove(p);
        Gui.getInstances().remove(p);
    }

}
