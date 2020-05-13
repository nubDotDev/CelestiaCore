package me.nubdotdev.celestia.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Represents a page in a {@link Gui}
 */
public class GuiPage implements InventoryHolder {

    private String name;
    private int size;
    private InventoryType type;
    private ItemStack filler;
    private final Set<Player> viewers = new HashSet<>();
    private final Map<Integer, GuiButton> buttons;
    private Inventory inventory;

    /**
     * Creates a new GUI page with a specified name and {@link InventoryType}
     *
     * @param name  name of GUI page
     * @param type  invetory type
     */
    public GuiPage(String name, InventoryType type) {
        this.name = name;
        this.type = type;
        this.buttons = new HashMap<>();
        createInventory();
    }

    /**
     * Creates a new GUI page with a specified name and size
     *
     * @param name  name of GUI page
     * @param size  invetory size
     */
    public GuiPage(String name, int size) {
        this.name = name;
        this.size = size;
        this.buttons = new HashMap<>();
        createInventory();
    }

    /**
     * Copy constructor
     */
    public GuiPage(GuiPage page) {
        this.name = page.name;
        this.type = page.type;
        this.size = page.size;
        this.buttons = new HashMap<>(page.buttons);
        this.filler = page.filler;
        this.inventory = page.inventory;
    }

    /**
     * Opens GUI page to a player
     *
     * @param player  player to whom to show the GUI page
     */
    public void open(Player player) {
        player.openInventory(getInventory());
        viewers.add(player);
    }

    /**
     * Translates all placeholders in the title of the {@link Inventory}<br>
     * '%page%' - page number<br>
     * '%pages' - number of pages
     *
     * @param page   page number
     * @param pages  number of pages
     */
    public void setPlaceholders(int page, int pages) {
        if (name.contains("%page%") || name.contains("%pages%")) {
            createInventory(name
                    .replaceAll("%page%", page + "")
                    .replaceAll("%pages%", pages + "")
            );
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        createInventory();
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
        this.size = type.getDefaultSize();
        createInventory();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        this.type = InventoryType.CHEST;
        createInventory();
    }

    public Map<Integer, GuiButton> getButtons() {
        return buttons;
    }

    public void setButton(int slot, GuiButton button) {
        buttons.put(slot, button);
        inventory.setItem(slot, button.getItem());
    }

    /**
     * Adds buttons to a specified range
     *
     * @param buttonRange  slots to which to add buttons
     * @param buttons      buttons to add
     * @return             list of buttons that were not added
     */
    public List<GuiButton> addButtonsToRange(int[] buttonRange, List<GuiButton> buttons) {
        List<GuiButton> buttons1 = new ArrayList<>(buttons);
        for (int i : buttonRange) {
            if (buttons1.isEmpty())
                break;
            if (i >= size || this.buttons.containsKey(i))
                continue;
            setButton(i, buttons1.get(0));
            buttons1.remove(0);
        }
        return buttons1;
    }

    public void removeButton(int slot) {
        buttons.remove(slot);
        inventory.setItem(slot, null);
    }

    public void moveButton(int oldSlot, int newSlot) {
        GuiButton button = buttons.get(oldSlot);
        buttons.remove(oldSlot);
        buttons.put(newSlot, button);
    }

    public ItemStack getFiller() {
        return filler;
    }

    public void setFiller(ItemStack filler) {
        this.filler = filler;
        for (int i = 0; i < inventory.getSize(); i++)
            if (!buttons.containsKey(i))
                inventory.setItem(i, filler);
    }

    public void createInventory(String name) {
        if (type != null)
            inventory = Bukkit.createInventory(this, type, name);
        else if (size != -1)
            inventory = Bukkit.createInventory(this, size, name);
        else
            return;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (buttons.containsKey(i))
                inventory.setItem(i, buttons.get(i).getItem());
            else
                inventory.setItem(i, filler);
        }
        for (Player p : viewers)
            p.openInventory(inventory);
    }

    public void createInventory() {
        createInventory(name);
    }

    public Set<Player> getViewers() {
        return viewers;
    }

    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            createInventory();
        }
        return inventory;
    }

}
