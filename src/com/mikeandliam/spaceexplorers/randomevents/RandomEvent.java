package com.mikeandliam.spaceexplorers.randomevents;

import com.mikeandliam.spaceexplorers.GameEnvironment;
import com.mikeandliam.spaceexplorers.Util;

/**
 * A random event that can happen to the player
 */
public interface RandomEvent {
    RandomEvent[] nextDayRandomEvents = {new AlienPirateRandomEvent(), new SpacePlagueRandomEvent()};
    RandomEvent[] nextPlanetRandomEvents = {new AsteroidBeltRandomEvent()};

    /**
     * Decides randomly whether a 'nextDayRandomEvent' will happen
     * If an event is going to happen, chooses a random event from RandomEvent.nextDayRandomEvents
     * and performs it
     * @param environment the GameEnvironment to perform the random event on
     */
    static void tryNextDayRandomEvent(GameEnvironment environment) {
        //50% chance of event happening
        if (Util.globalRandom.nextInt(2) == 0) {
            Util.getRandomElement(nextDayRandomEvents).performEvent(environment);
        }
    }

    /**
     * Decides randomly whether a 'nextPlanetRandomEvent' will happen
     * If an event is going to happen, chooses a random event from RandomEvent.nextPlanetRandomEvents
     * and performs it
     * @param environment the GameEnvironment to perform the random event on
     */
    static void tryNextPlanetRandomEvent(GameEnvironment environment) {
        //50% change of event happening
        if (Util.globalRandom.nextInt(2) == 0) {
            Util.getRandomElement(nextPlanetRandomEvents).performEvent(environment);
        }
    }

    /**
     * This is called when the event should happen. Override with event-specific code to carry out the event.
     * @param environment the GameEnvironment that will be affected by this RandomEvent
     */
    void performEvent(GameEnvironment environment);
}
