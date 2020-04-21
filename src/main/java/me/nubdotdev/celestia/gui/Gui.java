package me.nubdotdev.celestia.gui;

import me.nubdotdev.celestia.gui.button.GuiRangeButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui {

    private String name;
    private List<GuiPage> pages = new ArrayList<>();
    private int page;
    private List<Player> viewers = new ArrayList<>();

    public Gui(String name) {
        this.name = name;
    }

    public void open(Player player) {
        player.openInventory(pages.get(page).getInventory());
        if (!viewers.contains(player))
            viewers.add(player);
    }

    public void addButtons(List<GuiRangeButton> buttons, GuiPage template) {
        while (buttons.size() > 0) {
            GuiPage page = new GuiPage(template, this);
            page.addButtonsToRange(buttons);
            pages.add(page);
        }
    }

    public String getName() {
        return name;
    }

    public List<GuiPage> getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        for (Player p : getViewers())
            p.openInventory(pages.get(page).getInventory());
    }

    public List<Player> getViewers() {
        viewers.removeIf(player -> !Bukkit.getOnlinePlayers().contains(player));
        return viewers;
    }

}
