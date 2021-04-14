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
 * Class for main, handles input and output using a Graphic User Interface
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
 * GUIAccessSQL is used to create a Graphic User Interface that requests from the
 * user their database ID, database Password, and the URL to the database.
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

    private JButton connectButton;

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
        //Let's set up the JLabels and the JTextFields and the JButton for our GUI.
        generalMessage1 = new JLabel("Welcome to the University of Calgary");
        generalMessage2 = new JLabel("Supply Chain Management Software v2.5.");
        usernameLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password :");
        urlLabel = new JLabel("URL      :");

        usernameTextField = new JTextField("e.g. MyUsername", 18);
        passwordTextField = new JTextField("e.g. MyPassword", 18);
        urlTextField = new JTextField("e.g. http://localhost/", 30);

        connectButton = new JButton("Connect");

        //add Mouse Listeners to the JTextFields and ActionListener to the JButton
        usernameTextField.addMouseListener(this);
        passwordTextField.addMouseListener(this);
        urlTextField.addMouseListener(this);
        connectButton.addActionListener(this);

        //Create the JPanels.
        JPanel mainContainer = new JPanel();
        JPanel headerPanel = new JPanel();
        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        JPanel urlPanel = new JPanel();
        JPanel connectPanel = new JPanel();

        //Set the Layouts for the JPanels
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.PAGE_AXIS));
        headerPanel.setLayout(new FlowLayout());
        usernamePanel.setLayout(new FlowLayout());
        passwordPanel.setLayout(new FlowLayout());
        urlPanel.setLayout(new FlowLayout());
        connectPanel.setLayout(new FlowLayout());

        //Add Components to the JPanels.

        headerPanel.add(generalMessage1);
        headerPanel.add(generalMessage2);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);
        urlPanel.add(urlLabel);
        urlPanel.add(urlTextField);
        connectPanel.add(connectButton);

        //Add the JPanels to the main JPanel
        mainContainer.add(headerPanel);
        mainContainer.add(usernamePanel);
        mainContainer.add(passwordPanel);
        mainContainer.add(urlPanel);
        mainContainer.add(connectPanel);

        //Add the main panel to the JFrame.
        this.add(mainContainer);
    }

    /**
     * actionPerformed function used to handle an action performed on the
     * Connect Button.
     * @param e ActionEvent passed on the function actionPerformed
     */
    public void actionPerformed(ActionEvent e) {
        //Pull the data from the JTextFields username, password and url
        username = usernameTextField.getText();
        password = passwordTextField.getText();
        url = urlTextField.getText();
        //Attempt to create a databaseAccess object called database using the inputs provided by the user.
        if(validAccess(username, password, url)) {
            //We had a successfully created a DatabaseAccess object and connected successfully to the database. Let's
            //handle business.

            //JOptionPane dialog which notifies the user that they successfully connected.
            JOptionPane.showMessageDialog(null, "You successfully connected to the database with username : "
                    + username + " and password : "+ password+"and url : " + url);
            database.retrieveAll();
            /*WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);*/
            //Create a GUIUserInput frame and let us use EventQueue to "invokeLater" that Frame.
            GUIUserInput userInputFrame = new GUIUserInput(database);
            //close the currentFrame.
            this.setVisible(false);
            EventQueue.invokeLater(() -> {
                userInputFrame.setVisible(true);
            });
            //this.dispose();
        }
        else {
            //Unsuccessful connection to the database. Show a dialog to the user to notify them.
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
 *  Class used to create a JFrame that handles asks the user to input information
 *  needed to start the process for finding the cheaper combinations of used
 *  furniture to create another piece of furniture.
 */
class GUIUserInput extends JFrame implements ActionListener {
    DatabaseAccess database;
    String category;
    String type;
    int numOfItems;

    JLabel gMessage1;
    JLabel gMessage2;
    JLabel iLabel;
    JLabel noiLabel;
    JLabel typeLabel;

    JTextField typeTextField;
    JTextField noiTextField;

    final String[] choices = new String[] {"Chair", "Desk", "Lamp", "Filing"};
    final JComboBox<String> selectionDropdown = new JComboBox<String>(choices);
    /**
     * Instantiates a new GUI for user input.
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
     * Function used to Setup the Graphic User Interface
     */
    public void setupGUI() {
        //Instantiate the JLabels
        gMessage1 = new JLabel("Welcome to the University of Calgary");
        gMessage2 = new JLabel("Supply Chain Management Software v2.5.");
        iLabel = new JLabel("Select the furniture category: ");
        noiLabel = new JLabel("Number of Items :");
        typeLabel = new JLabel("Type of furniture :");

        //Instantiate the JTextFields.
        noiTextField = new JTextField(18);
        typeTextField = new JTextField(18);

        //create the Select button and add an action Listener object to it.
        JButton selectCategoryButton = new JButton("Select");
        selectCategoryButton.addActionListener(this);

        //create Panels
        JPanel wrapContainer = new JPanel();
        JPanel headerPanel = new JPanel();
        JPanel selectorPanel = new JPanel();
        JPanel typePanel = new JPanel();
        JPanel noiPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Set the Panel Layouts
        wrapContainer.setLayout(new BoxLayout(wrapContainer, BoxLayout.PAGE_AXIS));
        headerPanel.setLayout(new FlowLayout());
        selectorPanel.setLayout(new FlowLayout());
        typePanel.setLayout(new FlowLayout());
        noiPanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout());

        //Add JLabels, JTextFields and JButton to appropriate panels.
        headerPanel.add(gMessage1);
        headerPanel.add(gMessage2);

        selectorPanel.add(iLabel);
        selectorPanel.add(selectionDropdown);

        typePanel.add(typeLabel);
        typePanel.add(typeTextField);

        noiPanel.add(noiLabel);
        noiPanel.add(noiTextField);

        buttonPanel.add(selectCategoryButton);

        //Add all the JPanels to the wrapper Panel.
        wrapContainer.add(headerPanel);
        wrapContainer.add(selectorPanel);
        wrapContainer.add(typePanel);
        wrapContainer.add(noiPanel);
        wrapContainer.add(buttonPanel);

        //Add the wrapper JPanel to the JFrame itself.
        this.add(wrapContainer);
    }

    /**
     * actionPerformed function used to handle interaction with the User Input GUI.
     * @param e Event handler in the case the button is interacted with.
     */
    public void actionPerformed(ActionEvent e) {
        String noiString;
        String[] furnitureTypeArray;

        //Get the data provided from the Graphic User Intefrace.
        category = selectionDropdown.getSelectedItem().toString();
        type = typeTextField.getText();
        noiString = noiTextField.getText();

        //Let's attempt to parse the number of Items we would like to create.
        try {
            numOfItems = Integer.parseInt(noiString);
            if(numOfItems <= 0) {
                //Illegal argument. The number of Items must be positive
                JOptionPane.showMessageDialog(null, "You have inserted non-positive Integer for number of items.");
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
                    //Show to the user what they have inputted, and prompt them with the result of their selection.
                    JOptionPane.showMessageDialog(null, "You have selected the following category : " + category +
                            ", type :" + type + ", number of items :" + numOfItems);
                    //Create a new GUIOrderForm object and then let's attempt to show on the screen the results of the
                    //user request.
                    GUIOrderForm processForm = new GUIOrderForm(category, type, numOfItems, database);
                    //hide the current frame.
                    this.setVisible(false);
                    EventQueue.invokeLater(()->{
                        processForm.setVisible(true);
                    });
                }
            }
        }
        catch(Exception ex) {
            //We caught an exception when attemption to convert to integer. User has given an invalid input.
            JOptionPane.showMessageDialog(null, "You have inserted an invalid input for number of items.");
            ex.printStackTrace();
        }
    }
}

