package com.mikeandliam.spaceexplorers;

import com.mikeandliam.spaceexplorers.event.*;
import com.mikeandliam.spaceexplorers.inventory.CrewUsableInventoryItem;
import com.mikeandliam.spaceexplorers.inventory.InventoryItem;
import com.mikeandliam.spaceexplorers.inventory.SpacePlagueCure;
import com.mikeandliam.spaceexplorers.inventory.TransporterPart;
import com.mikeandliam.spaceexplorers.randomevents.RandomEvent;

import java.util.ArrayList;

/**
 * Main logic class
 */
public class GameEnvironment {
    public static String startText = "You and your crew are lost in space with your spaceship. Your spaceships has been \n" +
            "broken and its pieces are scattered throughout the surrounding planets. You will need \n" +
            "to find the missing pieces of your spaceship so that you can repair it and get home. \n";

    private ArrayList<CrewMember> crew;
    private Ship ship;
    private int gameDurationDays;
    private int elapsedDays = 0;
    private int numTransporterPartsRequired;
    private int numTransporterPartsFound = 0;
    private int money = 500;
    private ArrayList<InventoryItem> inventory = new ArrayList<>();
    private Planet currentPlanet = new Planet(this);
    private Event<EnvironmentEventArgs> environmentEvent = new Event<>();
    private Event<CrewEventArgs> crewEvent = new Event<>();
    private Event<GameEndEventArgs> gameEndEvent = new Event<>();


    /**
     * @param crew the crew of the game
     * @param ship the ship of the game
     * @param gameDurationDays amount of days the game should last
     */
    public GameEnvironment(ArrayList<CrewMember> crew, Ship ship, int gameDurationDays) {
        this.crew = crew;
        this.ship = ship;
        this.gameDurationDays = gameDurationDays;

        numTransporterPartsRequired = (int) Math.floor((2f / 3f) * gameDurationDays);

        //wire up all crew's events to the single event for all crew (this.crewEvent)
        for (CrewMember crewMember : crew) {
            crewMember.getCrewEvent().addHandler(data -> crewEvent.invoke(data));
        }

        //start on a new day
        nextDay(false);
    }

    /**
     * Moves to the next day, performing required actions that should happen at the start of they day
     * @param doEvents whether or not RandomEvents should happen on this day
     */
    public void nextDay(boolean doEvents) {
        if (doEvents) {
            elapsedDays++;
        }

        if (elapsedDays >= gameDurationDays) {
            gameEndEvent.invoke(new GameEndEventArgs(ship.getName(), elapsedDays, false, calculateScore()));
        }

        environmentEvent.invoke(new EnvironmentEventArgs(EventType.INFO,
                String.format("It is now day %d of %d.", elapsedDays + 1, gameDurationDays)
        ));

        //must add to arraylist to avoid ConcurrentModificationException
        ArrayList<CrewMember> survivingCrewMembers = new ArrayList<>();

        for (CrewMember crewMember : crew) {
            boolean survived = crewMember.nextDay();
            if (survived) {
                survivingCrewMembers.add(crewMember);
            }
        }

        crew = survivingCrewMembers;

        if (doEvents) {
            RandomEvent.tryNextDayRandomEvent(this);
        }
    }

    /**
     * @return The list of crew
     */
    public ArrayList<CrewMember> getCrew() {
        return crew;
    }

    /**
     * @return The player's inventory as a list of items
     */
    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    /**
     * @return The amount of money the player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * @param money the amount of money the player should have
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * @return The player's ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * @return The planet that the player is currently on
     */
    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    /**
     * An event that fires whenever something of note happens in the environment
     * (eg. a new day, player buying items, etc.)
     */
    public Event<EnvironmentEventArgs> getEnvironmentEvent() {
        return environmentEvent;
    }

    /**
     * An event that fires whenever something of note happens to any crew member.
     * Instead of handling an individual crew member's crewEvent,
     * you can handle this to be notified of events from all crew members
     */
    public Event<CrewEventArgs> getCrewEvent() {
        return crewEvent;
    }

    /**
     * Event that is fired when the game ends
     */
    public Event<GameEndEventArgs> getGameEndEvent() {
        return gameEndEvent;
    }

    /**
     * @return the number of transport parts required to finish the game
     */
    public int getNumTransporterPartsRequired() {
        return numTransporterPartsRequired;
    }

