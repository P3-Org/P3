package com.aau.p3.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that establishes connection to Database
 */
public class ConnectToDB {
    /**
     * method for establishing a Connection to the given DB.
     * @param db the database connecting to
     * @return Connection class as an object
     */
    public static Connection connect(String db) {
        // SQLite connection string using jdbc
        var url = "jdbc:sqlite:" + db;

        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}