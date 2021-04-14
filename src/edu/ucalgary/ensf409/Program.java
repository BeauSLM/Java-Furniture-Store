package edu.ucalgary.ensf409;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
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
    private static GUIAccessSQL sqlFrame;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        sqlFrame = new GUIAccessSQL();
        EventQueue.invokeLater(() -> {
            sqlFrame.setVisible(true);
        });
    }
}

/**
 * The type Gui access sql.
 */
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

    /**
     * Instantiates a new Gui access sql.
     */
    public GUIAccessSQL() {
        super("Connect to Database.");
        setupGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets gui.
     */
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
        if(validAccess(username, password, url)) {
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
        }
        else {
            JOptionPane.showMessageDialog(null, "There was an error connecting to the database with username : "
                    + username + " and password : "+ password+"and url : " + url);
        }
    }

    /**
     * Attempts to create a databaseAccess object through info gained from the GUIAccessSQL object
     *
     * @param username Database username
     * @param password Database password
     * @param url      URL for the database inventory.
     * @return boolean Returns the status of the attempted connection
     */
    public boolean validAccess(String username, String password, String url)  {
        database = new DatabaseAccess(username, password, url);
        return database.getIsSuccessful();
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
}

/**
 * The type Gui user input.
 */
class GUIUserInput extends JFrame implements ActionListener {
    /**
     * The Database.
     */
    DatabaseAccess database;
    /**
     * The Category.
     */
    String category,
    /**
     * The Type.
     */
    type;
    /**
     * The Num of items.
     */
    int numOfItems;
    /**
     * The Choices.
     */
    final String[] choices = new String[] {"Chair", "Desk", "Lamp", "Filing"};
    /**
     * The G message 1.
     */
    JLabel gMessage1,
    /**
     * The G message 2.
     */
    gMessage2;
    /**
     * The Label.
     */
    JLabel iLabel,
    /**
     * The Noi label.
     */
    noiLabel,
    /**
     * The Type label.
     */
    typeLabel;
    /**
     * The Selection dropdown.
     */
    final JComboBox<String> selectionDropdown = new JComboBox<String>(choices);
    /**
     * The Type text field.
     */
    JTextField typeTextField,
    /**
     * The Noi text field.
     */
    noiTextField;

    /**
     * Instantiates a new Gui user input.
     *
     * @param database the database
     */
    public GUIUserInput(DatabaseAccess database) {
        super("Select Category Form.");
        this.database = database;
        setupGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets gui.
     */
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
                            "word separated by a space must start with a capital letter.");
                } else {
                    JOptionPane.showMessageDialog(null, "You have selected the following category : " + category +
                            ", type :" + type + ", number of items :" + numOfItems);
                    GUIOrderForm processForm = new GUIOrderForm(category, type, numOfItems, database);
                    this.setVisible(false);
                    EventQueue.invokeLater(()->{
                        processForm.setVisible(true);
                    });
                }
            }
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "You have inserted an invalid input for number of items.");
        }
    }
}

/**
 * The type Gui order form.
 */
class GUIOrderForm extends JFrame {
    private DatabaseAccess database;
    private String category;
    private OptionCalculation orderCalc;
    private JLabel generalMessage1, generalMessage2;

