package me.nubdotdev.celestia.sidebar;

import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A player-specific sidebar, which allows for PAPI placeholders to be used
 */
public class PlayerSidebar extends Sidebar {

    private Player player;

    /**
     * Creates a new player-specific sidebar with a specified title and update period
     *
     * @param title         title of sidebar
     * @param updatePeriod  number of ticks between each call of {@link #update()}
     * @param player        sole viewer of the sidebar and whose PAPI placeholders will be used
     */
    public PlayerSidebar(String title, long updatePeriod, Player player) {
        super(title, updatePeriod);
        this.player = player;
    }

    /**
     * Applies {@link StringUtils#setPlaceholders(Player, String)} to every line and then calls {@link Sidebar#update()}
     */
    @Override
    public void update() {
        for (int i = 0; i < getLines().size(); i++)
            getLines().set(i, StringUtils.setPlaceholders(player, getLines().get(i)));
        super.update();
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Hides the sidebar from the previous player and shows it to a new player
     *
     * @param player  new player to whom to show the sidebar
     */
    public void setPlayer(Player player) {
        hide();
        this.player = player;
        show();
    }

    /**
     * Shows the sidebar to the player
     */
    public void show() {
        player.setScoreboard(getScoreboard());
    }

    /**
     * Hides the sidebar from the player
     */
    public void hide() {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

}
