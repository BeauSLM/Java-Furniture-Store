package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Class for unit tests.
 */
public class UnitTest {

    private static DatabaseAccess testDb;

    //change these to fit your system before running tests
    private static String username = "ensf409";
    private static String password = "ensf409";

    // RIGHT NOW THIS IS FOR BEAU'S MACHINE CHANGE INVENTORY TO LOWERCASE IF YOU NEED TO
    private static String url = "jdbc:mysql://localhost:3306/INVENTORY";

    /**
     * Instantiates a new Unit test.
     */
    public UnitTest() {
    }

    //Pre-test setup
    //_____________________________________________________________________________

    @BeforeClass
    public static void DBSetup(){
        //user input from command line maybe?
        //
        testDb = new DatabaseAccess(username, password, url);
    }

    //DatabaseAccess Tests
    //________________________________________________________________

    @Test
    public void testDatabaseAccessRetrieval_Manu() {
        //searching for Chairs R Us
        boolean foundItem = false;
        for(Manufacturer item : testDb.getManuList()){
            if(item.getName().equals("Chairs R Us") && item.getManuID().equals("003")
                    && item.getPhone().equals("705-667-9481") && item.getProvince().equals("ON")) {
                foundItem = true;
            }
        }
        assertTrue(foundItem);
    }
    @Test
    public void testDatabaseAccessRetrieval_Chair() {
        //searching for C8138
        boolean foundItem = false;
        for(Chair item : testDb.getChairList()){
            if(item.getId().equals("C8138") && item.getType().equals("Mesh") && !item.getLegs() && !item.getArms()
                    && item.getSeat() && !item.getCushion() && item.getPrice() == 75 && item.getManuID().equals("005")){
                foundItem = true;
            }
        }
        assertTrue(foundItem);
    }
    @Test
    public void testDatabaseAccessRetrieval_Desk() {
        //searching for D3820
        boolean foundItem = false;
        for(Desk item : testDb.getDeskList()) {
            if(item.getId().equals("D3820") && item.getLegs() && !item.getDrawer() && !item.getTop()
                && item.getType().equals("Standing") && item.getPrice() == 150 && item.getManuID().equals("001")) {
                foundItem = true;
            }
        }
        assertTrue(foundItem);
    }
    @Test
    public void testDatabaseAccessRetrieval_Lamp() {
        //searching for L053
        boolean foundItem = false;
        for(Lamp item : testDb.getLampList()){
            if(item.getId().equals("L053") && item.getType().equals("Swing Arm") && item.getBase()
                    && !item.getBulb() && item.getPrice() == 27 && item.getManuID().equals("002")){
                foundItem = true;
            }
        }
        assertTrue(foundItem);
    }
    @Test
    public void testDatabaseAccessRetrieval_Filing() {
        //searching for F011
        boolean foundItem = false;
        for(Filing item : testDb.getFilingList()){
            if(item.getId().equals("F011") && item.getType().equals("Large") && !item.getRails() && item.getDrawers()
                    && item.getCabinet() && item.getPrice() == 225 && item.getManuID().equals("005")){
                foundItem = true;
            }
        }
        assertTrue(foundItem);
    }

    @Test
    public void testDatabaseAccessRetrieval_DeleteChair() {
        // testing deleting a chair and searching for it
        String id = "C3819";
        String type = "Kneeling";
        String legs = "N";
        String arms = "N";
        String seat = "Y";
        String cushion = "N";
        int price = 75;
        String manuID = "005";
            
        boolean foundItem = false;
        testDb.deleteItem(type, id);

        for(Chair item : testDb.getChairList()){
            if(item.getId().equals(id) && item.getType().equals(type)){
                foundItem = true;
            }
        }
        assertFalse("Chair was NOT deleted", foundItem);

        // attempt to add the chair that was deleted back into database.
        try {
            String query = "INSERT INTO chair (id, type, legs, arms, seat, cushion, price, manuid) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement myStmt = testDb.getDbConnect().prepareStatement(query);
                
            myStmt.setString(1, id);
            myStmt.setString(2, type);
            myStmt.setString(3, legs);
            myStmt.setString(4, arms);
            myStmt.setString(5, seat);
            myStmt.setString(6, cushion);
            myStmt.setInt(7, price);
            myStmt.setString(8, manuID);

            myStmt.executeUpdate();
                
            myStmt.close();
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    // OptionCalculation Tests
    //_______________________________________________________
    @Test
    public void testOptionCalculation_1DeskLamp() {
        OptionCalculation cheapestLamp = new OptionCalculation("Desk", 1);
        cheapestLamp.calculateCheapestPrice(testDb.getLampList());
        assertEquals(cheapestLamp.getTotalLowestPrice(), 20);
        for(Object id : cheapestLamp.getLowestPriceIDs()){
            System.out.println((String)id);
        }
    }

    @Test
    public void testOptionCalculation_2DeskLamps() {
        OptionCalculation cheapestLamp = new OptionCalculation("Desk", 2);
        cheapestLamp.calculateCheapestPrice(testDb.getLampList());
        assertEquals(cheapestLamp.getTotalLowestPrice(), 40);
        for(Object id : cheapestLamp.getLowestPriceIDs()){
            System.out.println((String)id);
        }
    }
}