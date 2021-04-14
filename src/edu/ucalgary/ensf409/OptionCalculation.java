package edu.ucalgary.ensf409;
import java.util.*;
//v1

public class OptionCalculation <T extends Furniture> {

    private final String type;
    private final int numOfItems;

    private int totalLowestPrice = -1;
    private ArrayList<T> lowestPriceItems = new ArrayList<>();
    private final ArrayList<String> lowestPriceIDs = new ArrayList<>();


	public OptionCalculation(String type, int numOfItems) {
        this.type = type;
        this.numOfItems = numOfItems;
    }

    private ArrayList<T> getFurnitureOfType(ArrayList<T> furnList){
	    ArrayList<T> furnitureOfType = new ArrayList<>();
	    for(T furniture : furnList){
	        if(furniture.getType().equals(type)){
                furnitureOfType.add(furniture);
            }
        }
	    return furnitureOfType;
    }
    public boolean calculateCheapestPrice(ArrayList<T> furnList){
	    furnList = getFurnitureOfType(furnList);
	    if(furnList.isEmpty()){
	        return false; //no valid items
        }
	    else {
            int maxNumOfItems = furnList.get(0).getValidParts().length * numOfItems;
            for (int i = 1; i <= maxNumOfItems; i++) {
                if (i < furnList.size()) {
                    calculateCheapestCombo(furnList, i);
                }
            }
            if (lowestPriceItems.isEmpty()) {
                return false;
            }
            else{
                genItemIDs(lowestPriceItems);
                return true;
            }
        }
    }

    private void calculateCheapestCombo(ArrayList<T> furnitureList, int r){
        ArrayList<T> currentCombo = new ArrayList<>();
        findCombinations_Recursion(furnitureList, currentCombo, 0, 0, r);
    }

    private void findCombinations_Recursion(ArrayList<T> furnList, ArrayList<T> currCombo, int currIndex, int level, int r){
        if(level == r){
            processCombination(currCombo);
            return;
        }
        for(int i = currIndex; i < furnList.size(); i++){
            currCombo.set(level, furnList.get(i));
            findCombinations_Recursion(furnList, currCombo, i+1, level+1, r);
        }
    }

    private void processCombination(ArrayList<T> combo){
        int[] numOfParts = new int[combo.get(0).getValidParts().length];
        int totalPrice = 0;
        for(T item : combo){
            totalPrice += item.getPrice();
            for(int i = 0; i < item.getValidParts().length; i++){
                if(item.getValidParts()[i]){
                    numOfParts[i]++;
                }
            }
        }
        //checks to see if each item has all the parts for this combination
        boolean hasAllParts = true;
        for(int numOfThisPart : numOfParts){
            if(numOfThisPart < numOfItems){
                hasAllParts = false;
                break;
            }
        }
        //if it has all the parts and the total price is lower (or the first valid one),
        //set the total lowest price and its items to the combination processed
        if(hasAllParts && (totalPrice < totalLowestPrice || totalLowestPrice == -1)){
            lowestPriceItems = combo;
            totalLowestPrice = totalPrice;
        }
    }

    public void genItemIDs(ArrayList<T> furnList){
	    lowestPriceIDs.clear();
	    for(T item : furnList){
	        lowestPriceIDs.add(item.getId());
        }
    }

    //getters
    public ArrayList<T> getLowestPriceItems() { return lowestPriceItems; }
    public ArrayList<String> getLowestPriceIDs() { return lowestPriceIDs; }
    public int getTotalLowestPrice() { return totalLowestPrice; }
    public String getType() { return this.type; }
    public int getNumOfItems() { return this.numOfItems; }
}