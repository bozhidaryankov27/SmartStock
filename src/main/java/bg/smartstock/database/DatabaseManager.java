package bg.smartstock.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DatabaseManager {
    private static final String URL =
            "jdbc:sqlite:smartstock.db";

    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}