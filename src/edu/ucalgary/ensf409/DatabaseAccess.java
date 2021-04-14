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
    public DatabaseAccess(String username, String password, String url) {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.URL = url;
        createConnection();
        retrieveAll();
    }

    /**
     * Creates connection to sql DB, fills the class lists with the database's data.
     * This calls all the other functions.
     */
    public void retrieveAll() {
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

    /**
     * Delete item.
     * @param category the category
     * @param id   the id
     */
    public void deleteItem(String category, String id){
        String query = "DELETE FROM " + category + " WHERE ID = '" + id + "'";
        try {
            Statement deleteItem = dbConnect.createStatement();
            deleteItem.executeUpdate(query);
            //if our query is executed let us remove the item from its respective collection
            switch(category) {
                case "Chair":
                    for(int i=0; i < chairList.size(); i++) {
                        if(chairList.get(i).getId().equals(id)) {
                            chairList.remove(i);
                        }
                    }
                    break;
                case "Filing":
                    for(int i=0; i < filingList.size(); i++) {
                        if(filingList.get(i).getId().equals(id)) {
                            filingList.remove(i);
                        }
                    }
                    break;
                case "Desk":
                    for(int i=0; i < deskList.size(); i++) {
                        if(deskList.get(i).getId().equals(id)) {
                            deskList.remove(i);
                        }
                    }
                    break;
                case "Lamp":
                    for(int i=0; i < lampList.size(); i++) {
                        if(lampList.get(i).getId().equals(id)) {
                            lampList.remove(i);
                        }
                    }
                    break;
            }
            deleteItem.close();
        }
        catch(SQLException e){
            System.out.println("Error deleting item " + id);
            e.printStackTrace();
        }
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

    /**
     * Gets manu list.
     *
     * @return the manu list
     */
    public ArrayList<Manufacturer> getManuList() { return this.manuList; }

    /**
     * Gets chair list.
     *
     * @return the chair list
     */
    public ArrayList<Chair> getChairList() { return this.chairList; }

    /**
     * Gets desk list.
     *
     * @return the desk list
     */
    public ArrayList<Desk> getDeskList() { return this.deskList; }

    /**
     * Gets lamp list.
     *
     * @return the lamp list
     */
    public ArrayList<Lamp> getLampList() { return this.lampList; }

    /**
     * Gets filing list.
     *
     * @return the filing list
     */
    public ArrayList<Filing> getFilingList() { return this.filingList; }

    /**
     * Gets dbConnect.
     *
     * @return the dbConnect
     */
    public Connection getDbConnect() {
        return this.dbConnect;
    }

    /**
     * Gets isSuccessful (status of whether the connection to the database was successful).
     *
     * @return the isSuccessful
     */
    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUSERNAME() {
        return USERNAME;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPASSWORD() {
        return PASSWORD;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() {
        return URL;
    }


    /**
     * Sets successful.
     *
     * @param successful the successful
     */
    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}

