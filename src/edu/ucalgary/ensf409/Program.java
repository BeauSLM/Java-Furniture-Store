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
        setDatabase(new DatabaseAccess(username, password, url));
        System.out.println("Program successfully accessed database.");
    }

    public void userInput() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the furniture category: (Chair, Desk, Lamp, Filing)");
            setCategory(scanner.nextLine().strip());
            if(category.equals("Chair") || category.equals("Desk") ||
                    category.equals("Lamp") || category.equals("Filing")) {
                break;
            }
            else {
                System.out.println("Invalid category");
            }
        }

        System.out.println("Enter the type of " + category + ": ");
        setType(scanner.nextLine().strip());

        System.out.println("Enter the number of items:");
        setNumOfItems(Integer.parseInt(scanner.nextLine().strip()));

        scanner.close();
        System.out.println("Category: " + category);
        System.out.println("Type: " + type);
        System.out.println("Number of Items: " + numOfItems);
    }

    //default constructor - unused atm
    public Program(){
    }

    public DatabaseAccess getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseAccess database) {
        this.database = database;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }
}