    /**
     * @return The amount of transporter parts that the player has found so far.
     */
    public int getNumTransporterPartsFound() {
        return numTransporterPartsFound;
    }

    /**
     * Makes a specified crew member use a specified CrewUsableInventoryItem
     */
    public void crewUseItem(CrewMember member, CrewUsableInventoryItem item) {
        if (member.tryPerformAction() && item.useOn(member)) {
            inventory.remove(item);
        } else {
            environmentEvent.invoke(new EnvironmentEventArgs(
                    EventType.ERROR,
                    String.format("Cannot use %s on %s", item.getDisplayName(), member.getName())
            ));
        }
    }

    /**
     * Makes a specified crew member sleep
     */
    public void crewSleep(CrewMember member) {
        if (member.tryPerformAction()) {
            member.sleep();
        }
    }

    /**
     * Makes a specified crew member repair the ship's shields
     */
    public void crewRepairShields(CrewMember member) {
        if (member.tryPerformAction()) {
            int repairAmount = member.getType().getRepairSkillLevel();
            ship.setShieldHealth(ship.getShieldHealth() + repairAmount);
            environmentEvent.invoke(new EnvironmentEventArgs(EventType.INFO, "Shield now at " + ship.getShieldHealth()));
        }
    }

    /**
     * Makes a specified crew member search for parts on the current planet
     */
    public void crewSearchForParts(CrewMember member) {
        if (member.tryPerformAction() && Util.globalRandom.nextInt(100) < member.getType().getSearchSkillLevel()) {
            InventoryItem foundItem = null;

            switch (Util.globalRandom.nextInt(4)) {
                case 0:
                    if (getCurrentPlanet().getHasTransporterPart()) {
                        foundItem = new TransporterPart();
                        numTransporterPartsFound++;
                        getCurrentPlanet().setHasTransporterPart(false);
                        if (numTransporterPartsFound >= numTransporterPartsRequired) {
                            gameEndEvent.invoke(new GameEndEventArgs(ship.getName(), elapsedDays, true, calculateScore()));
                        }
                        break;
                    }
                    //if there is no transporter part, fall through the switch/case and find a random food item.
                case 1:
                    foundItem = InventoryItem.allFoodItems[Util.globalRandom.nextInt(InventoryItem.allFoodItems.length)];
                    break;
                case 2:
                    foundItem = InventoryItem.allMedicalItems[Util.globalRandom.nextInt(InventoryItem.allMedicalItems.length)];
                    break;
                case 3:
                    foundItem = new SpacePlagueCure();
            }

            inventory.add(foundItem);

            assert foundItem != null;
            environmentEvent.invoke(new EnvironmentEventArgs(EventType.INFO, "Found " + foundItem.getDisplayName() + "."));

        } else {
            environmentEvent.invoke(new EnvironmentEventArgs(EventType.INFO, "Nothing was found."));
        }
    }

    /**
     * Makes the two specified crew members pilot the ship to a new planet, updating the currentPlanet
     */
    public void crewPilotToNewPlanet(CrewMember member1, CrewMember member2) {
        //the player must have at least 2 crew members with one action left to complete this.
        if (member1.getActionsRemaining() < 1 && member2.getActionsRemaining() < 1) {
            environmentEvent.invoke(new EnvironmentEventArgs(EventType.ERROR, "Both crew must have at least 1 action remaining"));
        } else {
            //update current planet...
            //every time you crewpilot to new planet reup inventory with constructor.
            member1.setActionsRemaining(member1.getActionsRemaining() - 1);
            member2.setActionsRemaining(member2.getActionsRemaining() - 1);
            member1.getStatusUpdatedEvent().invoke(null);
            member2.getStatusUpdatedEvent().invoke(null);
            currentPlanet = new Planet(this);

            RandomEvent.tryNextPlanetRandomEvent(this);

            environmentEvent.invoke(new EnvironmentEventArgs(EventType.INFO, "You have travelled to a new planet"));
        }
    }

    private int calculateScore() {
        int crewHealthSum = 0;
        for (CrewMember crewMember : crew) {
            crewHealthSum += crewMember.getHealth();
        }
        return 100 * (gameDurationDays - elapsedDays) + 50 * inventory.size() + crewHealthSum;
    }
}