package edu.ucalgary.ensf409;

/**
 * Child Class Chair that inherits from Parent Class Furniture
 * and implements Furniture of that fall in the category of
 * Chair.
 */
public class Chair extends Furniture {
    private boolean[] validParts = new boolean[4];

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
        super(id, type, price, manuID, new boolean[]{legs, arms, seat, cushion});
    }
}