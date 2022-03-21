package Model;

import Controller.InvalidItemIDException;
import java.sql.*;
import java.util.ArrayList;

public class ItemUpdates {
    private static int i = 0;
    private static final Connection con = DatabaseConnection.con;

    public static void addItem(int userID, double price, String condition,
                               String name, String description) throws SQLException {

        String query = "INSERT INTO items (custID, price, `name`, `description`, `condition`)" +
                       "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1, userID);
        preparedStatement.setDouble(2, price);
        preparedStatement.setString(3, condition);
        preparedStatement.setString(4, name);
        preparedStatement.setString(5, description);

        preparedStatement.executeUpdate();

    }
    public static boolean deleteItem(int itemID) throws SQLException, SQLIntegrityConstraintViolationException {

        ArrayList<Item> allItems = obtainAllItems();

        for (Item item : allItems) {
            if (item.itemID == itemID) {
                String query = "DELETE FROM items WHERE itemID = ?";
                PreparedStatement preparedStatement = con.prepareStatement(query);

                preparedStatement.setInt(1, itemID);
                i = preparedStatement.executeUpdate();

                return i > 0;
            }
        }

        return false;
    }
    public static ArrayList<Item> obtainUserItems(int customerID) throws SQLException {
        ArrayList<Item> allItems = obtainAllItems();
        ArrayList<Item> userItems = new ArrayList<>();

        for (Item item : allItems) {
            if (item.customerID == customerID) {
                userItems.add(item);
            }
        }

        return userItems;
    }
    public static ArrayList<Item> obtainAllItems() throws SQLException {
        ArrayList<Item> allItems = new ArrayList<>();

        int customerID;
        int itemID;
        double price;
        String condition, name, description;

        String query = "SELECT * FROM items";
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(query);

        while (result.next()) {
            itemID = result.getInt(1);
            customerID = result.getInt(2);
            price = result.getDouble(3);
            name = result.getString(4);
            description = result.getString(5);
            condition = result.getString(6);

            allItems.add(new Item(customerID, itemID, price, condition, name, description));
        }

        return allItems;
    }
    public static boolean addItemToFavourites(int itemIDToAdd, int customerID)
                                                      throws SQLException {

        ArrayList<Item> allItems = obtainAllItems();

        for (Item item : allItems) {
            if (itemIDToAdd == item.itemID) {
                if (customerID == item.customerID) {
                    System.out.println("You can't add your item to favourites!");
                    return false;
                } else {
                    String query = "INSERT INTO favourite_items (custID, itemID)" +
                                   "VALUES (?, ?);";
                    PreparedStatement preparedStatement = con.prepareStatement(query);

                    preparedStatement.setInt(1, customerID);
                    preparedStatement.setInt(2, itemIDToAdd);

                    preparedStatement.executeUpdate();
                }
            }
        }
        return true;
    }
    public static boolean modifyItem(int itemID, double price, String name,
                                  String description, String condition) throws SQLException {

        String query = "UPDATE items " +
                       "SET price = ?, `name` = ?, `description` = ?, `condition` = ?" +
                       "WHERE itemID = ?";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setDouble(1, price);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, condition);
        preparedStatement.setInt(5, itemID);

        i = preparedStatement.executeUpdate();

        return i > 0;

    }
    public static ArrayList<Item> obtainFavouriteItems(int customerID) throws SQLException {

        ArrayList<Integer> favouriteItemsIDs = new ArrayList<>();
        ArrayList<Item> allItems = obtainAllItems();
        ArrayList<Item> favouriteItems = new ArrayList<>();

        String query = "SELECT itemID from favourite_items WHERE custID = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, customerID);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            favouriteItemsIDs.add(result.getInt(1));
        }

        for (Item item : allItems) {
            for (Integer itemID : favouriteItemsIDs) {
                if (item.itemID == itemID) {
                    favouriteItems.add(item);
                }
            }
        }

        return favouriteItems;
    }
    public static boolean removeFromFavourites(int itemID, int customerID) throws SQLException, InvalidItemIDException {

        int flag = 0;
        ArrayList<Item> favouriteItems = obtainFavouriteItems(customerID);

        for (Item item : favouriteItems) {
            if (itemID == item.itemID) {
                flag = 1;
                break;
            }
        }

        if (flag != 1) {
            throw new InvalidItemIDException();
        }

        String query = "DELETE FROM favourite_items WHERE custID = ? AND itemID = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setInt(2, itemID);
        int i = preparedStatement.executeUpdate();
        return i > 0;
    }
}
