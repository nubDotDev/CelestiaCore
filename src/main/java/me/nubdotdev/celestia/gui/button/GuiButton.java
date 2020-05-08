package me.nubdotdev.celestia.gui.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiButton {

    private ItemStack item;

    public GuiButton(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

}
