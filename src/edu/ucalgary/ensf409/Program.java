package edu.ucalgary.ensf409;

import java.io.*;
import java.util.*;

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

        String username;
        String password;
        String URL;

        System.out.println("When prompted to enter, enter the necessary informatiion, then press return.");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username: ");
        username = scanner.nextLine().strip();
        System.out.println("Enter your password: ");
        password = scanner.nextLine().strip();
        System.out.println("Enter your db URL, or press return ONCE for the default: ");
        String tmp = scanner.nextLine().strip();
        if (tmp.equals("")) {
            URL = "jdbc:mysql://localhost/inventory";
        } else {
            URL = new String(tmp);
        }

        scanner.close();

        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("Url is: " + URL);

        DatabaseAccess dBConnect = new DatabaseAccess(username, password, URL);
    }
}