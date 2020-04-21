package me.nubdotdev.celestia.gui.button;

import me.nubdotdev.celestia.gui.GuiPage;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiButton {

    private int slot;
    private ItemStack item;
    private GuiPage page;

    public GuiButton(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int newSlot) {
        this.slot = newSlot;
        page.removeButton(slot);
        page.addButton(this);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        page.getInventory().setItem(slot, item);
    }

    public GuiPage getPage() {
        return page;
    }

    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

}
