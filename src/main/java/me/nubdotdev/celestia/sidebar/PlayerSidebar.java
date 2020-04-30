package me.nubdotdev.celestia.sidebar;

import me.nubdotdev.celestia.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerSidebar extends Sidebar {

    private Player player;

    public PlayerSidebar(String title, long updatePeriod, Player player) {
        super(title, updatePeriod);
        this.player = player;
    }

    @Override
    public void update() {
        for (int i = 0; i < getLines().size(); i++)
            getLines().set(i, StringUtils.setPlaceholders(player, getLines().get(i)));
        super.update();
    }

    public Player getPlayer() {
        return player;
    }

    public void show() {
        player.setScoreboard(getScoreboard());
    }

    public void hide() {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

}
