package edu.ucalgary.ensf409;

/**
 * Class for main, handles input and output using the terminal.
 */
public class Program {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Manufacturer manufacturers[] = null;
        Desk desks[] = null;
        Lamp lamps[] = null;
        Chair chairs[] = null;
        String username = "ENSF409";
        String password = "ensf409";
        String URL = "jdbc:mysql://localhost/inventory";
        DatabaseAccess dbAccess = new DatabaseAccess(username, password, URL);
    }
}