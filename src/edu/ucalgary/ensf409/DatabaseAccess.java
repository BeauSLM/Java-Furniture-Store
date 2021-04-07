package edu.ucalgary.ensf409;
import java.sql.*;

/**
 * Class used to access the inventory database.
 */
public class DatabaseAccess {
    private Connection dbConnect;
    private ResultSet results;

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

    public Manufacturer[] retrieveManufacturers() {
        Manufacturer[] result = null;
        String Query = "SELECT * FROM MANUFACTURER";
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery(Query);
            while(results.next()) {

            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Chair[] retrieveChairs() {
        Chair[] result = null;
        return result;
    }
    public Desk[] retrieveDesks() {
        Desk[] result = null;
        return result;
    }

    public Lamp[] retrieveLamps() {
        Lamp[] result = null;
        return result;
    }
}
