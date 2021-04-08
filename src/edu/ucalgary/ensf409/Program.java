package edu.ucalgary.ensf409;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

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
    }

    /**
     * Access sql.
     */
    public void accessSQL(){
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Connect to Database.");
            frame.setSize(400,400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel connectButtonPanel = new JPanel();
            JButton connectButton = new JButton("Connect");
            connectButtonPanel.add(connectButton);
            frame.getContentPane().add(BorderLayout.NORTH, connectButtonPanel);
            frame.setVisible(true);
        });
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

    /**
     * User input.
     */
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

    /**
     * Instantiates a new Program.
     */
//default constructor - unused atm
    public Program(){
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public DatabaseAccess getDatabase() {
        return database;
    }

    /**
     * Sets database.
     *
     * @param database the database
     */
    public void setDatabase(DatabaseAccess database) {
        this.database = database;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets num of items.
     *
     * @return the num of items
     */
    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     * Sets num of items.
     *
     * @param numOfItems the num of items
     */
    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }
}