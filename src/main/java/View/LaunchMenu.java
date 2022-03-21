package View;

import Controller.AccountController;
import Model.UserUpdates;
import java.util.Scanner;

public class LaunchMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void signUpMenu() {
        String firstName, lastName, email, password;

        System.out.print("Enter your first name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        lastName = scanner.nextLine();
        System.out.print("Enter your email: ");
        email = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();

        AccountController.signUp(firstName, lastName, email, password);
    }
    public static void logInMenu() {
        boolean accountExits;
        String email, password;

        System.out.print("Enter your email: ");
        email = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();

        accountExits = AccountController.logIn(email, password);

        if (!accountExits) {
            System.out.println("Incorrect email or password. Try again!");
            logInMenu();
        } else {
            int userIndex = AccountController.userIndex;
            String loggedInUser = UserUpdates.users.get(userIndex).getFirstName();
            System.out.println("\nSuccessful log in!");
            System.out.printf("Welcome back, %s!", loggedInUser);
        }
    }
}
