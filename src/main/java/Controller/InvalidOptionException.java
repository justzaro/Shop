package Controller;

public class InvalidOptionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid option. Please, choose 1 or 2!\n> ";
    }
}
