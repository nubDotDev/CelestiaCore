package me.nubdotdev.celestia.data.sql;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class SqlDatabaseHandler {

    private Connection connection;
    private Plugin plugin;

    public SqlDatabaseHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract void setupConnection();

    public void update(String sql) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void query(String sql, Consumer<ResultSet> handler) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                handler.accept(getConnection().createStatement().executeQuery(sql));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Connection getConnection() {
        if (connection == null)
            setupConnection();
        return connection;
    }

}
