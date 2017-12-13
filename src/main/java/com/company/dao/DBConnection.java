package com.company.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Oksa on 24.11.2017.
 */
public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/blog";
    private static final String USER = "user";
    private static final String PASS = "password";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASS);
            } catch (ClassNotFoundException|SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
