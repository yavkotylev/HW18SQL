package ru.sbt.dbworker;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yaroslav on 21.10.16.
 */
public class DBConnection {
    private final String url;
    private final String name;
    private final String password;

    public DBConnection(String url, String name, String password) {
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return java.sql.DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
