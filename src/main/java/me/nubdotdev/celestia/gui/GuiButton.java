package me.nubdotdev.celestia.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a button in a {@link GuiPage}
 */
public class GuiButton {

    private ItemStack item;

    /**
     * Creates a new GUI button with a specified {@link ItemStack}
     *
     * @param item  item that represents the button
     */
    public GuiButton(ItemStack item) {
        this.item = item;
    }

    /**
     * Called when button is clicked
     *
     * @param e  {@link InventoryClickEvent} to handle (cancelled if not overridden)
     */
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

}
