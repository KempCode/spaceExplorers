package com.mikeandliam.spaceexplorers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrewMemberTest {
    private CrewMemberType testType = CrewMemberType.MONKEY;

    @Test
    void nextDay() {
        CrewMember crew = new CrewMember(testType, "Test Crew Member");

        crew.tryPerformAction();
        crew.tryPerformAction();
        assertEquals(crew.getActionsRemaining(), 0);

        crew.nextDay();
        assertEquals(crew.getActionsRemaining(), 2);

        assertEquals(crew.getHealth(), 100 - testType.getHealthDegradation());
        assertEquals(crew.getHunger(), testType.getHungerDegradation());
        assertEquals(crew.getTiredness(), testType.getTirednessDegradation());

    }

    @Test
    void restoreHealth() {
        CrewMember crew = new CrewMember(testType, "Test");
        crew.nextDay();
        crew.nextDay();

        int expectedHealth = 100 - 2 * testType.getHealthDegradation();
        assertEquals(crew.getHealth(), expectedHealth);
        crew.restoreHealth(1);
        assertEquals(crew.getHealth(), expectedHealth + 1);

        //test the 100 upper health limit
        crew.restoreHealth(100000);
        assertEquals(crew.getHealth(), 100);
    }

    @Test
    void addNutrition() {
        CrewMember crew = new CrewMember(testType, "Test");
        crew.nextDay();
        crew.nextDay();

        int expectedHunger = 2 * testType.getHungerDegradation();
        assertEquals(crew.getHunger(), expectedHunger);
        crew.restoreHunger(1);
        assertEquals(crew.getHunger(), expectedHunger - 1);

        //test the 100 upper health limit
        crew.restoreHunger(100000);
        assertEquals(crew.getHunger(), 0);
    }

    @Test
    void sleep() {
        CrewMember crew = new CrewMember(testType, "Test");
        crew.nextDay();
        assertEquals(crew.getTiredness(), testType.getTirednessDegradation());
        crew.sleep();
        assertEquals(crew.getTiredness(), 0);
    }

    @Test
    void tryPerformAction() {
        CrewMember crew = new CrewMember(testType, "Test");
        crew.nextDay();
        assertEquals(crew.getActionsRemaining(), 2);

        assertTrue(crew.tryPerformAction());
        assertEquals(crew.getActionsRemaining(), 1);
        assertTrue(crew.tryPerformAction());
        assertEquals(crew.getActionsRemaining(), 0);
        assertFalse(crew.tryPerformAction());
        assertEquals(crew.getActionsRemaining(), 0);
    }
}