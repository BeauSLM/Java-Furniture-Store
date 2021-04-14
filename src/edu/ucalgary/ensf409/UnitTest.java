package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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
        assertTrue("Manufacturer was not able to be retrieved.", foundItem);
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
        assertTrue("Chair C8138 was not able to be retrieved.", foundItem);
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
        assertTrue("Desk D3820 was not able to be retrieved.", foundItem);
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
        assertTrue("Lamp L053 was not able to be retrieved.", foundItem);
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
        assertTrue("Filing F011 was not able to be retrieved.", foundItem);
    }

    /**
     * Test to see if a chair can be deleted successfully. Adds back which ever chair was deleted back into database.
     */
    @Test
    public void testDatabaseAccessRetrieval_DeleteChair() {
        // testing deleting a chair and searching for it
        String category = "Chair";
        String id = "C3819";
        String type = "Kneeling";
        String legs = "N";
        String arms = "N";
        String seat = "Y";
        String cushion = "N";
        int price = 75;
        String manuID = "005";
            
        boolean foundItem = false;
        testDb.deleteItem(category, id);

        for(Chair item : testDb.getChairList()){
            if(item.getId().equals(id) && item.getType().equals(type)){
                foundItem = true;
            }
        }
        assertFalse("Chair was deleted as it was unable to be found.", foundItem);

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

    /**
     * Test to see if a desk can be deleted successfully. Adds back which ever desk was deleted back into database.
     */
    @Test
    public void testDatabaseAccessRetrieval_DeleteDesk() {
        // testing deleting a chair and searching for it
        String category = "Desk";
        String id = "D3682";
        String type = "Adjustable";
        String legs = "N";
        String top = "N";
        String drawer = "Y";
        int price = 50;
        String manuID = "005";
            
        boolean foundItem = false;
        testDb.deleteItem(category, id);

        for(Desk item : testDb.getDeskList()){
            if(item.getId().equals(id) && item.getType().equals(type)){
                foundItem = true;
            }
        }
        assertFalse("Desk was deleted as it was unable to be found.", foundItem);

        /*
        // attempt to add the chair that was deleted back into database.
        try {
            String query = "INSERT INTO desk (id, type, legs, top, drawer, price, manuid) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement myStmt = testDb.getDbConnect().prepareStatement(query);
                
            myStmt.setString(1, id);
            myStmt.setString(2, type);
            myStmt.setString(3, legs);
            myStmt.setString(4, top);
            myStmt.setString(5, drawer);
            myStmt.setInt(6, price);
            myStmt.setString(7, manuID);

            myStmt.executeUpdate();
                
            myStmt.close();
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        */
    }

    /**
     * Test to see if a lamp can be deleted successfully. Adds back which ever lamp was deleted back into database.
     */
    @Test
    public void testDatabaseAccessRetrieval_DeleteLamp() {
        // testing deleting a chair and searching for it
        String category = "Lamp";
        String id = "L649";
        String type = "Desk";
        String base = "Y";
        String bulb = "N";
        int price = 18;
        String manuID = "004";
            
        boolean foundItem = false;
        testDb.deleteItem(category, id);

        for(Lamp item : testDb.getLampList()){
            if(item.getId().equals(id) && item.getType().equals(type)){
                foundItem = true;
            }
        }
        assertFalse("Lamp was deleted as it was unable to be found.", foundItem);

        /*
        // attempt to add the lamp that was deleted back into database.
        try {
            String query = "INSERT INTO lamp (id, type, base, bulb, price, manuid) VALUES (?,?,?,?,?,?)";
            PreparedStatement myStmt = testDb.getDbConnect().prepareStatement(query);
                
            myStmt.setString(1, id);
            myStmt.setString(2, type);
            myStmt.setString(3, base);
            myStmt.setString(4, bulb);
            myStmt.setInt(5, price);
            myStmt.setString(6, manuID);

            myStmt.executeUpdate();
                
            myStmt.close();
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         */
    }

    /**
     * Test to see if a filing can be deleted successfully. Adds back which ever filing was deleted back into database.
     */
    @Test
    public void testDatabaseAccessRetrieval_DeleteFiling() {
        // testing deleting a chair and searching for it
        String category = "Filing";
        String id = "F015";
        String type = "Large";
        String rails = "Y";
        String drawers = "N";
        String cabinet = "N";
        int price = 75;
        String manuID = "004";
            
        boolean foundItem = false;
        testDb.deleteItem(category, id);

        for(Filing item : testDb.getFilingList()){
            if(item.getId().equals(id) && item.getType().equals(type)){
                foundItem = true;
            }
        }
        assertFalse("Filing was deleted as it was unable to be found.", foundItem);

        /*
        // attempt to add the filing that was deleted back into database.
        try {
            String query = "INSERT INTO filing (id, type, rails, drawers, cabinet, price, manuid) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement myStmt = testDb.getDbConnect().prepareStatement(query);
                
            myStmt.setString(1, id);
            myStmt.setString(2, type);
            myStmt.setString(3, rails);
            myStmt.setString(4, drawers);
            myStmt.setString(5, cabinet);
            myStmt.setInt(6, price);
            myStmt.setString(7, manuID);

            myStmt.executeUpdate();
                
            myStmt.close();
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

         */
    }

    // OptionCalculation Tests
    //_______________________________________________________

    /**
     * Tests if OptionCalculation can find or purchase 1 desk lamp
     */
    @Test
    public void testOptionCalculation_1DeskLamp() {
        OptionCalculation cheapestLamp = new OptionCalculation("Desk", 1);
        cheapestLamp.calculateCheapestPrice(testDb.getLampList());
        assertEquals("Lowest price was incorrectly calculated.", cheapestLamp.getTotalLowestPrice(), 20);
    }

    /**
     * Tests if OptionCalculation can find or purchase 2 desk lamps
     */
    @Test
    public void testOptionCalculation_2DeskLamps() {
        OptionCalculation cheapestLamp = new OptionCalculation("Desk", 2);
        cheapestLamp.calculateCheapestPrice(testDb.getLampList());
        assertEquals("Lowest price was incorrectly calculated.", cheapestLamp.getTotalLowestPrice(), 40);
    }

    @Test //tests an edge case where all possible items are needed
    public void testOptionCalculation_3MediumFilings() {
        OptionCalculation cheapestFiling = new OptionCalculation("Medium", 3);
        cheapestFiling.calculateCheapestPrice(testDb.getFilingList());
        assertEquals(cheapestFiling.getTotalLowestPrice(), 600);
    }

    @Test //tests w/ chair, which requires 4 parts unlike others
    public void testOptionCalculation_1MeshChair(){
        OptionCalculation cheapestChair = new OptionCalculation("Mesh", 1);
        cheapestChair.calculateCheapestPrice(testDb.getChairList());
        assertEquals(cheapestChair.getTotalLowestPrice(), 200);
    }
}