package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class used to access the inventory database.
 */
public class DatabaseAccess {
    //SQL Connection stuff
    private final String USERNAME;
    private final String PASSWORD;
    private final String URL;
    private Connection dbConnect;

    //ArrayLists that store the Database info
    private ArrayList<Manufacturer> manuList;
    private ArrayList<Chair> chairList;
    private ArrayList<Desk> deskList;
    private ArrayList<Lamp> lampList; //ropes, bombs

    /**
     * Instantiates a new Database access and calls a function to retrieve its data.
     *
     * @param username the username
     * @param password the password
     * @param url      the url
     */
    public DatabaseAccess(String username, String password, String url) {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.URL = url;
        updateDatabaseAccess();
    }

    /**
     * Creates connection to sql DB, fills the class lists with the database's data.
     * This calls all the other functions.
     */
    public void updateDatabaseAccess() {
        createConnection();
        retrieveManufacturers();
        retrieveChairs();
        retrieveDesks();
        retrieveLamps();
        closeConnection();
    }

    /**
     * Create connection to SQL database.
     */
    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an ArrayList of Manufacturers with data from the SQL database.
     */
    public void retrieveManufacturers() {
        ArrayList<Manufacturer> resultList = new ArrayList<>();
        String Query = "SELECT * FROM Manufacturer";
        ResultSet results;
        try {
            Statement selectAllManus = dbConnect.createStatement();
            results = selectAllManus.executeQuery(Query); //query returned at this point
            while(results.next()) { //for each result:
                //add a Manufacturer object to the list
                resultList.add(new Manufacturer(results.getString("ManuID"), results.getString("name"),
                            results.getString("Phone"), results.getString("Province")));
            }
            //close it out
            selectAllManus.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        manuList = resultList; //update field
    }

    /**
     * Creates an ArrayList of Chairs with data from the SQL database.
     */
    public void retrieveChairs() {
        ArrayList<Chair> resultList = new ArrayList<>();
        String query = "SELECT * FROM Chair";
        try {
            Statement selectAllChairs = dbConnect.createStatement();
            ResultSet results = selectAllChairs.executeQuery(query); //query returned by this point

            while (results.next()) { //for each result:
                //converts "Y"/"N" to boolean
                boolean hasLegs = false, hasArms = false, hasSeat = false, hasCushion = false;
                if (results.getString("Leg").equals("Y")) {
                    hasLegs = true;
                }
                if (results.getString("Arms").equals("Y")) {
                    hasArms = true;
                }
                if (results.getString("Seat").equals("Y")) {
                    hasSeat = true;
                }
                if (results.getString("Cushion").equals("Y")) {
                    hasCushion = true;
                }
                //creates a Chair object and adds to list
                resultList.add(new Chair(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("ManuID"),
                        hasLegs, hasArms, hasSeat, hasCushion));
            }
            //done! just close stuff now.
            selectAllChairs.close();
            results.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        chairList = resultList; //updates field
    }

    /**
     * Creates an ArrayList of Desks with data from the SQL database.
     */
    public void retrieveDesks() {
        ArrayList<Desk> resultList = new ArrayList<>();
        String query = "SELECT * FROM Desk";
        try {
            Statement selectAllDesks = dbConnect.createStatement();
            ResultSet results = selectAllDesks.executeQuery(query); //query returned at this point

            while(results.next()) { //for each result:
                //converts "Y"/"N" to boolean
                boolean usableLegs = false, usableTop = false, usableDrawer = false;
                if(results.getString("Legs").equals("Y")) {
                    usableLegs = true;
                }
                if(results.getString("Top").equals("Y")) {
                    usableTop = true;
                }
                if(results.getString("Drawer").equals("Y")) {
                    usableDrawer = true;
                }
                //creates Desk object and adds to list
                resultList.add(new Desk(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("ManuID"),
                        usableLegs, usableTop, usableDrawer));
            }
            //close em up!
            selectAllDesks.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        deskList = resultList; //updates field
    }

    /**
     * Creates an ArrayList of Lamps with data from the SQL database.
     */
    public void retrieveLamps() {
        ArrayList<Lamp> resultList = new ArrayList<>();
        String query = "SELECT * FROM Lamp";
        try {
            Statement selectAllLamps = dbConnect.createStatement();
            ResultSet results = selectAllLamps.executeQuery(query); //query returned at this point
            while(results.next()) { //for each result:
                //converts "Y"/"N" to boolean
                boolean usableBase = false, usableBulb = false;
                if(results.getString("Base").equals("Y")) {
                    usableBase = true;
                }
                if(results.getString("Bulb").equals("Y")) {
                    usableBulb = true;
                }
                //create and add Lamp object to the list
                resultList.add(new Lamp(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("manuID"),
                        usableBase, usableBulb));
            }
            //close stuff out!
            selectAllLamps.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        lampList = resultList; //updates field
    }

    /**
     * Closes the sql connection.
     */
    public void closeConnection() {
        try {
            dbConnect.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
