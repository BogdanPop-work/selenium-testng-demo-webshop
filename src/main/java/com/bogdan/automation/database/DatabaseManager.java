package com.bogdan.automation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseManager {

    private static final String URL =
            "jdbc:h2:mem:webshop;DB_CLOSE_DELAY=-1";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}