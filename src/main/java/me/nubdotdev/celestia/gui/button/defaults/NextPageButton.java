package me.nubdotdev.celestia.gui.button.defaults;

import me.nubdotdev.celestia.gui.Gui;
import me.nubdotdev.celestia.gui.button.GuiButton;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NextPageButton extends GuiButton {

    public NextPageButton(int slot, ItemStack item) {
        super(slot, item);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Gui gui = getPage().getGui();
        gui.setPage(gui.getCurrentPage() + 1);
    }

}
