package com.mikeandliam.spaceexplorers;

import com.mikeandliam.spaceexplorers.event.EnvironmentEventArgs;
import com.mikeandliam.spaceexplorers.event.EventType;
import com.mikeandliam.spaceexplorers.inventory.FoodItem;
import com.mikeandliam.spaceexplorers.inventory.InventoryItem;
import com.mikeandliam.spaceexplorers.inventory.MedicalItem;
import com.mikeandliam.spaceexplorers.inventory.SpacePlagueCure;

import java.util.ArrayList;
import java.util.List;

/**
 * Planet and "space outpost" are interchangeable terms since each planet has one space outpost
 */
public class Planet {
    //Make a planet list.
    private ArrayList<InventoryItem> itemsForSale = new ArrayList<>();
    private GameEnvironment environment;
    private boolean hasTransporterPart = true;


    //Using an integer to track the planet/ number of outpost player is on.
    /**
     * This is the Planet class constructor.
      * @param environment This parameter is the specific instantiation of the Game environment.
     */
    public Planet(GameEnvironment environment) {
        this.environment = environment;

        this.itemsForSale.addAll(List.of(
                FoodItem.pizza,
                FoodItem.nachos,
                FoodItem.carrots,
                FoodItem.apples,
                FoodItem.bananas,
                FoodItem.vodka,
                MedicalItem.anaesthetic,
                MedicalItem.bandaid,
                MedicalItem.cbd,
                new SpacePlagueCure()
        ));
    }


    //Ability to purchase item from inventory.. add to players inventory.
    //method to remove inventory items after purchase.

    /**
     * Try to purchase an item from a space outpost
     *
     * @param item the item to buy. MUST be one of the items being sold by the current space outpost
     * @return whether or not the purchase as successful (ie. player has enough money)
     */
    public boolean attemptPurchase(InventoryItem item) {
        if (!getItemsForSale().contains(item)) {
            throw new IllegalArgumentException("Item to be bought must be for sale at the current planet.");
        }

        if (environment.getMoney() >= item.getPrice()) {
            //have enough money to buy item

            environment.getCurrentPlanet().getItemsForSale().remove(item);
            environment.getInventory().add(item);
            environment.setMoney(environment.getMoney() - item.getPrice());
            environment.getEnvironmentEvent().invoke(new EnvironmentEventArgs(EventType.INFO, "Purchased " + item.getDisplayName() + "."));
            return true;
        } else {
            // not enough money
            environment.getEnvironmentEvent().invoke(new EnvironmentEventArgs(EventType.ERROR, "Not enough funds."));
            return false;
        }
    }

    /**
      * @return Returns an array list of the items for sale. This is a getter.
     */
    public ArrayList<InventoryItem> getItemsForSale() {
        return itemsForSale;
    }

    /**
     * @param itemsForSale Takes array list of items for sale as the parameter.
     * Sets the items for sale at specific instantiation of Planet class.
     */
    public void setItemsForSale(ArrayList<InventoryItem> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }

    /**
      * @return returns boolean value for whether a Planet object contains a transporter part.
     */
    public boolean getHasTransporterPart() {
        return hasTransporterPart;
    }

    /**
     * @param hasTransporterPart Boolean value for if Planet has a transporter part.
     * Sets the boolean value for whether a specific Planet object has a transporter part to true or false.
     */
    public void setHasTransporterPart(boolean hasTransporterPart) {
        this.hasTransporterPart = hasTransporterPart;
    }
}
