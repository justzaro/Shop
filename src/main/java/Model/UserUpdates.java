package Model;

import java.sql.*;
import java.util.ArrayList;

public class UserUpdates {
    public ArrayList<User> users = new ArrayList<>();
    private static final Connection con = DatabaseConnection.con;

    private final String OBTAIN_USERS = "SELECT * FROM clients";
    private final String INSERT_INTO_CLIENTS = "INSERT INTO clients (first_name, last_name, email, password)" +
                                               "VALUES (?, ?, ?, ?)";

    public void obtainUsers() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet output = stmt.executeQuery(OBTAIN_USERS);

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
    public void addUser(String firstName, String lastName, String email,
                        String password) throws SQLException {

        PreparedStatement pstmt = con.prepareStatement(INSERT_INTO_CLIENTS);

        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, email);
        pstmt.setString(4, password);

        pstmt.executeUpdate();
    }

}
