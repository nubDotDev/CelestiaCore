package me.nubdotdev.celestia.data.sql;

import me.nubdotdev.celestia.utils.CelestiaLogger;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDatabaseHandler extends SqlDatabaseHandler {

    private File file;
    private Connection connection;

    public SqliteDatabaseHandler(File file) {
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
            CelestiaLogger.warning("Failed to create file '" + file.getName() + "'");
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
