package com.mikeandliam.spaceexplorers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameEnvironmentTest {
    private GameEnvironment newTestEnvironment() {
        ArrayList<CrewMember> dummyCrew = new ArrayList<>();
        dummyCrew.add(new CrewMember(CrewMemberType.MONKEY, "test"));
        return new GameEnvironment(dummyCrew, new Ship("test"), 100);
    }

    @Test
    void nextDay() {
        GameEnvironment environment = newTestEnvironment();
        for (int i = 0; i < 80; i++) {
            environment.nextDay(true);
        }
        //everyone should be dead
        assertEquals(environment.getCrew().size(), 0);
    }
}