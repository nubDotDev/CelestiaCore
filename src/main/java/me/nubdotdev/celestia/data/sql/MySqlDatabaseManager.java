package me.nubdotdev.celestia.data.sql;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabaseManager extends SqlDatabaseManager {

    private Connection connection;
    private final String host, database, user, password;
    private final int port;

    public MySqlDatabaseManager(ConfigurationSection dataSection, Plugin plugin) {
        this(
                dataSection.getString("host"),
                dataSection.getString("database"),
                dataSection.getString("user"),
                dataSection.getString("password"),
                dataSection.getInt("port"),
                plugin
        );
    }

    public MySqlDatabaseManager(String host, String database, String user, String password, int port, Plugin plugin) {
        super(plugin);
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    @Override
    public void setupConnection() {
        try {
            synchronized (this) {
                if (connection == null || connection.isClosed()) {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
                }
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }

}
