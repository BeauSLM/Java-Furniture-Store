package edu.ucalgary.ensf409;
import java.util.ArrayList;

/**
 * Child class that inherits from class Furniture.
 * Used to implement the Furniture that can be
 * classified as Lamps.
 */
public class Lamp extends Furniture {
    /**
     * boolean that indicates if the lamp base is usable
     */
    private boolean base;

    /**
     * boolean that indicates if the lamp bulb is usable
     */
    private boolean bulb;

    /**
     * Constructor of the Child Class Lamp.
     *
     * @param id     ID of the Lamp.
     * @param type   Type of the Lamp.
     * @param price  Price of the Lamp.
     * @param manuID Manufacturer ID of the Lamp.
     * @param base   Boolean that indicates if the base of the Lamp is usable.
     * @param bulb   Boolean that indicates if the bulb of the Lamp is usable.
     */
    public Lamp(String id, String type, int price, String manuID, boolean base, boolean bulb) {
        super(id, type, price, manuID);
        this.base = base;
        this.bulb = bulb;
    }
    //Setter functions of the Child Class Lamp

    /**
     * Sets base
     *
     * @param base  the status of the base
     */
    public void setBase(boolean base) {
        this.base = base;
    }

    /**
     * Sets bulb
     *
     * @param bulb  the status of the bulb
     */
    public void setBulb(boolean bulb) {
        this.bulb = bulb;
    }

    //Getter functions of the Child Class Lamp

    /**
     * Gets the status of the base
     *
     * @return the status of the lamp
     */
    public boolean getBase() {
        return base;
    }

    /**
     * Gets the status of the bulb
     *
     * @return the status of the bulb
     */
    public boolean getBulb() {
        return bulb;
    }

    /**
     * Gets valid parts
     *
     * @return the valid parts array
     */
    public boolean[] getValidParts() {
        return new boolean[] {base, bulb};
    }
}