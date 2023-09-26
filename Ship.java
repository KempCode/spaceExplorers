package com.mikeandliam.spaceexplorers;

import com.mikeandliam.spaceexplorers.event.Event;

public class Ship {
    private String name;
    private int shieldHealth = 100;

    private Event<Void> onShieldStrengthChangeEvent = new Event<>();

    /**
     * @param name name of this ship
     */
    public Ship(String name) {
        this.name = name;
    }

    /**
     * @return the name of this ship
     */
    public String getName() {
        return name;
    }

    /**
     * @return the shield health of this ship
     */
    public int getShieldHealth() {
        return shieldHealth;
    }

    /**
     * Sets the shield health of this ship to a specified value
     */
    public void setShieldHealth(int shieldHealth) {
        this.shieldHealth = shieldHealth;
        if (shieldHealth > 100) {
            shieldHealth = 100;
        }
        onShieldStrengthChangeEvent.invoke(null);
    }

    /**
     * Event that is fired whenever the shield strength of this ship changes.
     * This Event can be handled to update the UI display on a change.
     */
    public Event<Void> getOnShieldStrengthChangeEvent() {
        return onShieldStrengthChangeEvent;
    }
}
