package me.nubdotdev.celestia.data.sql;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class SqlDatabaseManager {
    
    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    private Connection connection;
    private final Plugin plugin;

    public SqlDatabaseManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract void setupConnection();

    public void update(String sql) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            try {
                getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void query(String sql, Consumer<ResultSet> handler) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            try {
                handler.accept(getConnection().createStatement().executeQuery(sql));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Connection getConnection() {
        if (connection == null)
            setupConnection();
        return connection;
    }

}
