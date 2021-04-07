package edu.ucalgary.ensf409;

public class Lamp extends Furniture {
    private boolean base;
    private boolean bulb;

    /**
     * Constructor of the Child Class Lamp.
     * @param ID        ID of the Lamp.
     * @param type      Type of the Lamp.
     * @param price     Price of the Lamp.
     * @param manuID    Manufacturer ID of the Lamp.
     * @param base      Boolean that indicates if the base of the Lamp is usable.
     * @param bulb      Boolean that indicates if the bulb of the Lamp is usable.
     */
    public Lamp(String ID, String type, int price, String manuID, boolean base, boolean bulb) {
        super(ID, type, price, manuID);
        this.base = base;
        this.bulb = bulb;
    }

    //Getter functions of the Child Class Lamp

    public boolean getBase() {
        return base;
    }

    public boolean getBulb() {
        return bulb;
    }

    //Setter functions of the Child Class Lamp

    public void setBase(boolean base) {
        this.base = base;
    }

    public void setBulb(boolean bulb) {
        this.bulb = bulb;
    }
}