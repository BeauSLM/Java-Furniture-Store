package edu.ucalgary.ensf409;

/**
 * ItemNotFound Exception.
 */
public class ItemNotFoundException extends Exception {
    /**
     * Default constructor
     */
    public ItemNotFoundException() {
        super("Could not find specified item(s).");
    }

    /**
     * Constructor that accepts a message.
     *
     * @param message the message for the exception
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
