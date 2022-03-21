package Model;

import Controller.InvalidItemIDException;
import java.sql.*;
import java.util.ArrayList;

public class ItemUpdates {
    private static int i = 0;
    private static final Connection con = DatabaseConnection.con;

    private final String ADD = "INSERT INTO items (custID, price, `name`, `description`, `condition`)" +
                                      "VALUES (?, ?, ?, ?, ?)";
    private final String DELETE = "DELETE FROM items WHERE itemID = ?";
    private final String OBTAIN_ALL_ITEMS = "SELECT * FROM items";
    private final String OBTAIN_FAVOURITE_ITEMS = "SELECT itemID from favourite_items WHERE custID = ?";
    private final String ADD_ITEM_TO_FAVOURITES = "INSERT INTO favourite_items (custID, itemID)" +
                                                  "VALUES (?, ?)";
    private final String MODIFY_ITEM = "UPDATE items " +
                                       "SET price = ?, `name` = ?, `description` = ?, `condition` = ?" +
                                       "WHERE itemID = ?";
    private final String DELETE_FROM_FAVOURITES = "DELETE FROM favourite_items WHERE custID = ? AND itemID = ?";

    public boolean addItem(int userID, double price, String condition,
                               String name, String description) throws SQLException {

        PreparedStatement preparedStatement = con.prepareStatement(ADD);

        preparedStatement.setInt(1, userID);
        preparedStatement.setDouble(2, price);
        preparedStatement.setString(3, condition);
        preparedStatement.setString(4, name);
        preparedStatement.setString(5, description);

        i = preparedStatement.executeUpdate();
        return i > 0;
    }
    public boolean deleteItem(int itemID) throws SQLException, SQLIntegrityConstraintViolationException {

        ArrayList<Item> allItems = obtainAllItems();

        for (Item item : allItems) {
            if (item.itemID == itemID) {
                PreparedStatement preparedStatement = con.prepareStatement(DELETE);

                preparedStatement.setInt(1, itemID);
                i = preparedStatement.executeUpdate();

                return i > 0;
            }
        }

        return false;
    }
    public ArrayList<Item> obtainUserItems(int customerID) throws SQLException {
        ArrayList<Item> allItems = obtainAllItems();
        ArrayList<Item> userItems = new ArrayList<>();

        for (Item item : allItems) {
            if (item.customerID == customerID) {
                userItems.add(item);
            }
        }

        return userItems;
    }
    public ArrayList<Item> obtainAllItems() throws SQLException {
        ArrayList<Item> allItems = new ArrayList<>();

        int customerID;
        int itemID;
        double price;
        String condition, name, description;

        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(OBTAIN_ALL_ITEMS);

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
    public boolean addItemToFavourites(int itemIDToAdd, int customerID)
                                                      throws SQLException {

        ArrayList<Item> allItems = obtainAllItems();

        for (Item item : allItems) {
            if (itemIDToAdd == item.itemID) {
                if (customerID == item.customerID) {
                    System.out.println("You can't add your item to favourites!");
                    return false;
                } else {

                    PreparedStatement preparedStatement = con.prepareStatement(ADD_ITEM_TO_FAVOURITES);

                    preparedStatement.setInt(1, customerID);
                    preparedStatement.setInt(2, itemIDToAdd);

                    preparedStatement.executeUpdate();
                }
            }
        }
        return true;
    }
    public boolean modifyItem(int itemID, double price, String name,
                                  String description, String condition) throws SQLException {

        PreparedStatement preparedStatement = con.prepareStatement(MODIFY_ITEM);
        preparedStatement.setDouble(1, price);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, condition);
        preparedStatement.setInt(5, itemID);

        i = preparedStatement.executeUpdate();

        return i > 0;

    }
    public ArrayList<Item> obtainFavouriteItems(int customerID) throws SQLException {

        ArrayList<Integer> favouriteItemsIDs = new ArrayList<>();
        ArrayList<Item> allItems = obtainAllItems();
        ArrayList<Item> favouriteItems = new ArrayList<>();

        PreparedStatement preparedStatement = con.prepareStatement(OBTAIN_FAVOURITE_ITEMS);
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
    public boolean removeFromFavourites(int itemID, int customerID) throws SQLException, InvalidItemIDException {

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

        PreparedStatement preparedStatement = con.prepareStatement(DELETE_FROM_FAVOURITES);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setInt(2, itemID);
        int i = preparedStatement.executeUpdate();
        return i > 0;
    }
}
