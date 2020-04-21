package me.nubdotdev.celestia.data.sql;

import me.nubdotdev.celestia.CelestiaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDatabaseHandler extends SqlDatabaseHandler {

    private File file;

    public SqliteDatabaseHandler(CelestiaPlugin plugin, File file) {
        super(plugin);
        this.file = file;
    }

    @Override
    public Connection getConnection() {
        try {
            if (!file.exists())
                file.createNewFile();
            synchronized (this) {
                if (connection == null || connection.isClosed()) {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:" + file);
                }
            }
        } catch (IOException e) {
            getPlugin().getLog().warning("Failed to create file '" + file.getName() + "'");
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
