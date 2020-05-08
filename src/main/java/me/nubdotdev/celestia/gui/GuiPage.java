package me.nubdotdev.celestia.gui;

import me.nubdotdev.celestia.gui.button.GuiButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiPage implements InventoryHolder {

    private String name;
    private InventoryType type;
    private int size;
    private final Map<Integer, GuiButton> buttons;
    private ItemStack filler;
    private final List<Player> viewers = new ArrayList<>();
    private Inventory inventory;

    public GuiPage(String name, InventoryType type) {
        this.name = name;
        this.type = type;
        this.buttons = new HashMap<>();
        createInventory();
    }

    public GuiPage(String name, int size) {
        this.name = name;
        this.size = size;
        this.buttons = new HashMap<>();
        createInventory();
    }

    public GuiPage(GuiPage page) {
        this.name = page.name;
        this.type = page.type;
        this.size = page.size;
        this.buttons = new HashMap<>(page.buttons);
        this.filler = page.filler;
        this.inventory = page.inventory;
    }

    public void open(Player player) {
        player.openInventory(getInventory());
        viewers.add(player);
    }

    public String getName() {
        return name;
    }

    public void setPlaceholders(int page, int pages) {
        if (name.contains("%page%") || name.contains("%pages%")) {
            createInventory(name
                    .replaceAll("%page%", page + "")
                    .replaceAll("%pages%", pages + "")
            );
        }
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

    public void addButtonsToRange(int[] buttonRange, List<GuiButton> buttonList) {
        for (int i : buttonRange) {
            if (buttonList.isEmpty())
                break;
            if (i >= size || buttons.containsKey(i))
                continue;
            GuiButton button = buttonList.get(0);
            setButton(i, button);
            buttonList.remove(0);
        }
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

    public List<Player> getViewers() {
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
