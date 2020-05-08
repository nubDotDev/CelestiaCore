package me.nubdotdev.celestia.data.sql;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDatabaseManager extends SqlDatabaseManager {

    private Connection connection;
    private final File file;

    public SqliteDatabaseManager(File file, Plugin plugin) {
        super(plugin);
        this.file = file;
    }

    @Override
    public void setupConnection() {
        try {
            if (!file.exists())
                if (!file.createNewFile())
                    return;
            synchronized (this) {
                if (connection == null || connection.isClosed()) {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:" + file);
                }
            }
        } catch (IOException e) {
            Bukkit.getLogger().warning("Failed to create file '" + file.getName() + "'");
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        if (connection == null)
            setupConnection();
        return connection;
    }
}
