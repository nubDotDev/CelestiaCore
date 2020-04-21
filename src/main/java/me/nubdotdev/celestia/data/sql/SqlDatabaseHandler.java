package me.nubdotdev.celestia.data.sql;

import me.nubdotdev.celestia.CelestiaCore;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SqlDatabaseHandler {

    protected Connection connection = getConnection();

    public abstract Connection getConnection();

    public void update(String sql) {
        Bukkit.getScheduler().runTaskAsynchronously(CelestiaCore.getInst(), () -> {
            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void query(String sql, ResultSetHandler handler) {
        Bukkit.getScheduler().runTaskAsynchronously(CelestiaCore.getInst(), () -> {
            try {
                handler.handle(connection.createStatement().executeQuery(sql));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
