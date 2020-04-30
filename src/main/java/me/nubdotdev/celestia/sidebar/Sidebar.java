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

public abstract class Sidebar {

    private String title;
    private long updatePeriod;
    private int prevSize, scroll;
    private List<String> lines = new ArrayList<>();
    private SidebarEntry[] entries = new SidebarEntry[15];
    private Scoreboard scoreboard;
    private Objective objective;
    private BukkitTask updateTask;

    public Sidebar(String title, long updatePeriod) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("objective", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);
        setUpdatePeriod(updatePeriod);
        for (int i = 0; i < 15; i++)
            entries[i] = new SidebarEntry(scoreboard.registerNewTeam(i + ""), i);
    }

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

    public void setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod;
        if (updateTask != null)
            updateTask.cancel();
        updateTask = (new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }).runTaskTimer(CelestiaCore.getInst(), updatePeriod, updatePeriod);
    }

    public void scroll(int line) {
        this.scroll = line;
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
