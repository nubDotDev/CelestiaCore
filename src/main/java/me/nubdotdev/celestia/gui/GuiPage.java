package me.nubdotdev.celestia.gui;

import me.nubdotdev.celestia.gui.button.GuiButton;
import me.nubdotdev.celestia.gui.button.GuiRangeButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.IntStream;

public class GuiPage implements InventoryHolder {

    private String name;
    private InventoryType type;
    private int size;
    private IntStream buttonRange;
    private Map<Integer, GuiButton> buttons;
    private ItemStack filler;
    private Inventory inventory;
    private Gui gui;

    protected GuiPage(String name, Gui gui) {
        this.name = name;
        this.buttons = new HashMap<>();
        this.gui = gui;
    }

    public GuiPage(String name, Gui gui, InventoryType type, IntStream buttonRange) {
        this(name, gui);
        this.type = type;
        this.size = -1;
        this.buttonRange = buttonRange;
        createInventory();
    }

    public GuiPage(String name, Gui gui, InventoryType type) {
        this(name, gui, type, IntStream.range(0, type.getDefaultSize()));
    }

    public GuiPage(String name, Gui gui, int size, IntStream buttonRange) {
        this(name, gui);
        this.type = null;
        this.size = size;
        this.buttonRange = buttonRange;
        createInventory();
    }

    public GuiPage(String name, Gui gui, int size) {
        this(name, gui, size, IntStream.range(0, size));
    }

    public GuiPage(GuiPage page, Gui gui) {
        this.name = page.getName();
        this.type = page.getType();
        this.size = page.getSize();
        this.buttonRange = page.getButtonRange();
        this.buttons = page.getButtons();
        this.gui = gui;
    }

    public String getName() {
        return name;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
        this.size = -1;
        createInventory();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        this.type = null;
        createInventory();
    }

    public IntStream getButtonRange() {
        return buttonRange;
    }

    public Map<Integer, GuiButton> getButtons() {
        return buttons;
    }

    public void addButton(GuiButton button) {
        int slot = button.getSlot();
        buttons.put(slot, button);
        inventory.setItem(slot, button.getItem());
    }

    public void addButtonsToRange(List<GuiRangeButton> buttonList) {
        int i = 0;
        PrimitiveIterator.OfInt buttonRangeIterator = buttonRange.iterator();
        while (buttonRangeIterator.hasNext()) {
            int index = buttonRangeIterator.nextInt();
            if (buttons.containsKey(i))
                continue;
            GuiButton button = buttonList.get(i);
            button.setSlot(index);
            addButton(button);
            buttonList.remove(i);
            i++;
        }
    }

    public void removeButton(int slot) {
        buttons.remove(slot);
        inventory.setItem(slot, null);
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

    public void createInventory() {
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
        for (Player p : gui.getViewers())
            gui.open(p);
    }

    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            createInventory();
        }
        return inventory;
    }

    public Gui getGui() {
        return gui;
    }

}
