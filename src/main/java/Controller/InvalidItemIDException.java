package Controller;

public class InvalidItemIDException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid item ID. Please, enter a valid one from your favourite list!";
    }
}
