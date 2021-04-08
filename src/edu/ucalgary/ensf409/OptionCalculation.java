package edu.ucalgary.ensf409;
import java.util.*;

public class OptionCalculation {

    private String category;
    private DatabaseAccess database;

	public OptionCalculation(String category, DatabaseAccess database) {
        this.category = category;
        this.database = database;
    }

    public void calculateCheapestPrice(){
	    switch(category) {
            case "Chair":
                calcChair();
                break;
            case "Desk":
                calcDesk();
                break;
            case "Lamp":
                calcLamp();
                break;
            case "Filing":
                calcFiling();
                break;
        }

        //calculate cheapest option - brute force it using all possible combinations
        //if fails to calculate, then there isn't the inventory

        //remove said items from the database
        //print order
    }

    public void calcChair(){
        ArrayList<Chair> validChairs = new ArrayList<>();
        for(Chair temp : database.getChairList()) {
            if(temp.getType().equals(category)) {
                validChairs.add(temp);
            }
        }
        //now that we have a list of just the valid types, go through every combination of them. brute force a very ugly
        //solution by checking total validity then checking if price is lower than previous. yuck!
        //AND we have to do it per type - so 4 times. :(
    }

    public void calcDesk(){

    }

    public void calcLamp(){

    }
    public void calcFiling(){

    }
}