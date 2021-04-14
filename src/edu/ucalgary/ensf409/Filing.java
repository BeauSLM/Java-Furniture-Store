package edu.ucalgary.ensf409;
import java.util.ArrayList;

/**
 * Child Class Filing that is used as a container for furniture of
 * the category Filing Cabinets.
 */
public class Filing extends Furniture {
    private boolean rails;
    private boolean drawers;
    private boolean cabinet;

    /**
     * Constructor for the Child Class Filing
     *
     * @param id      ID of the Filing Cabinet
     * @param type    Type of the Filing Cabinet
     * @param price   Price of the Filing Cabinet
     * @param manuID  Manufacturer ID of the Filing Cabinet
     * @param rails   Boolean that is used to indicate if rails are usable
     * @param drawers Boolean that is used to indicate if the drawers are usable
     * @param cabinet Boolean that is used to indicate if the cabinet is usable
     */
    public Filing(String id, String type, int price, String manuID, boolean rails, boolean drawers,
                 boolean cabinet) {
        super(id, type, price, manuID);
        this.rails = rails;
        this.drawers = drawers;
        this.cabinet = cabinet;
    }
    //Getter functions for the Filing Cabinets

    /**
     * Gets the status of the rails if they are usable or not
     *
     * @return the status of the rails
     */
    public boolean getRails() {
        return rails;
    }

    /**
     * Gets the status of the drawers if they are usable or not
     *
     * @return the status of the drawers
     */
    public boolean getDrawers() {
        return drawers;
    }

    /**
     * Gets the status of the cabinet if it is usable or not
     *
     * @return the status of the cabinet
     */
    public boolean getCabinet() {
        return cabinet;
    }

    /**
     * Gets valid parts
     *
     * @return the valid parts array of Filing
     */
    public boolean[] getValidParts() {
        return new boolean[] {rails, drawers, cabinet};
    }
}
