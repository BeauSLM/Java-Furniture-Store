package edu.ucalgary.ensf409;

/**
 * <h1>Manufacturer</h1>
 * This class is used as the placeholder for the manufacturer class.
 *
 * @author  Beau McCartney, Apostolos Scondrianis, Quentin Jennings, Jacob Lansang
 * @version 2.4
 */

public class Manufacturer {
    /**
     * The manufacturers ID
     */
    private String manuID;

    /**
     * The name of the manufacturer
     */
    private String name;

    /**
     * The phone number of the manufacturer
     */
    private String phone;

    /**
     * The province where the manufacturer is located
     */
    private String province;

    /**
     * Constructor for Manufacturer Class.
     *
     * @param manuID   Manufacturer ID.
     * @param name     Manufacturer Name.
     * @param phone    Manufacturer Phone.
     * @param province Manufacturer Province
     */
    public Manufacturer(String manuID, String name, String phone, String province) {
        this.manuID = manuID;
        this.name = name;
        this.phone = phone;
        this.province = province;
    }

    //Getter functions for class Manufacturer

    /**
     * Gets the manufacturers ID
     *
     * @return the manufacturers ID
     */
    public String getManuID() {
        return manuID;
    }

    /**
     * Gets the name of the manufacturer
     *
     * @return the name of the manufacturer
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the phone number of the manufacturer
     *
     * @return the phone number of the manufacturer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the province of the manufacturer
     *
     * @return the province of the manufacturer
     */
    public String getProvince() {
        return province;
    }


    //Setter functions for class Manufacturer

    /**
     * Sets manuID
     *
     * @param manuID the ID of the manufacturer
     */
    public void setManuID(String manuID) {
        this.manuID = manuID;
    }

    /**
     * Sets name
     *
     * @param name the name of the manufacturer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets phone
     *
     * @param phone the phone number of the manufacturer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets province
     *
     * @param province the province of the manufacturer
     */
    public void setProvince(String province) {
        this.province = province;
    }
}