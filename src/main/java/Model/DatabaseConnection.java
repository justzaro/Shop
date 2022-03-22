package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection con;

    public static void establishConnection(String url, String username, String password) throws SQLException {
        con = DriverManager.getConnection(url, username, password);
    }
}
