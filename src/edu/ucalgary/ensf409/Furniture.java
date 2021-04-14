package edu.ucalgary.ensf409;

/**
 * <h1>Furniture</h1>
 * 
 * Class Furniture is used as a parent class for the classes
 * Chair, Database, Desk, Lamp, Filing. There are common members
 * that can be implemented. String id, String type, int price and
 * String manuID.
 *
 * @author  Beau McCartney, Apostolos Scondrianis, Quentin Jennings, Jacob Lansang
 * @version 2.4
 */

public abstract class Furniture {
    /**
     * String that identifies the ID of the furniture
     */
    private String id;

    /**
     * String that identifies the type of the furniture
     */
    private String type;

    /**
     * int that identifies the price of the furniture
     */
    private int price;

    /**
     * String that identifies the manufacturers ID of the furniture
     */
    private String manuID;

    /**
     * Constructor for the Parent Class Furniture.
     *
     * @param id     The ID of the furniture.
     * @param type   The type of the furniture
     * @param price  The price of the furniture
     * @param manuID The Manufacturer ID
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
     *
     * @return the ID of the furniture
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the type
     *
     * @return the type of the furniture
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets price
     *
     * @return the price of the furniture
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Gets manufacturer ID
     *
     * @return the ID of the manufacturer
     */
    public String getManuID() {
        return this.manuID;
    }

    /**
     * Sets type
     *
     * @param type the type of furniture
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets price
     *
     * @param price the price of the furniture
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Sets the manufacturers ID
     *
     * @param manuID the ID of the manufacturer
     */
    public void setManuID(String manuID) {
        this.manuID = manuID;
    }

    /**
     * Gets valid parts
     *
     * @return the valid parts array
     */
    public abstract boolean[] getValidParts();
}

