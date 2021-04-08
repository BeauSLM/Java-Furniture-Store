package edu.ucalgary.ensf409;

/**
 * Child class that inherits from class Furniture.
 * Used to implement the Furniture that can be
 * classified as Desks.
 */
public class Desk extends Furniture {
    private boolean legs;
    private boolean top;
    private boolean drawer;

    /**
     * Constructor for Child Class Desk
     * @param id        ID of the Desk
     * @param type      Type of the Desk
     * @param price     Price tag of the Desk
     * @param manuID    Manufacturer ID of the Desk
     * @param legs      Boolean that indicates if the Legs are usable
     * @param top       Boolean that indicates if the Top is usable
     * @param drawer    Boolean that indicates if the Drawers are usable
     */
    public Desk(String id, String type, int price, String manuID, boolean legs, boolean top, boolean drawer) {
        super(id, type, price, manuID);
        this.legs = legs;
        this.top = top;
        this.drawer = drawer;
    }

    //Desk Class getter functions

    /**
     * Gets the status of the legs if they are usable or unusable
     * @return  the status of the legs
     */
    public boolean getLegs() {
        return legs;
    }

    /**
     * Gets the status of the top if it is usable or unusable
     * @return  the status of the top
     */
    public boolean getTop() {
        return top;
    }

    /**
     * Gets the status of the drawer if it is usable or unusable
     * @return  the status of the drawer
     */
    public boolean getDrawer() {
        return drawer;
    }

    //Desk class setter Function

    /**
     * Sets legs
     * @param legs  If the legs are usable or not
     */
    public void setLegs(boolean legs) {
        this.legs = legs;
    }

    /**
     * Sets top
     * @param top   If the top is usable or not
     */
    public void setTop(boolean top) {
        this.top = top;
    }

    /**
     * Sets drawer
     * @param drawer    If the drawer is usable or not
     */
    public void setDrawer(boolean drawer) {
        this.drawer = drawer;
    }
}