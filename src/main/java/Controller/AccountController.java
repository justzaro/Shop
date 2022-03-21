package Controller;

import Model.Item;
import Model.ItemUpdates;
import Model.User;
import Model.UserUpdates;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountController {
    public static int userIndex, userID;
    private static final Scanner scanner = new Scanner(System.in);

    public static void signUp(String firstName, String lastName, String email, String password) {
        try {
            UserUpdates.addUser(firstName, lastName, email, password);
            UserUpdates.obtainUsers();
            System.out.println("Successful registration!");
        } catch (InputMismatchException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean logIn(String email, String password) {
        ArrayList<User> users = UserUpdates.users;

        int i = 0;
        boolean accountExists = false;

        for (User user : users) {
            if ((email.equals(user.getEmail())) && (password.equals(user.getPassword()))) {
                accountExists = true;
                userIndex = i;
                userID = user.getUserID();
                break;
            }
            i++;
        }

        return accountExists;
    }
    public static void addItem() throws SQLException {
        double price;
        String condition, name, description;

        System.out.print("\nName: ");
        name = scanner.nextLine();
        System.out.print("Condition: ");
        condition = scanner.nextLine();
        System.out.print("Description: ");
        description = scanner.nextLine();
        System.out.print("Price: ");
        price = scanner.nextDouble();

        ItemUpdates.addItem(userID, price, condition, name, description);
    }
    public static boolean deleteItem(int itemID) throws SQLException, SQLIntegrityConstraintViolationException {
        return ItemUpdates.deleteItem(itemID);
    }
    public static ArrayList<Item> obtainUserItems() throws SQLException {
        return ItemUpdates.obtainUserItems(userID);
    }
    public static ArrayList<Item> obtainAllItems() throws SQLException {
        return ItemUpdates.obtainAllItems();
    }
    public static boolean addItemToFavourites(int itemIDtoAdd) throws SQLException {
        return ItemUpdates.addItemToFavourites(itemIDtoAdd, userID);
    }
    public static boolean modifyItem(int itemID, double price, String name, String description, String condition) throws SQLException {
        return ItemUpdates.modifyItem(itemID, price, name, description, condition);
    }
    public static ArrayList<Item> obtainFavouriteItems() throws SQLException {
        return ItemUpdates.obtainFavouriteItems(userID);
    }
    public static boolean removeFavouriteItem(int itemID) throws SQLException, InvalidItemIDException {
        return ItemUpdates.removeFromFavourites(itemID, userID);
    }
}
