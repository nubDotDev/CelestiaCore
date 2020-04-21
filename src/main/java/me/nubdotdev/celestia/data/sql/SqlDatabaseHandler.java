package me.nubdotdev.celestia.data.sql;

import me.nubdotdev.celestia.CelestiaPlugin;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SqlDatabaseHandler {

    private CelestiaPlugin plugin;
    protected Connection connection;

    public SqlDatabaseHandler(CelestiaPlugin plugin) {
        this.plugin = plugin;
        this.connection = getConnection();
    }

    public abstract Connection getConnection();

    public void update(String sql) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void query(String sql, ResultSetHandler handler) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                handler.handle(connection.createStatement().executeQuery(sql));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CelestiaPlugin getPlugin() {
        return plugin;
    }

}
