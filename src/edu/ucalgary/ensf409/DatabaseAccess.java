package edu.ucalgary.ensf409;
import java.sql.*;

/**
 * Class to access the inventory db.
 */
public class DatabaseAccess {
    private Connection dbConnect;
    private ResultSet results;
    String username;
    String password;
    String URL;

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

    public void selectFurniture() {

    }
}
