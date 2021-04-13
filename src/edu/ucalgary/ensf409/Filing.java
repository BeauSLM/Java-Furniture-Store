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
     * @param id        ID of the Filing Cabinet
     * @param type      Type of the Filing Cabinet
     * @param price     Price of the Filing Cabinet
     * @param manuID    Manufacturer ID of the Filing Cabinet
     * @param rails     Boolean that is used to indicate if rails are usable
     * @param drawers   Boolean that is used to indicate if the drawers are usable
     * @param cabinet   Boolean that is used to indicate if the cabinet is usable
     */
    public Filing(String id, String type, int price, String manuID, boolean rails, boolean drawers,
                 boolean cabinet) {
        super(id, type, price, manuID, new boolean[]{rails, drawers, cabinet});
        this.rails = rails;
        this.drawers = drawers;
        this.cabinet = cabinet;
    }
}
