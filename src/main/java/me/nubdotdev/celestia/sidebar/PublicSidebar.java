package me.nubdotdev.celestia.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PublicSidebar extends Sidebar {

    private List<Player> viewers = new ArrayList<>();

    public PublicSidebar(String title, long updatePeriod) {
        super(title, updatePeriod);
    }

    public void show(Player player) {
        player.setScoreboard(getScoreboard());
        viewers.add(player);
    }

    public void hide(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        viewers.remove(player);
    }

    public List<Player> getViewers() {
        return viewers;
    }

}
