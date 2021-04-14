package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for unit tests.
 */
public class UnitTest {

    static DatabaseAccess testDb;

    //change these to fit your system before running tests
    static String username = "ensf409";
    static String password = "ensf409";
    static String url = "jdbc:mysql://localhost:3306/INVENTORY";

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
        for(Desk item : testDb.getDeskList()){
            if(item.getId().equals("D3820") && item.getLegs() && !item.getDrawer() && !item.getTop()
                && item.getType().equals("Standing") && item.getPrice() == 150 && item.getManuID().equals(001)) {
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

    // Tests
    //_______________________________________________________
}