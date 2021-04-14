package edu.ucalgary.ensf409;

/**
 * Child Class Chair that inherits from Parent Class Furniture
 * and implements Furniture of that fall in the category of
 * Chair.
 */
public class Chair extends Furniture {
    /**
     * boolean that indicates if the chair legs are usable
     */
    private boolean legs;

    /**
     * boolean that indicates if the chair arms are usable
     */
    private boolean arms;

    /**
     * boolean that indicates if the chair seat is usable
     */
    private boolean seat;

    /**
     * boolean that indicates if the chair cushion is usable
     */
    private boolean cushion;

    /**
     * Constructor for the Child Class Chair.
     *
     * @param id      ID of the Chair.
     * @param type    Type of the Chair.
     * @param price   Price tag of the Chair.
     * @param manuID  Manufacturer ID of the Chair.
     * @param legs    Boolean that indicates if the legs of the Chair are usable.
     * @param arms    Boolean that indicates if the arms of the Chair are usable.
     * @param seat    Boolean that indicates if the seat of the Chair is usable.
     * @param cushion Boolean that indicates if the cushion of the Chair is usable.
     */
    public Chair(String id, String type, int price, String manuID, boolean legs, boolean arms,
                 boolean seat, boolean cushion) {

        super(id, type, price, manuID);
        this.legs = legs;
        this.arms = arms;
        this.seat = seat;
        this.cushion = cushion;
    }
    //Setter functions for the Child Class Chair

    /**
     * Sets legs
     * @param legs      If legs are usable or not
     */
    public void setLegs(boolean legs) {
        this.legs = legs;
    }

    /**
     * Sets arms
     * @param arms      If arms are usable or not
     */
    public void setArms(boolean arms) {
        this.arms = arms;
    }

    /**
     * Sets seat
     * @param seat      If seat is usable or not
     */
    public void setSeat(boolean seat) {
        this.seat = seat;
    }

    /**
     * Sets cushion
     * @param cushion   If cushion is usable or not
     */
    public void setCushion(boolean cushion) {
        this.cushion = cushion;
    }
    //Getter functions for the Child Class Chair

    /**
     * Gets the status of the legs if they are usable or unusable
     *
     * @return legs the status of the legs
     */
    public boolean getLegs() {
        return legs;
    }

    /**
     * Gets the status of the arms if they are usable or unusable
     *
     * @return arms the status of the arms
     */
    public boolean getArms() {
        return arms;
    }

    /**
     * Gets the status of the seat if it is usable or unusable
     *
     * @return seat the status of the seat
     */
    public boolean getSeat() {
        return seat;
    }

    /**
     * Gets the status of the cushion if it is usable or unusable
     *
     * @return cushion the status of the cushion
     */
    public boolean getCushion() {
        return cushion;
    }

    /**
     * Gets the valid parts available from the chairs we have
     * @return  arms, seat, cushion the statuses of the chair arms, seats, and cushions.
     */
    public boolean[] getValidParts() {
        return new boolean[] {legs, arms, seat, cushion};
    }
}