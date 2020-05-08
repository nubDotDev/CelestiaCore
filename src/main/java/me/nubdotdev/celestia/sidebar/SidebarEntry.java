package me.nubdotdev.celestia.sidebar;

import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

/**
 * Represents one line of a {@link Sidebar}
 */
public class SidebarEntry {

    private static final ImmutableList<String> RESET = ImmutableList.of(
            "\u00A7f", "\u00A7r"
    );
    private static final ImmutableList<String> COLORS = ImmutableList.of(
            "\u00A70", "\u00A71", "\u00A72", "\u00A73", "\u00A74", "\u00A75", "\u00A76", "\u00A77", "\u00A78", "\u00A79",
            "\u00A7a", "\u00A7b", "\u00A7c", "\u00A7d", "\u00A7e", "\u00A7f"
    );
    private static final ImmutableList<String> FORMATS = ImmutableList.of(
            "\u00A7k", "\u00A7l", "\u00A7m", "\u00A7n", "\u00A7o"
    );

    private final Team team;

    /**
     * Creates a new entry for a {@link Sidebar}
     *
     * @param team  used for its prefix and suffix to set text without flickering
     * @param line  index of the entry in its {@link Sidebar}
     */
    public SidebarEntry(Team team, int line) {
        this.team = team;
        team.addEntry(ChatColor.values()[line] + "\u00A7r");
        team.setPrefix("");
        team.setSuffix("");
    }

    /**
     * Sets the text of the entry using its team's prefix and suffix<br>
     * Special cases with color codes are checked for so they are not lost when switching from the prefix to the suffix
     *
     * @param text  text to be displayed on the {@link Sidebar}
     */
    public void setText(String text) {
        if (text.length() <= 16) {
            team.setPrefix(text);
            return;
        }
        if (!text.contains("\u00A7")) {
            team.setPrefix(text.substring(0, 16));
            team.setSuffix(text.substring(16));
            return;
        }
        StringBuilder color = new StringBuilder();
        int i = 0;
        for (String s : text.split("\u00A7")) {
            if (s.length() == 0)
                continue;
            if (text.charAt(i) == '\u00A7') {
                char c = s.charAt(0);
                if (RESET.contains("\u00A7" + c))
                    color = new StringBuilder();
                else if (COLORS.contains("\u00A7" + c))
                    color = new StringBuilder("\u00A7" + c);
                else if (FORMATS.contains("\u00A7" + c))
                    color.append("\u00A7").append(c);
            }
            i += s.length() + 1;
            if (i >= 15)
                break;
        }
        String prefix, suffix;
        if (i == 15) {
            prefix = text.substring(0, 15);
            suffix = text.substring(15);
        } else {
            prefix = text.substring(0, 16);
            suffix = text.substring(16);
        }
        if (RESET.contains(suffix.substring(0, 2))) {
            suffix = suffix.replaceAll(RESET.get(0), "").replaceAll(RESET.get(1), "");
            suffix = suffix.substring(0, 16);
        } else {
            suffix = color.toString() + suffix.substring(0, Math.min(suffix.length() - color.length(), 16 - color.length()));
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
    }

}
