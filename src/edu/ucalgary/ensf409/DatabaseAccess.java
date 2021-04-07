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
    public Desk[] retrieveDesks() {
        Desk[] result = null;
        return result;
    }

    public Lamp[] retrieveLamps() {
        Lamp[] result = null;
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
