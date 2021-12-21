package com.mikeandliam.spaceexplorers.inventory;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.Resources;

import javax.swing.*;

/**
 * Class that cures the space plague random event for a specific crew member.
 * The class implements the CrewUsableInventoryItem interface.
 */
public class SpacePlagueCure implements CrewUsableInventoryItem {

    /**
     * useOn is used to specify which crew member to use the SpacePlagueCure on.
     * @param crewMember is the parameter where you specify which crew member to use it on.
     * @return Returns true if the crew member has space plague and cures it. Returns false if they don't have it.
     */
    @Override
    public boolean useOn(CrewMember crewMember) {
        if (crewMember.getHasSpacePlague()) {
            crewMember.setHasSpacePlague(false);
            return true;
        } else {
            return false;
        }
    }

    /**
      * @return returns the price of the SpacePlagueCure item as $50
     */
    @Override
    public int getPrice() {
        return 50;
    }

    /**
      * @return Returns the item's display name.
     */
    @Override
    public String getDisplayName() {
        return "Space plague cure";
    }

    /**
      * @return Returns the String description of what the space plague cure is.
     */
    @Override
    public String getDescription() {
        return "Cures a crew member of the space plague.";
    }

    /**
      * @return This is a getter for the image used for the space plague cure icon. Returns the cure icon image data.
     */
    @Override
    public ImageIcon getIcon() {
        return Resources.CURE;
    }
}
