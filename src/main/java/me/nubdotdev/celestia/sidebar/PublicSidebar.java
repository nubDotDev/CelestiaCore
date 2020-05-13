package me.nubdotdev.celestia.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a sidebar which can be viewed by multiple players
 */
public class PublicSidebar extends Sidebar {

    private final Set<Player> viewers = new HashSet<>();

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

    public Set<Player> getViewers() {
        return viewers;
    }

}
