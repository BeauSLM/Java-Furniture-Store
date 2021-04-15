package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * <h1>UnitTests</h1>
 * 
 * Class for unit tests to test program functionality.
 * 
 * @author  Beau McCartney, Apostolos Scondrianis, Quentin Jennings, Jacob Lansang
 * @version 2.4
 */
 
public class UnitTest {

    private DatabaseAccess testDb;

    //change these to fit your system before running tests
    private static String USERNAME = "ensf409";
    private static String PASSWORD = "ensf409";

    // RIGHT NOW THIS IS FOR BEAU'S MACHINE CHANGE INVENTORY TO LOWERCASE IF YOU NEED TO
    private static String URL = "jdbc:mysql://localhost:3306/inventory";

    /**
     * Instantiates a new Unit test.
     */
    public UnitTest() {
    }

    //Pre-test setup
    //_____________________________________________________________________________

    @Before //resets the database before each connection
    public void resetDatabase() {
        try {
            testDb = new DatabaseAccess(USERNAME, PASSWORD, URL);
            String query = "DROP DATABASE IF EXISTS INVENTORY;";
            PreparedStatement myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "CREATE DATABASE INVENTORY;";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "USE INVENTORY";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "DROP TABLE IF EXISTS MANUFACTURER;";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "CREATE TABLE MANUFACTURER ( ManuID char(3) not null, Name varchar(25),"+
                    "Phone	char(12), Province char(2), primary key (ManuID));";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "INSERT INTO MANUFACTURER (ManuID, Name, Phone, Province)"+
                    "VALUES"+
                    "('001',	'Academic Desks',	'236-145-2542',	'BC'),"+
                    "('002',	'Office Furnishings',	'587-890-4387',	'AB'),"+
                    "('003',	'Chairs R Us',	'705-667-9481',	'ON'),"+
                    "('004',	'Furniture Goods',	'306-512-5508',	'SK'),"+
                    "('005',	'Fine Office Supplies',	'403-980-9876',	'AB');";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "DROP TABLE IF EXISTS CHAIR;";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "CREATE TABLE CHAIR ("+
                    "ID				char(5)	not null,"+
                    "Type			varchar(25),"+
                    "Legs			char(1),"+
                    "Arms			char(1),"+
                    "Seat			char(1),"+
                    "Cushion			char(1),"+
                    "Price			integer,"+
                    "ManuID			char(3),"+
                    "primary key (ID),"+
                    "foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE"+
                    ");";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "INSERT INTO CHAIR (ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID)"+
                    "VALUES"+
                    "('C1320',	'Kneeling',	'Y',	'N',	'N',	'N',	50,	'002'),"+
                    "('C3405',	'Task',	'Y',	'Y',	'N',	'N',	100,	'003'),"+
                    "('C9890',	'Mesh',	'N',	'Y',	'N',	'Y',	50,	'003'),"+
                    "('C7268',	'Executive',	'N',	'N',	'Y',	'N',	75,	'004'),"+
                    "('C0942',	'Mesh',	'Y',	'N',	'Y',	'Y',	175,	'005'),"+
                    "('C4839',	'Ergonomic',	'N',	'N',	'N',	'Y',	50,	'002'),"+
                    "('C2483',	'Executive',	'Y',	'Y',	'N',	'N',	175,	'002'),"+
                    "('C5789',	'Ergonomic',	'Y',	'N',	'N',	'Y',	125,	'003'),"+
                    "('C3819',	'Kneeling',	'N',	'N',	'Y',	'N',	75,	'005'),"+
                    "('C5784',	'Executive',	'Y',	'N',	'N',	'Y',	150,	'004'),"+
                    "('C6748',	'Mesh',	'Y',	'N',	'N',	'N',	75,	'003'),"+
                    "('C0914',	'Task',	'N',	'N',	'Y',	'Y',	50,	'002'),"+
                    "('C1148',	'Task',	'Y',	'N',	'Y',	'Y',	125,	'003'),"+
                    "('C5409',	'Ergonomic',	'Y',	'Y',	'Y',	'N',	200,	'003'),"+
                    "('C8138',	'Mesh',	'N',	'N',	'Y',	'N',	75,	'005');";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "DROP TABLE IF EXISTS DESK;";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "CREATE TABLE DESK ("+
                    "ID				char(5)	not null,"+
                    "Type			varchar(25),"+
                    "Legs			char(1),"+
                    "Top			char(1),"+
                    "Drawer			char(1),"+
                    "Price			integer,"+
                    "ManuID			char(3),"+
                    "primary key (ID),"+
                    "foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE"+
                    ");";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "INSERT INTO DESK (ID, Type, Legs, Top, Drawer, Price, ManuID)"+
                    "VALUES"+
                    "('D3820',	'Standing',	'Y',	'N',	'N',	150,	'001'),"+
                    "('D4475',	'Adjustable',	'N',	'Y',	'Y',	200,	'002'),"+
                    "('D0890',	'Traditional',	'N',	'N',	'Y',	25,	'002'),"+
                    "('D2341',	'Standing',	'N',	'Y',	'N',	100,	'001'),"+
                    "('D9387',	'Standing',	'Y',	'Y',	'N',	250,	'004'),"+
                    "('D7373',	'Adjustable',	'Y',	'Y',	'N',	350,	'005'),"+
                    "('D2746',	'Adjustable',	'Y',	'N',	'Y',	250,	'004'),"+
                    "('D9352',	'Traditional',	'Y',	'N',	'Y',	75,	'002'),"+
                    "('D4231',	'Traditional',	'N',	'Y',	'Y',	50,	'005'),"+
                    "('D8675',	'Traditional',	'Y',	'Y',	'N',	75,	'001'),"+
                    "('D1927',	'Standing',	'Y',	'N',	'Y',	200,	'005'),"+
                    "('D1030',	'Adjustable',	'N',	'Y',	'N',	150,	'002'),"+
                    "('D4438',	'Standing',	'N',	'Y',	'Y',	150,	'004'),"+
                    "('D5437',	'Adjustable',	'Y',	'N',	'N',	200,	'001'),"+
                    "('D3682',	'Adjustable',	'N',	'N',	'Y',	50,	'005');";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "DROP TABLE IF EXISTS LAMP;";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "CREATE TABLE LAMP ("+
                    "ID				char(4)	not null,"+
                    "Type			varchar(25),"+
                    "Base			char(1),"+
                    "Bulb			char(1),"+
                    "Price			integer,"+
                    "ManuID			char(3),"+
                    "primary key (ID),"+
                    "foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE"+
                    ");";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "INSERT INTO LAMP (ID, Type, Base, Bulb, Price, ManuID)"+
                    "VALUES"+
                    "('L132',	'Desk',	'Y',	'N',	18,	'005'),"+
                    "('L980',	'Study',	'N',	'Y',	2,	'004'),"+
                    "('L487',	'Swing Arm',	'Y',	'N',	27,	'002'),"+
                    "('L564',	'Desk',	'Y',	'Y',	20,	'004'),"+
                    "('L342',	'Desk',	'N',	'Y',	2,	'002'),"+
                    "('L982',	'Study',	'Y',	'N',	8,	'002'),"+
                    "('L879',	'Swing Arm',	'N',	'Y',	3,	'005'),"+
                    "('L208',	'Desk',	'N',	'Y',	2,	'005'),"+
                    "('L223',	'Study',	'N',	'Y',	2,	'005'),"+
                    "('L928',	'Study',	'Y',	'Y',	10,	'002'),"+
                    "('L013',	'Desk',	'Y',	'N',	18,	'004'),"+
                    "('L053',	'Swing Arm',	'Y',	'N',	27,	'002'),"+
                    "('L112',	'Desk',	'Y',	'N',	18,	'005'),"+
                    "('L649',	'Desk',	'Y',	'N',	18,	'004'),"+
                    "('L096',	'Swing Arm',	'N',	'Y',	3,	'002');";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "DROP TABLE IF EXISTS FILING;";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "CREATE TABLE FILING ("+
                    "ID				char(4)	not null,"+
                    "Type			varchar(25),"+
                    "Rails			char(1),"+
                    "Drawers			char(1),"+
                    "Cabinet			char(1),"+
                    "Price			integer,"+
                    "ManuID			char(3),"+
                    "primary key (ID),"+
                    "foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE"+
                    ");";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

            query = "INSERT INTO FILING (ID, Type, Rails, Drawers, Cabinet, Price, ManuID)"+
                    "VALUES"+
                    "('F001',	'Small',	'Y',	'Y',	'N',	50,	'005'),"+
                    "('F002',	'Medium',	'N',	'N',	'Y',	100,	'004'),"+
                    "('F003',	'Large',	'N',	'N',	'Y',	150,	'002'),"+
                    "('F004',	'Small',	'N',	'Y',	'Y',	75,	'004'),"+
                    "('F005',	'Small',	'Y',	'N',	'Y',	75,	'005'),"+
                    "('F006',	'Small',	'Y',	'Y',	'N',	50,	'005'),"+
                    "('F007',	'Medium',	'N',	'Y',	'Y',	150,	'002'),"+
                    "('F008',	'Medium',	'Y',	'N',	'N',	50,	'005'),"+
                    "('F009',	'Medium',	'Y',	'Y',	'N',	100,	'004'),"+
                    "('F010',	'Large',	'Y',	'N',	'Y',	225,	'002'),"+
                    "('F011',	'Large',	'N',	'Y',	'Y',	225,	'005'),"+
                    "('F012',	'Large',	'N',	'Y',	'N',	75,	'005'),"+
                    "('F013',	'Small',	'N',	'N',	'Y',	50,	'002'),"+
                    "('F014',	'Medium',	'Y',	'Y',	'Y',	200,	'002'),"+
                    "('F015',	'Large',	'Y',	'N',	'N',	75,	'004');";
            myStatement = testDb.getDbConnect().prepareStatement(query);
            myStatement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
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

    @Test //test with desk traditional, 1 item
    public void testOptionCalculation_1DeskTraditional() {
        OptionCalculation cheapestDesk = new OptionCalculation("Traditional", 1);
        cheapestDesk.calculateCheapestPrice(testDb.getDeskList());
        assertEquals(cheapestDesk.getTotalLowestPrice(), 100);
    }

    @Test //test with desk traditional, 1 item, check IDs this time.
    public void testOptionCalculation_1DeskTraditionalCheckItems() {
        StringBuilder testString = new StringBuilder("");
        OptionCalculation cheapestDesk = new OptionCalculation("Traditional", 1);
        cheapestDesk.calculateCheapestPrice(testDb.getDeskList());
        for(int i = 0; i < cheapestDesk.getLowestPriceItems().size(); i++) {
            testString.append(cheapestDesk.getLowestPriceIDs().get(i));
        }

        assertEquals(new String(testString), "D0890D8675");
    }
}