package com.mikeandliam.spaceexplorers.inventory;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.Resources;

import javax.swing.*;

/**
 * MedicalItem class is a class that can be used on a crew member to restore a specified amount of health.
 */
public class MedicalItem implements CrewUsableInventoryItem {
    private int healthRestoration;
    private String displayName;
    private String description;
    private int price;
    private ImageIcon icon;

    /**
     * Constructor for the MedicalItem class.
     * @param healthRestoration Integer value that contains level of health restoration a medical item is worth.
     * @param displayName String that gives the name to be displayed when a Medical item is instantiated.
     * @param description A string that gives a description of the medical item.
     * @param price Integer property that contains the price of the Medical item.
     * @param icon Contains image data for the Medical item when instantiated.
     */
    public MedicalItem(int healthRestoration, String displayName, String description, int price, ImageIcon icon) {
        this.healthRestoration = healthRestoration;
        this.displayName = displayName;
        this.description = description;
        this.price = price;
        this.icon = icon;
    }

    /**
     * @param crewMember the crew member who uses this medical item
     * @return whether or not the healing was successful (ie. was the crew member already at full health?)
     */
    @Override
    public boolean useOn(CrewMember crewMember) {
        if(crewMember.getHealth() < 100) {
            crewMember.restoreHealth(healthRestoration);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return getter for the health restoration property.
     */
    public int getHealthRestoration() {
        return healthRestoration;
    }

    /**
     * @return returns the price for the price property. Overrides the superclass.
     */
    @Override
    public int getPrice() {
        return this.price;
    }

    /**
     * @return return the display name for an instantiated MedicalItem. Overrides the superclass.
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * @return Returns the Description for an instantiated MedicalItem. Overrides the superclass.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * @return returns the image data for the icon used when instantiating a medical object. Overrides the superclass.
     * This is a getter.
     */
    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    //Instantiation of 3 Medical Items.
    public static MedicalItem anaesthetic = new MedicalItem(20, "Anaesthetic", "This will numb your crew member and increase health by 20", 20, Resources.ANAESTHETIC);
    public static MedicalItem bandaid = new MedicalItem(30, "Bandaid", "Will heal cuts and increase health by 30", 25, Resources.BANDAID);
    public static MedicalItem cbd = new MedicalItem(40, "Cannabidiol", "Me oh my I'm a healthy boy! Increases health by 40", 30, Resources.CBD);
}
