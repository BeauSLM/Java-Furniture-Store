package edu.ucalgary.ensf409;

/**
 * Class Manufacturer. Acts as a placeholder for a Manufacturer
 * for our Program.
 */
public class Manufacturer {
    private String manuID;
    private String name;
    private String phone;
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