package edu.ucalgary.ensf409;

/**
 * Class Furniture is used as a parent class for the classes
 * Chair, Database, Desk, Lamp, Filing. There are common members
 * that can be implemented. String ID, String type, int price and
 * String manuID.
 */
public class Furniture {
    private String ID;
    private String type;
    private int price;
    private String manuID;

    /**
     * Constructor for the Parent Class Furniture.
     * @param ID        The ID of the furniture.
     * @param type      The type of the furniture
     * @param price     The price of the furniture
     * @param manuID    The Manufacturer ID
     */
    public Furniture(String ID, String type, int price, String manuID) {
        this.ID = ID;
        this.type = type;
        this.price = price;
        this.manuID = manuID;
    }

    //All the getter functions for the parent class Furniture

    public String getID() {
        return this.ID;
    }

    public String getType() {
        return this.type;
    }

    public int getPrice() {
        return this.price;
    }

    public String getManuID() {
        return this.manuID;
    }

    //All the setter functions for the parent class Furniture

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setManuID(String manuID) {
        this.manuID = manuID;
    }
}