package com.mikeandliam.spaceexplorers.inventory;

import com.mikeandliam.spaceexplorers.Resources;

import javax.swing.*;

/**
 * This is the class used for transporter parts, implements inventory item interface.
 */
public class TransporterPart implements InventoryItem {
    /**
     * @return Returns the price of a transporter part to fix the ship but it is 0 because it can only be found
     * when the crew searches the planet.
     */
    @Override
    public int getPrice() {
        return 0;
    }

    /**
     * @return Returns the display name for the transporter part. It is a getter for this string.
     */
    @Override
    public String getDisplayName() {
        return "Transporter Part";
    }

    /**
     * @return Returns a string description of the transporter part item.
     */
    @Override
    public String getDescription() {
        return "A piece of your spaceship";
    }

    /**
     * @return Is a getter for the image data used for the transporter part. Returns this.
     */
    @Override
    public ImageIcon getIcon() {
        return Resources.TRANSPORTER;
    }
}
