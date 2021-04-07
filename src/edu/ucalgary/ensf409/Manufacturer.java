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
     * @param manuID    Manufacturer ID.
     * @param name      Manufacturer Name.
     * @param phone     Manufacturer Phone.
     * @param province  Manufacturer Province
     */
    public Manufacturer(String manuID, String name, String phone, String province) {
        this.manuID = manuID;
        this.name = name;
        this.phone = phone;
        this.province = province;
    }

    //Getter functions for class Manufacturer

    public String getManuID() {
        return manuID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getProvince() {
        return province;
    }


    //Setter functions for class Manufacturer


    public void setManuID(String manuID) {
        this.manuID = manuID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}