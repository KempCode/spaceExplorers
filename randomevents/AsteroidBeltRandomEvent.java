package com.mikeandliam.spaceexplorers.randomevents;

import com.mikeandliam.spaceexplorers.GameEnvironment;
import com.mikeandliam.spaceexplorers.Ship;
import com.mikeandliam.spaceexplorers.event.EnvironmentEventArgs;
import com.mikeandliam.spaceexplorers.event.EventType;

/**
 * A RandomEvent in which an asteroid hits the ship and lowers the ship's shield amount
 */
public class AsteroidBeltRandomEvent implements RandomEvent {

    @Override
    public void performEvent(GameEnvironment environment) {
        Ship ship = environment.getShip();
        ship.setShieldHealth(getShieldLevelAfterDamage(ship.getShieldHealth()));
        environment.getEnvironmentEvent().invoke(new EnvironmentEventArgs(
                EventType.ERROR,
                "Asteroid belt hit the ship."
        ));
    }

    private int getShieldLevelAfterDamage(int shipShieldLevel) {
        return (int) (shipShieldLevel - (.5 * (100 - shipShieldLevel) + 5));
    }
}
