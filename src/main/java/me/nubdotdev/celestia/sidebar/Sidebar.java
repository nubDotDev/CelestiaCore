package me.nubdotdev.celestia.sidebar;

import me.nubdotdev.celestia.CelestiaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scoreboard whose display slot is the sidebar<br>
 * Up to 15 lines can be displayed at once, but there can be more lines if scrolling is used
 */
public abstract class Sidebar {

    private String title;
    private long updatePeriod;
    private int prevSize, scroll;
    private final List<String> lines = new ArrayList<>();
    private final SidebarEntry[] entries = new SidebarEntry[15];
    private final Objective objective;
    private final Scoreboard scoreboard;
    private BukkitTask updateTask;

    @SuppressWarnings("deprecation")
    public Sidebar(String title, long updatePeriod) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("objective", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);
        setUpdatePeriod(updatePeriod);
        for (int i = 0; i < 15; i++)
            entries[i] = new SidebarEntry(scoreboard.registerNewTeam(i + ""), i);
    }

    /**
     * Updates the lines and the length thereof in the sidebar
     */
    public void update() {
        if (lines.size() != prevSize) {
            for (int i = 0; i < 15; i++) {
                scoreboard.resetScores(ChatColor.values()[i] + "\u00A7r");
                if (i < lines.size() - scroll)
                    objective.getScore(ChatColor.values()[i] + "\u00A7r").setScore(15 - i);
                else
                    break;
            }
        }
        for (int i = 0; i < lines.size() - scroll; i++)
            entries[i].setText(lines.get(i + scroll));
        prevSize = lines.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        objective.setDisplayName(title);
    }

    public long getUpdatePeriod() {
        return updatePeriod;
    }

    /**
     * Sets the update period and restarts the update runnable
     *
     * @param updatePeriod  number of ticks between each call of {@link #update()}
     */
    public void setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod;
        if (updateTask != null)
            updateTask.cancel();
        updateTask = (new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }).runTaskTimer(CelestiaCore.getInst(), 0, updatePeriod);
    }

    /**
     * Scrolls the sidebar down to a line such that all lines above it are no longer visible
     *
     * @param scroll  the line which will become the new top line (0 being the first)
     */
    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public List<String> getLines() {
        return lines;
    }

    public SidebarEntry[] getEntries() {
        return entries;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
