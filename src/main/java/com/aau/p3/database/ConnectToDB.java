package com.aau.p3.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that establishes connection to Database
 */
public class ConnectToDB {
    /**
     * connect establishes a Connection to the given DB.
     * @param db the database your connecting to
     * @return Connection class as a object
     */
    public static Connection connect(String db) {
        // connection string
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