    /**
     * Instantiates a new Gui order form.
     *
     * @param category      the category
     * @param type          the type
     * @param numberOfItems the number of items
     * @param database      the database
     */
    public GUIOrderForm(String category, String type, int numberOfItems, DatabaseAccess database) {
        super("Order Form Process.");
        this.database = database;
        this.category = category;
        orderCalc = new OptionCalculation(type, numberOfItems);
        setupGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Sets gui.
     */
    public void setupGUI() {
        JPanel wrapContainer = new JPanel();
        wrapContainer.setLayout(new BoxLayout(wrapContainer, BoxLayout.PAGE_AXIS));
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        generalMessage1 = new JLabel("Welcome to the University of Calgary");
        generalMessage2 = new JLabel("Supply Chain Management Software v2.5.");
        headerPanel.add(generalMessage1);
        headerPanel.add(generalMessage2);
        wrapContainer.add(headerPanel);

        boolean calcSuccess = false;
        switch(category){
            case "Chair":
                calcSuccess = orderCalc.calculateCheapestPrice(database.getChairList());
                break;
            case "Desk":
                calcSuccess = orderCalc.calculateCheapestPrice(database.getDeskList());
                break;
            case "Lamp":
                calcSuccess = orderCalc.calculateCheapestPrice(database.getLampList());
                break;
            case "Filing":
                calcSuccess = orderCalc.calculateCheapestPrice(database.getFilingList());
                break;
        }

        if(calcSuccess) {
            generateOrderForm(orderCalc.getLowestPriceIDs(), orderCalc.getTotalLowestPrice(), category,
                    orderCalc.getType(), orderCalc.getNumOfItems());

            JPanel orderFormPanel = new JPanel();
            orderFormPanel.setLayout(new FlowLayout());

            JLabel messageLabel = new JLabel(successfulOrderString(orderCalc.getLowestPriceIDs(), orderCalc.getTotalLowestPrice()));
            orderFormPanel.add(messageLabel);
            wrapContainer.add(orderFormPanel);
            this.add(wrapContainer);
        } else {
            ArrayList<String> manufacturers = new ArrayList<>();
            switch (category) {
                case "Chair":
                    manufacturers = recommendManufacturers(database.getChairList());
                    break;
                case "Desk":
                    manufacturers = recommendManufacturers(database.getDeskList());
                    break;
                case "Lamp":
                    manufacturers = recommendManufacturers(database.getLampList());
                    break;
                case "Filing":
                    manufacturers = recommendManufacturers(database.getFilingList());
                    break;
            }
            JPanel manufacturerPanel = new JPanel();
            manufacturerPanel.setLayout(new FlowLayout());

            JLabel manuMessage1 = new JLabel("Your order can't be fulfilled with the current inventory.");
            JLabel manuMessage2 = new JLabel("Recommended Manufacturers :");
            manufacturerPanel.add(manuMessage1);
            manufacturerPanel.add(manuMessage2);

            JLabel[] manufacturerLabels = new JLabel[manufacturers.size()];
            for(int i = 0; i < manufacturerLabels.length; i++) {
                manufacturerLabels[i].setText(manufacturers.get(i));
                manufacturerPanel.add(manufacturerLabels[i]);
            }

            wrapContainer.add(manufacturerPanel);
            this.add(wrapContainer);
        }
    }

    /**
     * Makes a String that shows the itemIDs of the items that need to be purchased
     * and the total price of the items purchased in case of a successful order.
     * @param itemIDs   The IDs of the items that need to be purchased.
     * @param price     The total price of the items purchased.
     * @return          A formatted String that consists of the itemIDs of the purchased items and the total price of the purchased items.
     */
    public String successfulOrderString(ArrayList<String> itemIDs, int price) {
        StringBuilder itemList = new StringBuilder();
        itemList.append("Purchase ");
        for (int i = 0; i < (itemIDs.size() - 1); i++) { // prints out the IDs of the items ordered
            itemList.append(itemIDs.get(i) + " and ");
        }
        itemList.append(itemIDs.get(itemIDs.size() - 1) + ".");
        itemList.append(" for "+ "$"+price+".");
        return new String(itemList);
    }

    /**
     * Outputs a message in terminal if an order cannot be fulfilled based
     * on current inventory
     *
     * @param objectList list of manufacturers that sell components of the item that was ordered
     * @return the array list
     */
    public ArrayList<String> recommendManufacturers(ArrayList<? extends Furniture> objectList) { // method if order CANNOT be fulfilled
        ArrayList<String> recommendedManus = new ArrayList<>();
        for (int i = 0; i < objectList.size(); i++) {
            for (Manufacturer manu : database.getManuList()) {
                //if the object's manufacturer ID
                if (objectList.get(i).getManuID().equals(manu.getManuID()) && !recommendedManus.contains(manu.getManuID())) {
                    recommendedManus.add(manu.getName());
                    break;
                }
            }
        }

        //this should get you a list of manufacturer names. now you just need to output it. ^^

        return recommendedManus;
    }

    /**
     * Generates an order form in a text file.
     *
     * @param itemIDs    the item IDs
     * @param price      the price
     * @param category   the category
     * @param type       the type
     * @param numOfItems the number of items
     */
    public void generateOrderForm(ArrayList<String> itemIDs, int price, String category, String type, int numOfItems) { // output if order can be fulfilled
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
            System.err.println("IO Error when generating order form file.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}