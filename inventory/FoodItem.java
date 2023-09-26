package com.mikeandliam.spaceexplorers.inventory;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.Resources;

import javax.swing.*;

/**
 * Regens a certain amount of nutrition when used on a crew member.
 * This contains the constructor for FoodItem.
 */
public class FoodItem implements CrewUsableInventoryItem {
    private int nutrition;
    private String displayName;
    private String description;
    private int price;
    private ImageIcon icon;


    /**
     *
     * @param nutrition This is the nutrition rating/ value of every food item.
     * @param displayName This is the string displayName for every FoodItem
     * @param description This property is a rough description of every food item as a String.
     * @param price Integer value for the price of FoodItem.
     * @param icon Property contains value for icon image.
     */
    public FoodItem(int nutrition, String displayName, String description, int price, ImageIcon icon) {
        this.nutrition = nutrition;
        this.displayName = displayName;
        this.description = description;
        this.price = price;
        this.icon = icon;
    }

    /**
     * @param crewMember the crew member who eats this food
     * @return whether the eating was successful (eg. was the crew member already at full hunger?)
     */
    @Override
    public boolean useOn(CrewMember crewMember) {
        if (crewMember.getHunger() > 0) {
            crewMember.restoreHunger(nutrition);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Getter for nutrition value.
     */
    public int getNutrition() {
        return this.nutrition;
    }

    /**
     * @return Getter for price of FoodItem.
     */
    @Override
    public int getPrice() {
        return this.price;
    }

    /**
     * @return getter for the DisplayName property.
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * @return Getter for the description property. returns this.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * @return returns the icon image property.
     */
    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    //Instantiation of 6 Food Items.
    //The methods available to access each argument are contained within the implemented interface, in crewMemberType.
    public static FoodItem pizza = new FoodItem(30, "Pizza", "This is Hawaiian Pizza. 30 Nutrition", 10, Resources.PIZZA);
    public static FoodItem nachos = new FoodItem(25, "Nachos", "Nachos are an unhealthy snack. 25 Nutrition", 8, Resources.NACHOES);
    public static FoodItem carrots = new FoodItem(40, "Carrots", "Carrots are very good for your crew. 40 Nutrition", 15, Resources.CARROT);
    public static FoodItem apples = new FoodItem(45, "Apples", "Another healthy alternative. 45 Nutrition", 16, Resources.APPLE);
    public static FoodItem bananas = new FoodItem(45, "Bananas", "45 Nutrition", 16, Resources.BANANA);
    public static FoodItem vodka = new FoodItem(50, "Vodka", "This is the healthiest of them all. 50 Nutrition", 20, Resources.VODKA);


}
