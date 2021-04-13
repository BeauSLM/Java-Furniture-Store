package edu.ucalgary.ensf409;

import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;

/**
* <h1>Program</h1>
* This program implements an application that
* is used to determine the cheapest combination of
* inventory items that can be used to fulfill an order that
* is specified. The application calculates the most cost effective
* way of assembling a certain item using components from other items. 
*
* @author  Beau McCartney, Apostolos Scondrianis, Quentin Jennings, Jacob Lansang
* @version 1.0
*/

/**
 * Class for main, handles input and output using the terminal.
 */
public class Program {
    private DatabaseAccess database;
    private String category;
    private String type;
    private int numOfItems;
    private static GUIAccessSQL sqlFrame;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("When prompted to enter, enter the necessary information, then press return.");
        Program testRun = new Program();
        sqlFrame = new GUIAccessSQL();
        EventQueue.invokeLater(() -> {
            sqlFrame.setVisible(true);
        });
        //testRun.accessSQL();
        //testRun.userInput();
        //testRun.runProgram();
        //testRun.close();
    }

    /**
     * Access sql.
     */
    public void accessSQL() {
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
     * Outputs a message in terminal of an order be fulfilled based
     * on current inventory
     * 
     * @param itemIDs the lists of items that are used to fulfill the order
     * @param price   the total price of the items that are purchased       
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

            orderForm.append("Original Request: " + type + " " + category + ", " + numOfItems + "\n");
            orderForm.append("\n");

            orderForm.append("Items Ordered\n");
            for (int i = 0; i < itemIDs.size(); i++) { // prints out the IDs of the items ordered
                orderForm.append("ID: " + itemIDs.get(i) + "\n");
            }
            //iterate this please

            orderForm.append("\n");
            orderForm.append("Total Price: $" + price);

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
     * 
     * @param objectList list of manufacturers that sell components of the item that was ordered
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

                // code under this outputs recommended manufacturers
        StringBuilder manuList = new StringBuilder();
        for (int i = 0; i < (recommendedManus.size() - 1); i++) {
            manuList.append(recommendedManus.get(i));
            manuList.append(", ");
        }

        manuList.append("and ");
        manuList.append(recommendedManus.get(recommendedManus.size() - 1));
        manuList.append(".");

        System.out.println("Order cannot be fulfilled based on current inventory. Suggested manufacturers are " + manuList);
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

class GUIUserInput extends JFrame implements ActionListener {
    DatabaseAccess database;
    String category;
    String type;
    int numOfItems;
    final String[] choices = new String[] {"Chair", "Desk", "Lamp", "Filing"};
    JLabel gMessage1;
    JLabel gMessage2;
    JLabel iLabel;
    JLabel typeLabel;
    JLabel noiLabel; //number of items label
    final JComboBox<String> selectionDropdown = new JComboBox<String>(choices);
    JTextField typeTextField;
    JTextField noiTextField;




    public GUIUserInput(DatabaseAccess database) {
        super("Select Category Form.");
        this.database = database;
        setupGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupGUI() {
        gMessage1 = new JLabel("Welcome to the University of Calgary");
        gMessage2 = new JLabel("Supply Chain Management Software v2.5.");
        iLabel = new JLabel("Select the furniture category: ");
        noiLabel = new JLabel("Number of Items :");
        typeLabel = new JLabel("Type of furniture :");
        noiTextField = new JTextField(18);
        typeTextField = new JTextField(18);

        JButton selectCategoryButton = new JButton("Select");
        selectCategoryButton.addActionListener(this);

        //create Panels
        JPanel wrapContainer = new JPanel();
        wrapContainer.setLayout(new BoxLayout(wrapContainer, BoxLayout.PAGE_AXIS));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        JPanel selectorPanel = new JPanel();
        selectorPanel.setLayout(new FlowLayout());

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());

        JPanel noiPanel = new JPanel();
        noiPanel.setLayout(new FlowLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        //add stuff to panels.
        headerPanel.add(gMessage1);
        headerPanel.add(gMessage2);

        selectorPanel.add(iLabel);
        selectorPanel.add(selectionDropdown);


        typePanel.add(typeLabel);
        typePanel.add(typeTextField);

        noiPanel.add(noiLabel);
        noiPanel.add(noiTextField);



        buttonPanel.add(selectCategoryButton);

        //add everything to a wrapper panel
        wrapContainer.add(headerPanel);
        wrapContainer.add(selectorPanel);
        wrapContainer.add(typePanel);
        wrapContainer.add(noiPanel);
        wrapContainer.add(buttonPanel);

        //add the panel to the wrapper
        this.add(wrapContainer);
    }

    public void actionPerformed(ActionEvent e) {
        String noiString;
        String[] furnitureTypeArray;
        category = selectionDropdown.getSelectedItem().toString();
        type = typeTextField.getText();
        noiString = noiTextField.getText();

        try {
            numOfItems = Integer.parseInt(noiString);
            if(numOfItems < 0) {
                JOptionPane.showMessageDialog(null, "You have inserted a negative Integer for number of items.");
            } else {
                //check if the words of type are properly capitalized.
                furnitureTypeArray = type.split(" ");
                boolean capitalizationFlag = false;
                for(int i = 0; i < furnitureTypeArray.length; i++) {
                    if(furnitureTypeArray[i].charAt(0) <= 'Z' && furnitureTypeArray[i].charAt(0) >= 'A') {
                        capitalizationFlag = true;
                    } else {
                        capitalizationFlag = false;
                        break;
                    }
                }
                if(capitalizationFlag == false) {
                    JOptionPane.showMessageDialog(null, "Your type is in the wrong form, each"+
                            "word separated by space must start with a capital letter.");
                } else {
                    JOptionPane.showMessageDialog(null, "You have selected the following category : " + category +
                            ", type :" + type + ", number of items :" + numOfItems);
                }
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "You have inserted an invalid input for number of items.");
        }

    }

}
class GUIAccessSQL extends JFrame implements ActionListener, MouseListener {
    private DatabaseAccess database;
    private String username;
    private String password;
    private String url;

    private JLabel generalMessage1;
    private JLabel generalMessage2;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel urlLabel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField urlTextField;

    public GUIAccessSQL() {
        super("Connect to Database.");
        setupGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupGUI() {
        //Let's set up our Labels and TextFields and messages for the form.
        generalMessage1 = new JLabel("Welcome to the University of Calgary");
        generalMessage2 = new JLabel("Supply Chain Management Software v2.5.");
        usernameLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password :");
        urlLabel = new JLabel("URL      :");
        usernameTextField = new JTextField("e.g. MyUsername", 18);
        passwordTextField = new JTextField("e.g. MyPassword", 18);
        urlTextField = new JTextField("e.g. http://localhost/", 30);

        usernameTextField.addMouseListener(this);
        passwordTextField.addMouseListener(this);
        urlTextField.addMouseListener(this);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(this);

        //JPanels instantiation
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.PAGE_AXIS));
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout());

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout());

        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new FlowLayout());

        JPanel connectPanel = new JPanel();
        connectPanel.setLayout(new FlowLayout());

        //add components to their Panels

        headerPanel.add(generalMessage1);
        headerPanel.add(generalMessage2);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);
        urlPanel.add(urlLabel);
        urlPanel.add(urlTextField);
        connectPanel.add(connectButton);
        //add Panels to the Frame
        mainContainer.add(headerPanel);
        mainContainer.add(usernamePanel);
        mainContainer.add(passwordPanel);
        mainContainer.add(urlPanel);
        mainContainer.add(connectPanel);
        this.add(mainContainer);
    }

    public void actionPerformed(ActionEvent e) {

        username = usernameTextField.getText();
        password = passwordTextField.getText();
        url = urlTextField.getText();
        if(validAccess(username,password, url)) {
            JOptionPane.showMessageDialog(null, "You successfully connected to the database with username : "
                    + username + " and password : "+ password+"and url : " + url);
            database.retrieveAll();
            /*WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);*/
            GUIUserInput userInputFrame = new GUIUserInput(database);
            this.setVisible(false);
            EventQueue.invokeLater(() -> {
                userInputFrame.setVisible(true);
            });
            //this.dispose();
        }else {
            JOptionPane.showMessageDialog(null, "There was an error connecting to the database with username : "
                    + username + " and password : "+ password+"and url : " + url);
        }
    }

    public void mouseEntered(MouseEvent event) {

    }

    public void mouseClicked(MouseEvent event) {

        if(event.getSource().equals(usernameTextField)) {
            usernameTextField.setText("");
        }

        if(event.getSource().equals(passwordTextField)) {
            passwordTextField.setText("");
        }

        if(event.getSource().equals(urlTextField)) {
            urlTextField.setText("");
        }
    }

    public void mouseExited(MouseEvent event) {

    }

    public void mousePressed(MouseEvent event) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Attempts to create a databaseAccess object through info gained from the GUIAccessSQL object
     * @param username  Database username
     * @param password  Database password
     * @param url       URL for the database inventory.
     * @return boolean  Returns the status of the attempted connection
     */
    public boolean validAccess(String username, String password, String url)  {
        database = new DatabaseAccess(username, password, url);
        return database.getIsSuccessful();
    }
}