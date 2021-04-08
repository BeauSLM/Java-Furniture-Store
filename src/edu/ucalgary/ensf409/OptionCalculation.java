package edu.ucalgary.ensf409;
import java.util.*;

public class OptionCalculation {

    private final String category;
    private final DatabaseAccess database;
    private final int numOfItems;

    private int totalLowestPrice = 0; //-1 means there isn't enough stock to build the order
    private ArrayList<String> lowestPriceIDs = new ArrayList<>(); //used in the case of a successful order
    private ArrayList<String> manufacturerNames = new ArrayList<>(); //used in the case of an unsuccessful order

	public OptionCalculation(String category, int numOfItems, DatabaseAccess database) {
        this.category = category;
        this.numOfItems = numOfItems;
        this.database = database;
    }

    public void calculateCheapestPrice(){
	    switch(category) {
            case "CHAIR":
                if(calcChair()) {
                    //remove ordered chairs from data base
                    //produceOrderForm? OrderForm class?
                }
                else {
                    //suggestManufacturers?
                }
                break;
            case "DESK":
                calcDesk();
                break;
            case "LAMP":
                calcLamp();
                break;
            case "FILING":
                calcFiling();
                break;
        }

        //calculate cheapest option - brute force it using all possible combinations
        //if fails to calculate, then there isn't the inventory

        //remove said items from the database
        //print order
    }

    //i did not take an algorithm class please forgive me - TT
    public boolean calcChair(){
        ArrayList<Chair> validChairs = new ArrayList<>();
        for(Chair temp : database.getChairList()) {
            if(temp.getType().equals(category)) {
                validChairs.add(temp);
            }
        }
        if(validChairs.isEmpty()){
            return false;
        }
        //SPAGHETTI CODE TIME I AM SO SORRY
        else {
            for(int itemNum = 0; itemNum < numOfItems; itemNum++) {
                int lowestPrice = -1;

                //checks sets of 1
                for (Chair chair : validChairs) {
                    //if all the pieces are present on the chair
                    if (chair.getLegs() && chair.getArms() && chair.getSeat() && chair.getCushion()) {
                        //if the price of the chair is the lowest so far (or if it's the first combo)
                        if (chair.getPrice() < lowestPrice || lowestPrice == -1) {
                            lowestPrice = chair.getPrice();
                            lowestPriceIDs.clear();
                            lowestPriceIDs.add(chair.getId());
                        }
                    }
                }

                //checks sets of 2
                for (Chair chair1 : validChairs) {
                    for (Chair chair2 : validChairs) {
                        //if the chairs are unique (only one of each chair)
                        if (chair1 != chair2) {
                            //if all the pieces are present between the two
                            if ((chair1.getLegs() || chair2.getLegs())
                                    && (chair1.getArms() || chair2.getArms())
                                    && (chair1.getSeat() || chair2.getSeat())
                                    && (chair1.getCushion() || chair2.getCushion())) {

                                int totalPrice = chair1.getPrice() + chair2.getPrice();
                                //if the price is lower than the lowest price (or no valid combos have come up yet)
                                if (totalPrice < lowestPrice || lowestPrice == -1) {
                                    lowestPrice = totalPrice;
                                    lowestPriceIDs.clear();
                                    lowestPriceIDs.add(chair1.getId());
                                    lowestPriceIDs.add(chair2.getId());
                                }
                            }
                        }
                    }
                }

                //checks sets of 3
                for (Chair chair1 : validChairs) {
                    for (Chair chair2 : validChairs) {
                        for (Chair chair3 : validChairs) {
                            //if the chairs are unique (only one of each chair)
                            if (chair1 != chair2 && chair2 != chair3 && chair1 != chair3) {
                                //if all the pieces are present between the three
                                if ((chair1.getLegs() || chair2.getLegs() || chair3.getLegs())
                                        && (chair1.getArms() || chair2.getArms() || chair3.getArms())
                                        && (chair1.getSeat() || chair2.getSeat() || chair3.getSeat())
                                        && (chair1.getCushion() || chair2.getCushion() || chair3.getCushion())) {
                                    int totalPrice = chair1.getPrice() + chair2.getPrice() + chair3.getPrice();
                                    //if the price is lower than the lowest price (or no valid combos have come up yet)
                                    if (totalPrice < lowestPrice || lowestPrice == -1) {
                                        lowestPrice = totalPrice;
                                        lowestPriceIDs.clear();
                                        lowestPriceIDs.add(chair1.getId());
                                        lowestPriceIDs.add(chair2.getId());
                                        lowestPriceIDs.add(chair3.getId());
                                    }
                                }
                            }
                        }
                    }
                }

                //checks sets of 4 (sorry for the long lines!!! it has to be long or it is unreadable)
                for (Chair chair1 : validChairs) {
                    for (Chair chair2 : validChairs) {
                        for (Chair chair3 : validChairs) {
                            for (Chair chair4 : validChairs) {
                                //if the chairs are unique (only one of each chair)
                                if (chair1 != chair2 && chair1 != chair3 && chair1 != chair4
                                        && chair2 != chair3 && chair2 != chair4 && chair3 != chair4) {
                                    //if all the pieces are present between the four chairs
                                    if ((chair1.getLegs() || chair2.getLegs() || chair3.getLegs() || chair4.getLegs())
                                            && (chair1.getArms() || chair2.getArms() || chair3.getArms() || chair4.getArms())
                                            && (chair1.getSeat() || chair2.getSeat() || chair3.getSeat() || chair4.getArms())
                                            && (chair1.getCushion() || chair2.getCushion() || chair3.getCushion() || chair4.getCushion())) {
                                        int totalPrice = chair1.getPrice() + chair2.getPrice() + chair3.getPrice() + chair4.getPrice();
                                        //if the price is lower than the lowest price (or no valid combos have come up yet)
                                        if (totalPrice < lowestPrice || lowestPrice == -1) {
                                            lowestPrice = totalPrice;
                                            lowestPriceIDs.clear();
                                            lowestPriceIDs.add(chair1.getId());
                                            lowestPriceIDs.add(chair2.getId());
                                            lowestPriceIDs.add(chair3.getId());
                                            lowestPriceIDs.add(chair4.getId());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //we have checked all possible combinations and have found our lowest price for 1 item at this point!
                //if the lowest price is still -1, there are no valid combinations and the order can't be made
                if (lowestPrice == -1) {
                    totalLowestPrice = -1; //no more totalLowestPrice
                    lowestPriceIDs.clear(); //clear the IDs, we failed :(
                    return false;
                }
                //otherwise, remove the chairs from the validChairs list and increase the totalLowestPrice
                else {
                    totalLowestPrice += lowestPrice;
                    //A friendly reminder that the efficiency of our code is not a grading requirement.
                    for(int i = 0; i < lowestPriceIDs.size(); i++) {
                        for(Chair validChair : validChairs) {
                            if(lowestPriceIDs.get(i).equals(validChair.getId())){
                                validChairs.remove(validChair);
                            }
                        }
                    }
                }
            }

            return true; //finally done wow
        }
    }

    public void calcDesk(){

    }

    public void calcLamp(){

    }
    public void calcFiling(){

    }
}