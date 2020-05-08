package me.nubdotdev.celestia.gui.button.defaults;

import me.nubdotdev.celestia.gui.Gui;
import me.nubdotdev.celestia.gui.button.GuiButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NextPageButton extends GuiButton {

    public NextPageButton(ItemStack item) {
        super(item);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        Gui gui = Gui.getInstances().get(p);
        if (gui == null)
            return;
        int page = gui.getViewers().get(p);
        gui.open(p, page + 1);
    }

}
