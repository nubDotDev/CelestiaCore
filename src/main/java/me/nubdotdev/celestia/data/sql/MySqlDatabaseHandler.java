package me.nubdotdev.celestia.data.sql;

import me.nubdotdev.celestia.CelestiaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabaseHandler extends SqlDatabaseHandler {

    private String host, database, user, password;
    private int port;

    public MySqlDatabaseHandler(CelestiaPlugin plugin, String host, String database, String user, String password, int port) {
        super(plugin);
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    @Override
    public Connection getConnection() {
        try {
            synchronized (this) {
                if (connection == null || connection.isClosed()) {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
