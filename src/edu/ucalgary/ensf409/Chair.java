package edu.ucalgary.ensf409;

/**
 * Child Class Chair that inherits from Parent Class Furniture
 * and implements Furniture of that fall in the category of
 * Chair.
 */
public class Chair extends Furniture {
    private boolean legs;
    private boolean arms;
    private boolean seat;
    private boolean cushion;

    /**
     * Constructor for the Child Class Chair.
     * @param ID        ID of the Chair.
     * @param type      Type of the Chair.
     * @param price     Price tag of the Chair.
     * @param manuID    Manufacturer ID of the Chair.
     * @param legs      Boolean that indicates if the legs of the Chair are usable.
     * @param arms      Boolean that indicates if the arms of the Chair are usable.
     * @param seat      Boolean that indicates if the seat of the Chair is usable.
     * @param cushion   Boolean that indicates if the cushion of the Chair is usable.
     */
    public Chair(String ID, String type, int price, String manuID, boolean legs, boolean arms,
                 boolean seat, boolean cushion) {

        super(ID, type, price, manuID);
        this.legs = legs;
        this.arms = arms;
        this.seat = seat;
        this.cushion = cushion;
    }

    //Setter functions for the Child Class Chair

    public void setLegs(boolean legs) {
        this.legs = legs;
    }

    public void setArms(boolean arms) {
        this.arms = arms;
    }

    public void setSeat(boolean seat) {
        this.seat = seat;
    }

    public void setCushion(boolean cushion) {
        this.cushion = cushion;
    }


    //Getter functions for the Child Class Chair

    public boolean getLegs() {
        return legs;
    }

    public boolean getArms() {
        return arms;
    }

    public boolean getSeat() {
        return seat;
    }

    public boolean getCushion() {
        return cushion;
    }
}