package View;

import Controller.InvalidOptionException;
import Model.DatabaseConnection;
import Model.Item;
import Model.ItemUpdates;
import Model.UserUpdates;
import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        int option;
        
        DatabaseConnection.establishConnection();
        UserUpdates.obtainUsers();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Shop!");
        System.out.print("Enter 1 to log in or 2 to sign up:\n> ");

        while (true) {
            try {
                option = Integer.parseInt(scanner.nextLine());

                if (option != 1 && option != 2) {
                    throw new InvalidOptionException();
                }

                break;
            } catch (NumberFormatException e) {
                System.out.print("You must enter a valid option. Try again:\n> ");
            } catch (InvalidOptionException e) {
                System.out.print(e.getMessage());
            }
        }

        if (option != 1) {
            LaunchMenu.signUpMenu();
        }
        LaunchMenu.logInMenu();

        ProfileMenu.profileOptions();
    }
}
