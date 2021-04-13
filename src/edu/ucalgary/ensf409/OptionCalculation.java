package edu.ucalgary.ensf409;
import java.util.*;

public class OptionCalculation {

    private final String category;
    private final String type;
    private final int numOfItems;
    private final DatabaseAccess database;

    private int totalLowestPrice = 0;
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
                return calculateCheapestPrice(getFurnitureOfType(database.getChairList()));
            case "DESK":
                return calculateCheapestPrice(getFurnitureOfType(database.getDeskList()));
            case "LAMP":
                return calculateCheapestPrice(getFurnitureOfType(database.getLampList()));
            case "FILING":
                return calculateCheapestPrice(getFurnitureOfType(database.getFilingList()));
        }
        return false; //ok compiler
    }

    private <T extends Furniture> ArrayList<T> getFurnitureOfType(ArrayList<T> furnList){
	    ArrayList<T> furnitureOfType = new ArrayList<>();
	    for(T furniture : furnList){
	        if(furniture.getType().equals(type)){
                furnitureOfType.add(furniture);
            }
        }
	    return furnitureOfType;
    }
    private <T extends Furniture> boolean calculateCheapestPrice(ArrayList<T> furnList){
	    if(furnList.isEmpty()){
	        return false; //no valid combinations
        }
	    else {
            int maxNumOfItems = database.getChairList().get(0).getValidParts().length * numOfItems;
            for (int i = 1; i <= maxNumOfItems; i++) {
                if (i < furnList.size()) {
                    calculateCheapestCombo(furnList, i);
                }
            }
            if (lowestPriceIDs.isEmpty()) {
                return false;
            }
            return true;
        }
    }

    private <T extends Furniture> void calculateCheapestCombo(ArrayList<T> furnitureList, int r){
        ArrayList<T> currentCombo = new ArrayList<T>();
        findCombinations_Recursion(furnitureList, currentCombo, 0, 0, r);
    }

    private <T extends Furniture> void findCombinations_Recursion(ArrayList<T> furnList, ArrayList<T> currCombo, int currIndex, int level, int r){
        if(level == r){
            processCombination(currCombo);
            return;
        }
        for(int i = currIndex; i < furnList.size(); i++){
            currCombo.set(level, furnList.get(i));
            findCombinations_Recursion(furnList, currCombo, i+1, level+1, r);
        }
    }

    private <T extends Furniture> void processCombination(ArrayList<T> combo){
        //does the actual calculation stuff, need to do
    }

    //getters
    public ArrayList<String> getLowestPriceIDs() { return lowestPriceIDs; }
    public int getTotalLowestPrice() { return totalLowestPrice; }
    public String getCategory() { return this.category; }
    public String getType() { return this.type; }
    public int getNumOfItems() { return this.numOfItems; }
}