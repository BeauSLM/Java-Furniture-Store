package edu.ucalgary.ensf409;
import java.util.ArrayList;

public class Lamp extends Furniture {

    /**
     * Constructor of the Child Class Lamp.
     * @param id        ID of the Lamp.
     * @param type      Type of the Lamp.
     * @param price     Price of the Lamp.
     * @param manuID    Manufacturer ID of the Lamp.
     * @param base      Boolean that indicates if the base of the Lamp is usable.
     * @param bulb      Boolean that indicates if the bulb of the Lamp is usable.
     */
    public Lamp(String id, String type, int price, String manuID, boolean base, boolean bulb) {
        super(id, type, price, manuID, new boolean[]{base, bulb});
    }
}