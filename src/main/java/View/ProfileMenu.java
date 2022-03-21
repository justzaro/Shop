package View;

import Controller.AccountController;
import Controller.InvalidItemIDException;
import Model.Item;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProfileMenu {
    static final Scanner scanner = new Scanner(System.in);
    public static void profileOptions() throws SQLException {
        int option;
        while (true) {
            System.out.println();
            System.out.println("1. Add item");
            System.out.println("2. Delete item");
            System.out.println("3. Your items");
            System.out.println("4. Modify item");
            System.out.println("5. Favourite items");
            System.out.println("6. All items");
            System.out.println("7. Log out");
            System.out.print("Option: ");
            option = scanner.nextInt();

            if (option == 1) {
                AccountController.addItem();
                System.out.println("Item added!");
            } else if (option == 2) {
                deleteItem();
            } else if (option == 3) {
                showUserItems();
            } else if (option == 4) {
                modifyItem();
            } else if (option == 5) {
                showFavouriteItems();
            } else if (option == 6) {
                showAllItems();
            } else if (option == 7) {
                return;
            }
        }
    }
    public static void deleteItem() throws SQLException {
        int itemID;
        boolean result;

        System.out.print("Enter item ID to delete it: ");
        itemID = scanner.nextInt();

        try {
            result = AccountController.deleteItem(itemID);

            if (result) {
                System.out.println("Item deleted!");
            } else {
                System.out.println("Item with that ID has not been found!");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Invalid item ID!");
        }
    }
    public static void showUserItems() throws SQLException {
        ArrayList<Item> userItems = AccountController.obtainUserItems();

        if (userItems.isEmpty()) {
            System.out.println("You don't have any listed items!");
            return;
        }

        System.out.println("Your items:");
        for (Item item : userItems) {
            System.out.println(item.toString());
        }
    }
    public static void showAllItems() throws SQLException {
        ArrayList<Item> allItems = AccountController.obtainAllItems();
        int itemID;
        char option;

        if (allItems.isEmpty()) {
            System.out.println("There aren't any listed items!");
            return;
        }

        System.out.println("All items:");
        for (Item item : allItems) {
            System.out.println(item.toString());
        }
        System.out.print("Type [Y/N] to add or not to add item to favourites: ");
        option = scanner.next().charAt(0);

        if (option == 'Y' || option == 'y') {
            System.out.print("Enter item ID: ");
            itemID = scanner.nextInt();

            boolean itemAdded = false;

            try {
                itemAdded = AccountController.addItemToFavourites(itemID);
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("This item is already in your favourites list!");
            }

            if (itemAdded) {
                System.out.println("Item added!");
            }
        }
    }
    public static void showFavouriteItems() throws SQLException {
        System.out.println("Your favourite items are:");

        int itemID;
        char response;
        boolean result;

        ArrayList<Item> favouriteItems = AccountController.obtainFavouriteItems();

        for (Item item : favouriteItems) {
            System.out.println(item.toString());
        }

        System.out.println("Do you want to remove an item? Type [Y/N]");
        response = scanner.next().charAt(0);

        if (response == 'Y' || response == 'y') {
            System.out.println("Enter item ID: ");
            itemID = scanner.nextInt();

            try {
                result = AccountController.removeFavouriteItem(itemID);

                if (result) {
                    System.out.println("Item deleted!");
                } else {
                    System.out.println("The system couldn't delete the item. Try again!");
                }
            } catch (InvalidItemIDException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void modifyItem() throws SQLException {
        int itemID;
        double price;
        String name, description, condition;
        System.out.print("Enter item ID: ");
        itemID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new price: ");
        price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter new name: ");
        name = scanner.nextLine();
        System.out.print("Enter new description: ");
        description = scanner.nextLine();
        System.out.print("Enter new condition: ");
        condition = scanner.nextLine();

        boolean result = AccountController.modifyItem(itemID, price, name, description, condition);
        if (result) {
            System.out.println("Item updated successfully!");
        } else {
            System.out.println("There was an error while updating your item. Try again!");
        }
    }
}
