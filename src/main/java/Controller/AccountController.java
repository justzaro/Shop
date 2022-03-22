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
    private static final Scanner scanner = new Scanner(System.in);

    public static int userIndex, userID;
    public static final ItemUpdates itemUpdates = new ItemUpdates();
    public static final UserUpdates userUpdates = new UserUpdates();

    public static void signUp(String firstName, String lastName, String email, String password) {
        try {
            userUpdates.addUser(firstName, lastName, email, password);
            userUpdates.obtainUsers();
            System.out.println("Successful registration!");
        } catch (InputMismatchException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean logIn(String email, String password) {
        ArrayList<User> users = userUpdates.users;

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
    public static boolean addItem() throws SQLException {
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

        return itemUpdates.addItem(userID, price, condition, name, description);
    }
    public static boolean deleteItem(int itemID) throws SQLException, SQLIntegrityConstraintViolationException {
        return itemUpdates.deleteItem(itemID);
    }
    public static ArrayList<Item> obtainUserItems() throws SQLException {
        return itemUpdates.obtainUserItems(userID);
    }
    public static ArrayList<Item> obtainAllItems() throws SQLException {
        return itemUpdates.obtainAllItems();
    }
    public static boolean addItemToFavourites(int itemIDtoAdd) throws SQLException {
        return itemUpdates.addItemToFavourites(itemIDtoAdd, userID);
    }
    public static boolean modifyItem(int itemID, double price, String name, String description, String condition) throws SQLException {
        return itemUpdates.modifyItem(itemID, price, name, description, condition);
    }
    public static ArrayList<Item> obtainFavouriteItems() throws SQLException {
        return itemUpdates.obtainFavouriteItems(userID);
    }
    public static boolean removeFavouriteItem(int itemID) throws SQLException, InvalidItemIDException {
        return itemUpdates.removeFromFavourites(itemID, userID);
    }
}
