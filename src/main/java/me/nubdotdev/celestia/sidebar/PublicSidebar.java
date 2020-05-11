package me.nubdotdev.celestia.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sidebar which can be viewed by multiple players
 */
public class PublicSidebar extends Sidebar {

    private final List<Player> viewers = new ArrayList<>();

    /**
     * Creates a new public sidebar with a specified title and update period
     *
     * @param title         title of sidebar
     * @param updatePeriod  number of ticks between each call of {@link #update()}
     */
    public PublicSidebar(String title, long updatePeriod) {
        super(title, updatePeriod);
    }

    /**
     * Shows the sidebar to a player
     *
     * @param player  player to whom to show the sidebar
     */
    public void show(Player player) {
        player.setScoreboard(getScoreboard());
        viewers.add(player);
    }

    /**
     * Hides the sidebar from a player
     *
     * @param player  player from whom to hide the sidebar
     */
    public void hide(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        viewers.remove(player);
    }

    public List<Player> getViewers() {
        return viewers;
    }

}
