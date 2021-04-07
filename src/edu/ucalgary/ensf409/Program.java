package edu.ucalgary.ensf409;

import java.io.*;
import java.util.*;

/**
 * Class for main, handles input and output using the terminal.
 */
public class Program {
    private DatabaseAccess database;
    private String category;
    private String type;
    private int numOfItems;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("When prompted to enter, enter the necessary information, then press return.");
        Program testRun = new Program();
        testRun.accessSQL();
        testRun.userInput();
        testRun.calculateCheapestOption();
    }

    //default constructor - unused atm
    public Program(){
    }

    public void accessSQL(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username: ");
        String username = scanner.nextLine().strip();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine().strip();

        System.out.println("Enter your database URL, or press return ONCE for the default: ");
        String tmp = scanner.nextLine().strip();
        String url;
        if (tmp.equals("")) {
            url = "jdbc:mysql://localhost/inventory";
        } else {
            url = new String(tmp);
        }
        scanner.close();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Url: " + url);

        System.out.println("\nAccessing database...");
        database = new DatabaseAccess(username, password, url);
        System.out.println("Program successfully accessed database.");
    }

    public void userInput(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the furniture category: (Chair, Desk, Lamp, Filing)");
            category = scanner.nextLine().strip();
            if(category.equals("Chair") || category.equals("Desk") ||
                    category.equals("Lamp") || category.equals("Filing")) {
                break;
            }
        }

        System.out.println("Enter the type of " + category + ": ");
        type = scanner.nextLine().strip();

        System.out.println("Enter the number of items:");
        numOfItems = Integer.parseInt(scanner.nextLine().strip());

        scanner.close();
        System.out.println("Category: " + category);
        System.out.println("Type: " + type);
        System.out.println("Number of Items: " + numOfItems);
    }

    public void calculateCheapestOption(){

    }
}