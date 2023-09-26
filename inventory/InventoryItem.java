package com.mikeandliam.spaceexplorers.inventory;

import javax.swing.*;

/**
 * An object such as food or meds that can be bought at a space outpost and is kept in the players inventory.
 */
public interface InventoryItem {
    int getPrice();

    String getDisplayName();

    String getDescription();

    ImageIcon getIcon();

    InventoryItem[] allFoodItems = {
            FoodItem.apples,
            FoodItem.pizza,
            FoodItem.bananas,
            FoodItem.carrots,
            FoodItem.nachos,
            FoodItem.vodka
    };

    InventoryItem[] allMedicalItems = {
            MedicalItem.anaesthetic,
            MedicalItem.cbd,
            MedicalItem.bandaid,
    };
}
