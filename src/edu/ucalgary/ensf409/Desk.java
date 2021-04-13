package edu.ucalgary.ensf409;

/**
 * Child class that inherits from class Furniture.
 * Used to implement the Furniture that can be
 * classified as Desks.
 */
public class Desk extends Furniture {

    /**
     * Constructor for Child Class Desk
     *
     * @param id     ID of the Desk
     * @param type   Type of the Desk
     * @param price  Price tag of the Desk
     * @param manuID Manufacturer ID of the Desk
     * @param legs   Boolean that indicates if the Legs are usable
     * @param top    Boolean that indicates if the Top is usable
     * @param drawer Boolean that indicates if the Drawers are usable
     */
    public Desk(String id, String type, int price, String manuID, boolean legs, boolean top, boolean drawer) {
        super(id, type, price, manuID, new boolean[]{legs, top, drawer});
    }
}