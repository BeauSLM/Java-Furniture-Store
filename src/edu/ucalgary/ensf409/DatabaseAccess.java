package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class used to access the inventory database.
 */
public class DatabaseAccess {
    private Connection dbConnect;

    private String username;
    private String password;
    private String URL;

    public DatabaseAccess(String username, String password, String URL) {
        this.username = username;
        this.password = password;
        this.URL = URL;
    }

    public void createConnection(String username, String password, String URL) {
        try {
            dbConnect = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Manufacturer> retrieveManufacturers() {
        ArrayList<Manufacturer> result = new ArrayList<>();
        String Query = "SELECT * FROM MANUFACTURER";
        ResultSet results;
        try {
            Statement manuStatement = dbConnect.createStatement();
            results = manuStatement.executeQuery(Query);
            while(results.next()) {
                result.add(new Manufacturer(results.getString("ManuID"),results.getString("name"),
                            results.getString("Phone"),results.getString("Province")));
            }
            manuStatement.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Chair[] retrieveChairs() {
        Chair[] result = null;
        return result;
    }
    public ArrayList<Desk> retrieveDesks() {
        ArrayList<Desk> result = new ArrayList<>();
        String Query = "SELECT * FROM Desk";
        ResultSet results;
        try {
            Statement manuStatement = dbConnect.createStatement();
            results = manuStatement.executeQuery(Query);
            boolean usableLegs = false;
            boolean usableTop = false;
            boolean usableDrawer = false;
            while(results.next()) {
                if(results.getString("Legs").equals("Y")) {
                    usableLegs = true;
                } else {
                    usableLegs = false;
                }
                if(results.getString("Top").equals("Y")) {
                    usableTop = true;
                } else {
                    usableTop = false;
                }
                if(results.getString("Drawer").equals("Y")) {
                    usableDrawer = true;
                } else {
                    usableDrawer = false;
                }
                result.add(new Desk(results.getString("ID"),results.getString("Type"),
                        results.getInt("Price"),results.getString("ManuID"), usableLegs, usableTop, usableDrawer));
            }
            manuStatement.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public ArrayList<Lamp> retrieveLamps() {
        ArrayList<Lamp> result = new ArrayList<>();
        String Query = "SELECT * FROM LAMP";
        ResultSet results;
        try {
            Statement manuStatement = dbConnect.createStatement();
            results = manuStatement.executeQuery(Query);
            boolean usableBase = false;
            boolean usableBulb = false;
            while(results.next()) {
                if(results.getString("Base").equals("Y")) {
                    usableBase = true;
                } else {
                    usableBase = false;
                }
                if(results.getString("Bulb").equals("Y")) {
                    usableBulb = true;
                } else {
                    usableBulb = false;
                }
                result.add(new Lamp(results.getString("ID"),results.getString("Type"),
                        results.getInt("Price"),results.getString("manuID"), usableBase, usableBulb));
            }
            manuStatement.close();
            results.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public void closeConnection() {
        try {
            dbConnect.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