/**
 * This class is used to process the user input provided in GUIUserInput
 */
class GUIOrderForm extends JFrame {
    private DatabaseAccess database;
    private String category;
    private OptionCalculation orderCalc;
    private JLabel generalMessage1;
    private JLabel generalMessage2;

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
        //create an OptionCalculation Object used to process the user Order.
        orderCalc = new OptionCalculation(type, numberOfItems);
        setupGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *  setupGUI function sets up the Graphic User Interface for the OrderForm process.
     */
    public void setupGUI() {
        boolean calcSuccess = false;
        //Setup the top part of the Graphic Interface and the wrapper JPanel
        JPanel wrapContainer = new JPanel();
        JPanel headerPanel = new JPanel();

        //Setup the Layouts for the wrapper JPanel and the header JPanel
        wrapContainer.setLayout(new BoxLayout(wrapContainer, BoxLayout.PAGE_AXIS));
        headerPanel.setLayout(new FlowLayout());

        //instantiate the header JLabels
        generalMessage1 = new JLabel("Welcome to the University of Calgary");
        generalMessage2 = new JLabel("Supply Chain Management Software v2.5.");

        //add the JLabels to the headerPanel
        headerPanel.add(generalMessage1);
        headerPanel.add(generalMessage2);

        //add the header JPanel to the wrapper JPanel
        wrapContainer.add(headerPanel);

        //Let's attempt to calculate the cheapest price for the creation of the object.
        //Assign the status of the calculation to the variable calcSuccess
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
            //Let's first delete all items on the list.
            for(Object id : orderCalc.getLowestPriceIDs()){
                database.deleteItem(category, (String)id); //what the fuck java
            }

            //Create the Order Form in the text file.
            generateOrderForm(orderCalc.getLowestPriceIDs(), orderCalc.getTotalLowestPrice(), category,
                    orderCalc.getType(), orderCalc.getNumOfItems());

            //Create the JPanels needed and their layouts
            JPanel orderFormPanel = new JPanel();
            orderFormPanel.setLayout(new FlowLayout());

            //create a Label that shows what is the cheapest option.
            JLabel messageLabel = new JLabel(successfulOrderString(orderCalc.getLowestPriceIDs(), orderCalc.getTotalLowestPrice()));
            orderFormPanel.add(messageLabel);
            wrapContainer.add(orderFormPanel);
        } else {
            //recommends manufacturers based on the list of producers of the category
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
            //Create a JPanel to hold the Manufacturer Recommendation information
            JPanel manufacturerPanel = new JPanel();
            manufacturerPanel.setLayout(new FlowLayout());

            //Create the messages for the user.
            JLabel manuMessage1 = new JLabel("Your order can't be fulfilled with the current inventory.");
            JLabel manuMessage2 = new JLabel("Recommended Manufacturers :");

            //Add JLabels to the Panel
            manufacturerPanel.add(manuMessage1);
            manufacturerPanel.add(manuMessage2);

            //Depending on how many manufacturers are to be recommended create the appropriate JLabels
            //with their info.
            JLabel[] manufacturerLabels = new JLabel[manufacturers.size()];
            for(int i = 0; i < manufacturerLabels.length; i++) {
                manufacturerLabels[i].setText(manufacturers.get(i));
                manufacturerPanel.add(manufacturerLabels[i]);
            }

            wrapContainer.add(manufacturerPanel);
        }
        this.add(wrapContainer);
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
     * Returns an array List of recommended manufacturers in the case we can't fulfill an order
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