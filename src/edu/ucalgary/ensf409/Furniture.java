package edu.ucalgary.ensf409;

/**
 * Class Furniture is used as a parent class for the classes
 * Chair, Database, Desk, Lamp, Filing. There are common members
 * that can be implemented. String id, String type, int price and
 * String manuID.
 */
public class Furniture {
    private String id;
    private String type;
    private int price;
    private String manuID;

    /**
     * Constructor for the Parent Class Furniture.
     * @param id        The ID of the furniture.
     * @param type      The type of the furniture
     * @param price     The price of the furniture
     * @param manuID    The Manufacturer ID
     */
    public Furniture(String id, String type, int price, String manuID) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.manuID = manuID;
    }

    //All the getter functions for the parent class Furniture

    /**
     * Gets the ID
     * @return  the ID of the furniture
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the type
     * @return  the type of the furniture
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets price
     * @return  the price of the furniture
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Gets manufacturer ID
     * @return  the ID of the manufacturer
     */
    public String getManuID() {
        return this.manuID;
    }

    //All the setter functions for the parent class Furniture

    /**
     * Sets ID
     * @param ID    the ID of the furniture
     */
    public void setId(String ID) {
        this.id = id;
    }

    /**
     * Sets type
     * @param type  the type of furniture
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets price
     * @param price the price of the furniture
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Sets the manufacturers ID
     * @param manuID    the ID of the manufacturer
     */
    public void setManuID(String manuID) {
        this.manuID = manuID;
    }
}