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
    private boolean isSuccessful;

    //ArrayLists that store the Database info
	private String category;
    private ArrayList<Manufacturer> manuList;
    private ArrayList<Chair> chairList;
    private ArrayList<Desk> deskList;
    private ArrayList<Lamp> lampList;
    private ArrayList<Filing> filingList;

    /**
     * Instantiates a new Database access and calls a function to retrieve its data.
     *
     * @param username the username
     * @param password the password
     * @param url      the url
     */
    public DatabaseAccess(String username, String password, String url, String category) {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.URL = url;
		this.category = category;
        createConnection();
    }

    /**
     * Creates connection to sql DB, fills the class lists with the database's data.
     * This calls all the other functions.
     */
    public void retrieveList() {
		retrieveManufacturers();
        retrieveChairs();
        retrieveDesks();
        retrieveLamps();
        retrieveFilings();
    }

    /**
     * Create connection to SQL database.
     */
    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            isSuccessful = true;
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        }
    }

    /**
     * Creates an ArrayList of Manufacturers with data from the SQL database.
     */
    public void retrieveManufacturers() {
        ArrayList<Manufacturer> resultList = new ArrayList<>();
        String Query = "SELECT * FROM MANUFACTURER";
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
        String query = "SELECT * FROM CHAIR";
        try {
            Statement selectAllChairs = dbConnect.createStatement();
            ResultSet results = selectAllChairs.executeQuery(query); //query returned by this point

            while (results.next()) { //for each result:
                //converts "Y"/"N" to boolean
                boolean hasLegs = false, hasArms = false, hasSeat = false, hasCushion = false;
                if (results.getString("Legs").equals("Y")) {
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
        String query = "SELECT * FROM DESK";
        try {
            Statement selectAllDesks = dbConnect.createStatement();
            ResultSet results = selectAllDesks.executeQuery(query); //query returned at this point

            while(results.next()) { //for each result:
                //converts "Y"/"N" to boolean
                boolean hasLegs = false, hasTop = false, hasDrawer = false;
                if(results.getString("Legs").equals("Y")) {
                    hasLegs = true;
                }
                if(results.getString("Top").equals("Y")) {
                    hasTop = true;
                }
                if(results.getString("Drawer").equals("Y")) {
                    hasDrawer = true;
                }
                //creates Desk object and adds to list
                resultList.add(new Desk(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("ManuID"),
                        hasLegs, hasTop, hasDrawer));
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
        String query = "SELECT * FROM LAMP";
        try {
            Statement selectAllLamps = dbConnect.createStatement();
            ResultSet results = selectAllLamps.executeQuery(query); //query returned at this point
            while(results.next()) { //for each result:
                //converts "Y"/"N" to boolean
                boolean hasBase = false, hasBulb = false;
                if(results.getString("Base").equals("Y")) {
                    hasBase = true;
                }
                if(results.getString("Bulb").equals("Y")) {
                    hasBulb = true;
                }
                //create and add Lamp object to the list
                resultList.add(new Lamp(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("manuID"),
                        hasBase, hasBulb));
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
     * Creates an ArrayList of Lamps with data from the SQL database.
     */
    public void retrieveFilings() {
        ArrayList<Filing> resultList = new ArrayList<>();
        String query = "SELECT * FROM FILING";
        try {
            Statement selectAllFiling = dbConnect.createStatement();
            ResultSet results = selectAllFiling.executeQuery(query); //query returned at this point
            while(results.next()) { //for each result:
                //converts "Y"/"N" to boolean
                boolean hasRails = false, hasDrawers = false, hasCabinet = false;
                if(results.getString("Rails").equals("Y")) {
                    hasRails = true;
                }
                if(results.getString("Drawers").equals("Y")) {
                    hasDrawers = true;
                }
                if(results.getString("Cabinet").equals("Y")) {
                    hasCabinet = true;
                }
                //create and add Lamp object to the list
                resultList.add(new Filing(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("manuID"),
                        hasRails, hasDrawers, hasCabinet));
            }
            //close stuff out!
            selectAllFiling.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        filingList = resultList; //updates field
    }

    public void deleteItem(String type, String id){

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

    public ArrayList<Manufacturer> getManuList() { return this.manuList; }
    public ArrayList<Chair> getChairList() { return this.chairList; }
    public ArrayList<Desk> getDeskList() { return this.deskList; }
    public ArrayList<Lamp> getLampList() { return this.lampList; }
    public ArrayList<Filing> getFilingList() { return this.filingList; }

    public Connection getDbConnect() {
        return this.dbConnect;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }

}

