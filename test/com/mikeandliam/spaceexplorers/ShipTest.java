package com.mikeandliam.spaceexplorers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
    private String name = "Ship Name";
    private Ship ship= new Ship(name);

    @Test
    void getName() {
        assertEquals(ship.getName(), name);
    }

    @Test
    void getShieldHealth() {
        assertEquals(ship.getShieldHealth(), 100);
    }
}