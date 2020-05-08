package me.nubdotdev.celestia.gui;

import me.nubdotdev.celestia.gui.button.GuiButton;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gui {

    private final static Map<Player, Gui> instances = new HashMap<>();

    private final String name;
    private final Map<Player, Integer> viewers = new HashMap<>();
    private final List<GuiPage> pages = new ArrayList<>();

    public Gui(String name) {
        this.name = name;
    }

    public void open(Player player, int page) {
        if (page < pages.size() && page >= 0) {
            pages.get(page).open(player);
            viewers.put(player, page);
        }
        instances.put(player, this);
    }

    public void open(Player player) {
        open(player, 0);
    }

    public void addButtons(int[] buttonRange, List<GuiButton> buttons, GuiPage template) {
        while (!buttons.isEmpty()) {
            GuiPage page = new GuiPage(template);
            page.addButtonsToRange(buttonRange, buttons);
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
