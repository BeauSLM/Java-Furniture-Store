package edu.ucalgary.ensf409;
import java.util.*;

public class OptionCalculation {

    private final String category;
    private final String type;
    private final int numOfItems;
    private final DatabaseAccess database;

    private int totalLowestPrice = 0; //-1 means there isn't enough stock to build the order
    private ArrayList<String> lowestPriceIDs = new ArrayList<>(); //used in the case of a successful order

	public OptionCalculation(String category, String type, int numOfItems, DatabaseAccess database) {
        this.category = category;
        this.type = type;
        this.numOfItems = numOfItems;
        this.database = database;
    }

    public boolean calculateCheapestPrice(){
	    switch(category) {
            case "CHAIR":
                return calcChair();
            case "DESK":
                return calcDesk();
            case "LAMP":
                return calcLamp();
            case "FILING":
                return calcFiling();
        }
        return false; //ok compiler
    }

    //i did not take an algorithm class please forgive me for the following abomination - TT
    //needs separate functions because each has a different # of possible combinations and different getters and stuff
    private boolean calcChair(){
	    //separates the list into just the desired type
        ArrayList<Chair> validChairs = new ArrayList<>();
        for(Chair temp : database.getChairList()) {
            if(temp.getType().equals(type)) {
                validChairs.add(temp);
            }
        }
        //can't make order if no chairs of type
        if(validChairs.isEmpty()){
            return false;
        }
        //time to check every combination in search of the lowest price
        //SPAGHETTI CODE TIME I AM SO SORRY
        else {
            for(int itemNum = 0; itemNum < numOfItems; itemNum++) {
                int lowestPrice = -1, totalPrice;

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

                                totalPrice = chair1.getPrice() + chair2.getPrice();
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
                                        && (chair1.getArms() || chair2.getLegs() || chair3.getArms())
                                        && (chair1.getSeat() || chair2.getSeat() || chair3.getSeat())
                                        && (chair1.getCushion() || chair2.getCushion() || chair3.getCushion())) {
                                    totalPrice = chair1.getPrice() + chair2.getPrice() + chair3.getPrice();
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
                                        totalPrice = chair1.getPrice() + chair2.getPrice() + chair3.getPrice() + chair4.getPrice();
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
                    //Removes "bought" chairs from list of valid desks.
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

            return true; //success, yay!!!
        }
    }

    private boolean calcDesk(){
        //separates the list into just the desired type
        ArrayList<Desk> validDesks = new ArrayList<>();
        for(Desk temp : database.getDeskList()) {
            if(temp.getType().equals(type)) {
                validDesks.add(temp);
            }
        }
        //can't make order if no desks of type
        if(validDesks.isEmpty()){
            return false;
        }
        //
        else {
            for(int itemNum = 0; itemNum < numOfItems; itemNum++) {
                int lowestPrice = -1, totalPrice;

                //checks sets of 1
                for (Desk desk : validDesks) {
                    //if all the pieces are present on the desk
                    if (desk.getLegs() && desk.getTop() && desk.getDrawer()) {
                        //if the price of the desk is the lowest so far (or if it's the first combo)
                        if (desk.getPrice() < lowestPrice || lowestPrice == -1) {
                            lowestPrice = desk.getPrice();
                            lowestPriceIDs.clear();
                            lowestPriceIDs.add(desk.getId());
                        }
                    }
                }

                //checks sets of 2
                for (Desk desk1 : validDesks) {
                    for (Desk desk2 : validDesks) {
                        //if the desks are unique (only one of each desk)
                        if (desk1 != desk2) {
                            //if all the pieces are present between the two
                            if ((desk1.getLegs() || desk2.getLegs())
                                    && (desk1.getTop() || desk2.getTop())
                                    && (desk1.getDrawer() || desk2.getDrawer()) ) {

                                totalPrice = desk1.getPrice() + desk2.getPrice();
                                //if the price is lower than the lowest price (or no valid combos have come up yet)
                                if (totalPrice < lowestPrice || lowestPrice == -1) {
                                    lowestPrice = totalPrice;
                                    lowestPriceIDs.clear();
                                    lowestPriceIDs.add(desk1.getId());
                                    lowestPriceIDs.add(desk2.getId());
                                }
                            }
                        }
                    }
                }

                //checks sets of 3
                for (Desk desk1 : validDesks) {
                    for (Desk desk2 : validDesks) {
                        for (Desk desk3 : validDesks) {
                            //if the desks are unique (only one of each desk)
                            if (desk1 != desk2 && desk2 != desk3 && desk1 != desk3) {
                                //if all the pieces are present between the three
                                if ((desk1.getLegs() || desk2.getLegs() || desk3.getLegs())
                                        && (desk1.getTop() || desk2.getTop() || desk3.getTop())
                                        && (desk1.getDrawer() || desk2.getDrawer() || desk3.getDrawer()) ) {
                                    totalPrice = desk1.getPrice() + desk2.getPrice() + desk3.getPrice();
                                    //if the price is lower than the lowest price (or no valid combos have come up yet)
                                    if (totalPrice < lowestPrice || lowestPrice == -1) {
                                        lowestPrice = totalPrice;
                                        lowestPriceIDs.clear();
                                        lowestPriceIDs.add(desk1.getId());
                                        lowestPriceIDs.add(desk2.getId());
                                        lowestPriceIDs.add(desk3.getId());
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
                //otherwise, remove the desks from the validDesks list and increase the totalLowestPrice
                else {
                    totalLowestPrice += lowestPrice;
                    //Removes "bought" desks from list of valid desks.
                    //A friendly reminder that the efficiency of our code is not a grading requirement.
                    for(int i = 0; i < lowestPriceIDs.size(); i++) {
                        for(Desk validDesk : validDesks) {
                            if(lowestPriceIDs.get(i).equals(validDesk.getId())){
                                validDesks.remove(validDesk);
                            }
                        }
                    }
                }
            }

            return true; //success, yay!!!
        }
    }

    private boolean calcLamp(){
        //separates the list into just the desired type
        ArrayList<Lamp> validLamps = new ArrayList<>();
        for(Lamp temp : database.getLampList()) {
            if(temp.getType().equals(type)) {
                validLamps.add(temp);
            }
        }
        //can't make order if no lamps of type
        if(validLamps.isEmpty()){
            return false;
        }
        //order time!
        else {
            for(int itemNum = 0; itemNum < numOfItems; itemNum++) {
                int lowestPrice = -1, totalPrice;

                //checks sets of 1
                for (Lamp lamp : validLamps) {
                    //if all the pieces are present on the lamp
                    if (lamp.getBase() && lamp.getBulb()) {
                        //if the price of the lamp is the lowest so far (or if it's the first combo)
                        if (lamp.getPrice() < lowestPrice || lowestPrice == -1) {
                            lowestPrice = lamp.getPrice();
                            lowestPriceIDs.clear();
                            lowestPriceIDs.add(lamp.getId());
                        }
                    }
                }

                //checks sets of 2
                for (Lamp lamp1 : validLamps) {
                    for (Lamp lamp2 : validLamps) {
                        //if the lamps are unique (only one of each lamp)
                        if (lamp1 != lamp2) {
                            //if all the pieces are present between the two
                            if ((lamp1.getBase() || lamp2.getBase())
                                    && (lamp1.getBulb() || lamp2.getBulb()) ) {

                                totalPrice = lamp1.getPrice() + lamp2.getPrice();
                                //if the price is lower than the lowest price (or no valid combos have come up yet)
                                if (totalPrice < lowestPrice || lowestPrice == -1) {
                                    lowestPrice = totalPrice;
                                    lowestPriceIDs.clear();
                                    lowestPriceIDs.add(lamp1.getId());
                                    lowestPriceIDs.add(lamp2.getId());
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
                //otherwise, remove the lamps from the validLamps list and increase the totalLowestPrice
                else {
                    totalLowestPrice += lowestPrice;
                    //Removes "bought" lamps from list of valid lamps.
                    //A friendly reminder that the efficiency of our code is not a grading requirement.
                    for(int i = 0; i < lowestPriceIDs.size(); i++) {
                        for(Lamp validLamp : validLamps) {
                            if(lowestPriceIDs.get(i).equals(validLamp.getId())){
                                validLamps.remove(validLamp);
                            }
                        }
                    }
                }
            }

            return true; //success, yay!!!
        }
    }

    //if anyone has better ideas to reuse code feel free to edit - please edit oh my god
    private boolean calcFiling(){
        //separates the list into just the desired type
        ArrayList<Filing> validFilings = new ArrayList<>();
        for(Filing temp : database.getFilingList()) {
            if(temp.getType().equals(type)) {
                validFilings.add(temp);
            }
        }
        //can't make order if no filings of type
        if(validFilings.isEmpty()){
            return false;
        }
        //order time!
        else {
            for(int itemNum = 0; itemNum < numOfItems; itemNum++) {
                int lowestPrice = -1, totalPrice;

                //checks sets of 1
                for (Filing filing : validFilings) {
                    //if all the pieces are present on the filing
                    if (filing.getRails() && filing.getDrawers() && filing.getCabinet()) {
                        //if the price of the filing is the lowest so far (or if it's the first combo)
                        if (filing.getPrice() < lowestPrice || lowestPrice == -1) {
                            lowestPrice = filing.getPrice();
                            lowestPriceIDs.clear();
                            lowestPriceIDs.add(filing.getId());
                        }
                    }
                }

                //checks sets of 2
                for (Filing filing1 : validFilings) {
                    for (Filing filing2 : validFilings) {
                        //if the filings are unique (only one of each filing)
                        if (filing1 != filing2) {
                            //if all the pieces are present between the two
                            if ((filing1.getRails() || filing2.getRails())
                                    && (filing1.getDrawers() || filing2.getDrawers())
                                    && (filing1.getCabinet() || filing2.getCabinet()) ) {

                                totalPrice = filing1.getPrice() + filing2.getPrice();
                                //if the price is lower than the lowest price (or no valid combos have come up yet)
                                if (totalPrice < lowestPrice || lowestPrice == -1) {
                                    lowestPrice = totalPrice;
                                    lowestPriceIDs.clear();
                                    lowestPriceIDs.add(filing1.getId());
                                    lowestPriceIDs.add(filing2.getId());
                                }
                            }
                        }
                    }
                }

                //checks sets of 3
                for (Filing filing1 : validFilings) {
                    for (Filing filing2 : validFilings) {
                        for (Filing filing3 : validFilings) {
                            //if the filings are unique (only one of each filing)
                            if (filing1 != filing2 && filing2 != filing3 && filing1 != filing3) {
                                //if all the pieces are present between the three
                                if ((filing1.getRails() || filing2.getRails() || filing3.getRails())
                                        && (filing1.getDrawers() || filing2.getDrawers() || filing3.getDrawers())
                                        && (filing1.getCabinet() || filing2.getCabinet() || filing3.getCabinet()) ) {
                                    totalPrice = filing1.getPrice() + filing2.getPrice() + filing3.getPrice();
                                    //if the price is lower than the lowest price (or no valid combos have come up yet)
                                    if (totalPrice < lowestPrice || lowestPrice == -1) {
                                        lowestPrice = totalPrice;
                                        lowestPriceIDs.clear();
                                        lowestPriceIDs.add(filing1.getId());
                                        lowestPriceIDs.add(filing2.getId());
                                        lowestPriceIDs.add(filing3.getId());
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
                //otherwise, remove the filings from the validFilings list and increase the totalLowestPrice
                else {
                    totalLowestPrice += lowestPrice;
                    //Removes "bought" filings from list of valid filings.
                    //A friendly reminder that the efficiency of our code is not a grading requirement.
                    for(int i = 0; i < lowestPriceIDs.size(); i++) {
                        for(Filing validFiling : validFilings) {
                            if(lowestPriceIDs.get(i).equals(validFiling.getId())){
                                validFilings.remove(validFiling);
                            }
                        }
                    }
                }
            }

            return true; //success, yay!!!
        }
    }

    public ArrayList<String> getLowestPriceIDs() { return lowestPriceIDs; }
    public int getTotalLowestPrice() { return totalLowestPrice; }
    public String getCategory() {
	    return this.category;
    }
    public String getType() {
	    return this.type;
    }
    public int getNumOfItems() {
	    return this.numOfItems;
    }
}