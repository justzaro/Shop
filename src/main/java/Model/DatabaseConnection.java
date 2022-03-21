package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/clients";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mypass";

    public static Connection con;

    public static void establishConnection() throws SQLException {
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
