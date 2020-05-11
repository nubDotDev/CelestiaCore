package me.nubdotdev.celestia.gui.buttons;

import me.nubdotdev.celestia.gui.Gui;
import me.nubdotdev.celestia.gui.GuiButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a {@link GuiButton} that will take the player to a different page
 */
public class PageButton extends GuiButton {

    /**
     * Represents where a {@link PageButton} will take the player<br>
     */
    public enum Target {
        /**
         * Takes the player to the first page
         */
        FIRST,
        /**
         * Takes the player to the last page
         */
        LAST,
        /**
         * Takes the player to the next page
         */
        NEXT,
        /**
         * Takes the player to the previous page
         */
        PREVIOUS
    }

    private Target target;

    /**
     * Creates a new {@link GuiButton} with a specified {@link ItemStack} that will take the player to a different page,
     * depending on the target
     *
     * @param item    item that represents the button
     * @param target  where the button will take the player when clicked
     */
    public PageButton(ItemStack item, Target target) {
        super(item);
        this.target = target;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        Gui gui = Gui.getInstances().get(p);
        if (gui == null)
            return;
        int page;
        switch (target) {
            case FIRST:
                page = 0;
                break;
            case LAST:
                page = gui.getPages().size() - 1;
                break;
            case NEXT:
                page = gui.getViewers().get(p) + 1;
                break;
            case PREVIOUS:
                page = gui.getViewers().get(p) - 1;
                break;
            default:
                page = gui.getViewers().get(p);
        }
        gui.open(p, page);
    }

    public Target getTarget() {
        return target;
    }

}
