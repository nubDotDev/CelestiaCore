package me.nubdotdev.celestia.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabaseHandler extends SqlDatabaseHandler {

    private String host, database, user, password;
    private int port;
    private Connection connection;

    public MySqlDatabaseHandler(String host, String database, String user, String password, int port) {
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
