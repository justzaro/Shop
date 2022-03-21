package Model;

import java.sql.*;
import java.util.ArrayList;

public class UserUpdates {
    public static ArrayList<User> users = new ArrayList<>();
    private static final Connection con = DatabaseConnection.con;

    public static void obtainUsers() throws SQLException {
        String query = "SELECT * FROM clients";
        Statement stmt = con.createStatement();
        ResultSet output = stmt.executeQuery(query);

        while (output.next()) {
            int userID = output.getInt(1);
            String firstName = output.getString(2);
            String lastName = output.getString(3);
            String email = output.getString(4);
            String password = output.getString(5);

            User user = new User(userID, firstName, lastName, email, password);
            users.add(user);
        }
    }
    public static void addUser(String firstName, String lastName, String email,
                        String password) throws SQLException {

        String rawQuery = "INSERT INTO clients (first_name, last_name, email, password)" +
                          "VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = con.prepareStatement(rawQuery);

        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, email);
        pstmt.setString(4, password);

        pstmt.executeUpdate();
    }

    public void deleteUser() {

    }
    public void resetPassword() {

    }
}
