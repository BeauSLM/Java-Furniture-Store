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
    public boolean getLegs() {
        return legs;
    }

    public boolean getTop() {
        return top;
    }

    public boolean getDrawer() {
        return drawer;
    }

    //Desk class setter Function

    public void setLegs(boolean legs) {
        this.legs = legs;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public void setDrawer(boolean drawer) {
        this.drawer = drawer;
    }
}