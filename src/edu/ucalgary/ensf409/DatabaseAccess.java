package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class used to access the inventory database.
 */
public class DatabaseAccess {
    private Connection dbConnect;

    private final String USERNAME;
    private final String PASSWORD;
    private final String URL;

    private ArrayList<Manufacturer> manuList;
    private ArrayList<Chair> chairList;
    private ArrayList<Desk> deskList;
    private ArrayList<Lamp> lampList; //ropes, bombs

    public DatabaseAccess(String username, String password, String url) {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.URL = url;
    }

    public void getDatabase() {
        createConnection();
        retrieveManufacturers();
        retrieveChairs();
        retrieveDesks();
        retrieveLamps();
        closeConnection();
    }

    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveManufacturers() {
        ArrayList<Manufacturer> resultList = new ArrayList<>();
        String Query = "SELECT * FROM Manufacturer";
        ResultSet results;
        try {
            Statement selectAllManus = dbConnect.createStatement();
            results = selectAllManus.executeQuery(Query);
            while(results.next()) {
                resultList.add(new Manufacturer(results.getString("ManuID"), results.getString("name"),
                            results.getString("Phone"), results.getString("Province")));
            }
            selectAllManus.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        manuList = resultList;
    }

    public void retrieveChairs() {
        ArrayList<Chair> resultList = new ArrayList<>();
        String query = "SELECT * FROM Chair";
        try {
            Statement selectAllChairs = dbConnect.createStatement();
            ResultSet results = selectAllChairs.executeQuery(query);

            while (results.next()) {
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
                resultList.add(new Chair(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("ManuID"),
                        hasLegs, hasArms, hasSeat, hasCushion));
            }
            selectAllChairs.close();
            results.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        chairList = resultList;
    }

    public void retrieveDesks() {
        ArrayList<Desk> resultList = new ArrayList<>();
        String Query = "SELECT * FROM Desk";
        try {
            Statement selectAllDesks = dbConnect.createStatement();
            ResultSet results = selectAllDesks.executeQuery(Query);

            while(results.next()) {
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
                resultList.add(new Desk(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("ManuID"),
                        usableLegs, usableTop, usableDrawer));
            }
            selectAllDesks.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        deskList = resultList;
    }

    public void retrieveLamps() {
        ArrayList<Lamp> resultList = new ArrayList<>();
        String Query = "SELECT * FROM LAMP";
        try {
            Statement selectAllLamps = dbConnect.createStatement();
            ResultSet results = selectAllLamps.executeQuery(Query);
            while(results.next()) {
                boolean usableBase = false, usableBulb = false;
                if(results.getString("Base").equals("Y")) {
                    usableBase = true;
                }
                if(results.getString("Bulb").equals("Y")) {
                    usableBulb = true;
                }
                resultList.add(new Lamp(results.getString("ID"), results.getString("Type"),
                        results.getInt("Price"), results.getString("manuID"),
                        usableBase, usableBulb));
            }
            selectAllLamps.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        lampList = resultList;
    }
    public void closeConnection() {
        try {
            dbConnect.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
