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
        testRun.runProgram();
        testRun.close();
    }

    /**
     * Access sql.
     */
    public void accessSQL() {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Connect to Database.");
            frame.setSize(400, 400);
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
        while (true) {
            System.out.println("Enter the furniture category: (Chair, Desk, Lamp, Filing)");
            setCategory(scanner.nextLine().strip());
            if (category.equals("Chair") || category.equals("Desk") ||
                    category.equals("Lamp") || category.equals("Filing")) {
                break;
            } else {
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

    public void runProgram() {
        OptionCalculation orderCalc = new OptionCalculation(category, type, numOfItems, database);
        boolean canOrder = orderCalc.calculateCheapestPrice();
        if (canOrder) {
            generateOrderForm(orderCalc.getLowestPriceIDs(), orderCalc.getTotalLowestPrice());
        } else {
            switch (category) {
                case "CHAIR":
                    recommendManufacturers(database.getChairList());
                    break;
                case "DESK":
                    recommendManufacturers(database.getDeskList());
                    break;
                case "LAMP":
                    recommendManufacturers(database.getLampList());
                    break;
                case "FILING":
                    recommendManufacturers(database.getFilingList());
                    break;
            }
        }
    }

    public void close() {
        database.closeConnection();
    }

    /**
     * Outputs a message in terminal if an order be fulfilled based
     * on current inventory
     */
    public void generateOrderForm(ArrayList<String> itemIDs, int price) { // output if order can be fulfilled
        try {
            // System.out.println("Purchase " + id + "and " + manuID + "for " + price + "."); // placeholder as need added price of each item.
            BufferedWriter orderFormWriter = new BufferedWriter(new FileWriter("lib/orderform.txt"));

            StringBuilder orderForm = new StringBuilder();
            orderForm.append("Furniture Order Form\n");
            orderForm.append("\n");

            orderForm.append("Faculty Name: \n");
            orderForm.append("Contact: \n");
            orderForm.append("Date: \n");
            orderForm.append("\n");

            orderForm.append("Original Request: " + type + " " + category + ", " + numOfItems +
                    "\n");
            orderForm.append("\n");

            orderForm.append("Items Ordered");
            // orderForm.append("ID: " + items we need); needs to output each type of furniture to be bought to make certain item
            //iterate this please

            orderForm.append("Total Price: " + price);

            String form = orderForm.toString();
            orderFormWriter.write(form);
            orderFormWriter.close();
        } catch (IOException e) {
            System.err.println("IO Error.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Outputs a message in terminal if an order cannot be fulfilled based
     * on current inventory
     */
    public void recommendManufacturers(ArrayList<? extends Furniture> objectList) { // method if order CANNOT be fulfilled
        ArrayList<String> recommendedManus = new ArrayList<>();
        for (int i = 0; i < objectList.size(); i++) {
            //finds the connection between the objectList's manuID to a manufacturer from the list
            //yet another friendly reminder that optimization is not graded
            for (Manufacturer manu : database.getManuList()) {
                //if the object's manufacturer ID
                if (objectList.get(i).getManuID().equals(manu.getManuID()) && recommendedManus.indexOf(manu.getManuID()) == -1) {
                    recommendedManus.add(manu.getName());
                    break;
                }
            }
        }

        //this should get you a list of manufacturer names. now you just need to output it. ^^

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
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
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
     * Sets num of items.
     *
     * @param numOfItems the num of items
     */
    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }
}