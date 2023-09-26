package com.mikeandliam.spaceexplorers.inventory;

import com.mikeandliam.spaceexplorers.CrewMember;

/**
 * These objects can be used by a specific crew member to heal themselves or regen hunger.
 * This contains the interface promise for the inventory items usable by the crew and inherits InventoryItem.
 */
public interface CrewUsableInventoryItem extends InventoryItem {
    /**
     * @return whether or not it was successful
     */
    boolean useOn(CrewMember crewMember);
}
