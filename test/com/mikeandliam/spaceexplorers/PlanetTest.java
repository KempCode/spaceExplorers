package com.mikeandliam.spaceexplorers;

import com.mikeandliam.spaceexplorers.inventory.FoodItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlanetTest {

    @Test
    void attemptPurchase() {
        GameEnvironment environment = new GameEnvironment(new ArrayList<>(), new Ship("test"), 5);
        Planet planet = new Planet(environment);
        assertTrue(planet.getHasTransporterPart());

        assertTrue(planet.attemptPurchase(FoodItem.apples));
        assertTrue(environment.getInventory().contains(FoodItem.apples));

        environment.setMoney(0);
        assertFalse(planet.attemptPurchase(FoodItem.carrots));

        //give player exactly enough monet
        environment.setMoney(FoodItem.bananas.getPrice());
        assertTrue(planet.attemptPurchase(FoodItem.bananas));
        assertTrue(environment.getInventory().contains(FoodItem.bananas));
    }
}