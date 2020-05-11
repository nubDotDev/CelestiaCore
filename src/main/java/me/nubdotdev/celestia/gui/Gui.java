package me.nubdotdev.celestia.gui;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an interactive inventory GUI
 */
public class Gui {

    private final static Map<Player, Gui> instances = new HashMap<>();

    private final String name;
    private final Map<Player, Integer> viewers = new HashMap<>();
    private final List<GuiPage> pages = new ArrayList<>();

    /**
     * Creates a new GUI with a specified name
     *
     * @param name  name of GUI
     */
    public Gui(String name) {
        this.name = name;
    }

    /**
     * Opens GUI to a player on a specified page
     *
     * @param player  player to whom to show the GUI
     * @param page    page at which to open the GUI
     */
    public void open(Player player, int page) {
        if (page < pages.size() && page >= 0) {
            pages.get(page).open(player);
            viewers.put(player, page);
            instances.put(player, this);
        }
    }

    /**
     * Opens GUI to a player on the first page<br>
     * This is equivalent to calling open(player, 0)
     *
     * @param player  player to whom to show the GUI
     */
    public void open(Player player) {
        open(player, 0);
    }

    /**
     * Adds pages based on a template filled with buttons in a specified range
     *
     * @param buttonRange  slots to which to add buttons
     * @param buttons      buttons to add
     * @param template     page to copy
     */
    public void addButtons(int[] buttonRange, List<GuiButton> buttons, GuiPage template) {
        List<GuiButton> buttons1 = new ArrayList<>(buttons);
        while (!buttons1.isEmpty()) {
            GuiPage page = new GuiPage(template);
            buttons1 = page.addButtonsToRange(buttonRange, buttons);
            addPage(page);
        }
    }

    public String getName() {
        return name;
    }

    public Map<Player, Integer> getViewers() {
        return viewers;
    }

    public List<GuiPage> getPages() {
        return pages;
    }

    public void addPage(int index, GuiPage page) {
        pages.add(index, page);
        page.setPlaceholders(index, pages.size());
        for (int i = 0; i < pages.size(); i++)
            pages.get(i).setPlaceholders(i + 1, pages.size());
    }

    public void addPage(GuiPage page) {
        addPage(pages.size(), page);
    }

    public void removePage(int index) {
        pages.remove(index);
        for (int i = 0; i < pages.size(); i++)
            pages.get(i).setPlaceholders(i + 1, pages.size());
    }

    public void removePage(GuiPage page) {
        if (pages.contains(page))
            removePage(pages.indexOf(page));
    }

    public static Map<Player, Gui> getInstances() {
        return instances;
    }

}
