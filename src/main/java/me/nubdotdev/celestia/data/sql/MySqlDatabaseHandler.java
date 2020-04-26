package me.nubdotdev.celestia.data.sql;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabaseHandler extends SqlDatabaseHandler {

    private Connection connection;
    private String host, database, user, password;
    private int port;

    public MySqlDatabaseHandler(ConfigurationSection dataSection, Plugin plugin) {
        this(
                dataSection.getString("host"),
                dataSection.getString("database"),
                dataSection.getString("user"),
                dataSection.getString("password"),
                dataSection.getInt("port"),
                plugin
        );
    }

    public MySqlDatabaseHandler(String host, String database, String user, String password, int port, Plugin plugin) {
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
