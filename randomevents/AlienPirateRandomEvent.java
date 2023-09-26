package com.mikeandliam.spaceexplorers.randomevents;

import com.mikeandliam.spaceexplorers.GameEnvironment;
import com.mikeandliam.spaceexplorers.Util;
import com.mikeandliam.spaceexplorers.event.EnvironmentEventArgs;
import com.mikeandliam.spaceexplorers.event.EventType;
import com.mikeandliam.spaceexplorers.inventory.InventoryItem;

/**
 * A RandomEvent in which alien pirates steal a random item from the players inventory
 */
public class AlienPirateRandomEvent implements RandomEvent {
    @Override
    public void performEvent(GameEnvironment environment) {
        if (environment.getInventory().size() > 0) {
            InventoryItem itemToRemove = environment.getInventory().get(Util.globalRandom.nextInt(environment.getInventory().size()));
            environment.getInventory().remove(itemToRemove);
            environment.getEnvironmentEvent().invoke(new EnvironmentEventArgs(EventType.ERROR, "Alien pirates stole " + itemToRemove.getDisplayName()));
        }
    }
}
