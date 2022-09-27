package connectfour;

/**
 * Exception to indicate when invalid moves are made.
 */
public class InvalidMoveException extends Exception {

    /**
     * Constructor method to create an connectfour.InvalidMoveException
     * @param message Message to display to the user
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
