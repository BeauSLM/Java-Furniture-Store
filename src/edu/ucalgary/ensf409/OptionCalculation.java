package edu.ucalgary.ensf409;
import java.util.*;
//v1.1
//documentation added

/**
 * Calculates the cheapest combination of items needed to fulfill the requested order, indicating if its not possible with the items in the database.
 *
 * @param <T> One of the four furniture types available: CHAIR, DESK, LAMP, FILING.
 */
public class OptionCalculation <T extends Furniture> {

    /**
     * Indicates what type of furniture: CHAIR, DESK, LAMP, or FILING
     */
    private final String TYPE;

    /**
     * The number of items required
     */
    private final int NUMOFITEMS;

    /**
     * The price of the cheapest possible order.
     */
    private int totalLowestPrice = -1;

    /**
     * A list of objects of the Furniture that are in the cheapest possible order.
     */
    private ArrayList<T> lowestPriceItems = new ArrayList<>();

    /**
     * List of id's of the furniture items needed in the cheapest possible order.
     */
    private ArrayList<String> lowestPriceIDs = new ArrayList<>();


    /**
     * Instantiates a new Option calculation.
     *
     * @param TYPE       Indicates what type of furniture.
     * @param NUMOFITEMS the number of items.
     */
    public OptionCalculation(String TYPE, int NUMOFITEMS) {
        this.TYPE = TYPE;
        this.NUMOFITEMS = NUMOFITEMS;
    }

    private ArrayList<T> getFurnitureOfType(ArrayList<T> furnList){
	    ArrayList<T> furnitureOfType = new ArrayList<>();
	    for(T furniture : furnList){
	        if(furniture.getType().equals(TYPE)){
                furnitureOfType.add(furniture);
            }
        }
	    return furnitureOfType;
    }

    /**
     * Finds the cheapest combination of furniture needed to fulfill the order, updating the class fields with a list of the furniture and its overall price.
     *
     * @param furnList the list of furniture available to order.
     * @return Boolean indicating if all necessary items are in stock to fulfill the order. Used to choose what to generate: an order form or a manufacturer recommendation.
     */
    public boolean calculateCheapestPrice(ArrayList<T> furnList) {
	    furnList = getFurnitureOfType(furnList);
	    if(furnList.isEmpty()){
	        return false; //no valid items
        }
	    else {
            int maxNumOfItems = furnList.get(0).getValidParts().length * NUMOFITEMS;
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

    /**
     * Finds the cheapest combination of items needed to provide all the necessary components to fulfill the order.
     *
     * @param furnitureList List of available furniture.
     * @param r
     */
    private void calculateCheapestCombo(ArrayList<T> furnitureList, int r){
        ArrayList<T> currentCombo = new ArrayList<>();
        findCombinations_Recursion(furnitureList, currentCombo, 0, 0, r);
    }

    /**
     * Implementation of a recursive algorithm that finds the cheapest combination of furniture with the necessary components.
     *
     * @param furnList
     * @param currCombo
     * @param currIndex
     * @param level
     * @param r
     */
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

    /**
     *
     *
     * @param combo
     */
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
            if(numOfThisPart < NUMOFITEMS){
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

    /**
     * Stores the IDs of furniture items needed to fulfill the order in the member ArrayList.
     *
     * @param furnList Array of the appropriate furniture type, from which the IDs of the furniture will be fetched.
     */
    public void genItemIDs(ArrayList<T> furnList){
	    lowestPriceIDs.clear();
	    for(T item : furnList){
	        lowestPriceIDs.add(item.getId());
        }
    }

    /**
     * Gets the list of Furniture objects containing the lowest price items.
     *
     * @return the list.
     */
//getters
    public ArrayList<T> getLowestPriceItems() { return lowestPriceItems; }

    /**
     * Gets the list of IDs of the items needed to fulfill the order
     *
     * @return the list.
     */
    public ArrayList<String> getLowestPriceIDs() { return lowestPriceIDs; }

    /**
     * Gets total lowest price.
     *
     * @return the total lowest price
     */
    public int getTotalLowestPrice() { return totalLowestPrice; }

    /**
     * Gets type of furniture.
     *
     * @return the type
     */
    public String getType() { return this.TYPE; }

    /**
     * Gets number of items.
     *
     * @return the number of items
     */
    public int getNumOfItems() { return this.NUMOFITEMS; }
}