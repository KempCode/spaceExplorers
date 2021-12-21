package com.mikeandliam.spaceexplorers;

import com.mikeandliam.spaceexplorers.event.CrewEventArgs;
import com.mikeandliam.spaceexplorers.event.Event;
import com.mikeandliam.spaceexplorers.event.EventType;

public class CrewMember {
    /**
     * the type/race of this crew member
     */
    private CrewMemberType type;

    private String name;
    //from 0 to 100
    private int health = 100;
    //from 0 to 100
    private int hunger = 0;
    //from 0 to 100
    private int tiredness = 0;

    //gets reset to 2 at the start of each day
    private int actionsRemaining = 2;

    private boolean hasSpacePlague = false;

    //event for whenever something regarding this crew member happens
    private Event<CrewEventArgs> crewEvent = new Event<>();

    private Event<Void> statusUpdatedEvent = new Event<>();

    /**
     * @param type the type of this CrewMember
     * @param name this CrewMember's name
     */
    public CrewMember(CrewMemberType type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * degrades health, hunger, and tiredness based on the stats of this crew's type
     *
     * @return whether the crew member survived the night
     */
    public boolean nextDay() {
        actionsRemaining = 2;
        health -= type.getHealthDegradation();
        hunger += type.getHungerDegradation();
        tiredness += type.getTirednessDegradation();

        if (hasSpacePlague) {
            health -= 10;
            crewEvent.invoke(new CrewEventArgs(EventType.ERROR, this, "Lost 10 health due to space plague."));
        }

        statusUpdatedEvent.invoke(null);

        if (health > 0) {
            return true;
        } else {
            crewEvent.invoke(new CrewEventArgs(EventType.ERROR, this, "Died in the night RIP."));
            return false;
        }
    }

    /**
     * @return the type of this CrewMember
     */
    public CrewMemberType getType() {
        return type;
    }

    /**
     * @param amount amount of health to be added to this crew's total health
     */
    public void restoreHealth(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }

        statusUpdatedEvent.invoke(null);
        crewEvent.invoke(new CrewEventArgs(EventType.INFO, this, "health now at " + health));
    }


    /**
     * @param nutritionalValue The CrewMember's hunger is decreased by this amount
     */
    public void restoreHunger(int nutritionalValue) {
        hunger -= nutritionalValue;
        if (hunger < 0) {
            hunger = 0;
        }

        statusUpdatedEvent.invoke(null);
        crewEvent.invoke(new CrewEventArgs(EventType.INFO, this, "hunger now at " + hunger));
    }

    /**
     * Reset's this CrewMember's tiredness to zero.
     */
    public void sleep() {
        tiredness = 0;
        statusUpdatedEvent.invoke(null);
        crewEvent.invoke(new CrewEventArgs(EventType.INFO, this, "slept."));
    }

    /**
     * called when a crew action is attempted
     * decrements the crew's action remaining.
     *
     * @return whether the action can be performed
     */
    public boolean tryPerformAction() {
        if (actionsRemaining > 0) {
            actionsRemaining--;
            statusUpdatedEvent.invoke(null);
            return true;
        } else {
            crewEvent.invoke(new CrewEventArgs(EventType.ERROR, this, "Not enough actions left."));
            return false;
        }
    }

    /**
     * @return the health of this CrewMember. Ranges from 0 to 100 (full health)
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return the hunger of this CrewMember. Ranges from 0 (not hungry at all) to 100
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * @return the tiredness of this CrewMember. Ranges from 0 (not tired at all) to 100
     */
    public int getTiredness() {
        return tiredness;
    }

    /**
     * @return The number of actions this CrewMember can take before running out of actions
     */
    public int getActionsRemaining() {
        return actionsRemaining;
    }

    /**
     * @param actionsRemaining the number of actions this CrewMember can take before running out of actions
     */
    public void setActionsRemaining(int actionsRemaining) {
        statusUpdatedEvent.invoke(null);
        this.actionsRemaining = actionsRemaining;
    }

    /**
     * @return the name of this CrewMember
     */
    public String getName() {
        return name;
    }

    /**
     * an Event that fires whenever something of note happens to this crew member.
     * an EventHandler can be added to this event using member.getCrewEvent().addhandler(args -> { <handler code> })
     * this handler will be notified of anything happening to this crew member.
     * For example: getting the space plague, restoring health, sleeping, etc will fire this event
     */
    public Event<CrewEventArgs> getCrewEvent() {
        return crewEvent;
    }

    /**
     *  An event that is fired whenever the status (health, tiredness, hunger, etc) is updated.
     *  Add a handler to this event if you need to be notified when this happens.
     *  (For example to re-draw the crew stats display in the gui when stats are updated)
     */
    public Event<Void> getStatusUpdatedEvent() {
        return statusUpdatedEvent;
    }

    /**
     * @return whether this crew member has the space plague
     */
    public boolean getHasSpacePlague() {
        return hasSpacePlague;
    }

    /**
     * @param hasSpacePlague whether this crew should contract (true) or be cured of (false) the space plague.
     */
    public void setHasSpacePlague(boolean hasSpacePlague) {
        this.hasSpacePlague = hasSpacePlague;
        statusUpdatedEvent.invoke(null);

        if (hasSpacePlague) {
            crewEvent.invoke(new CrewEventArgs(EventType.ERROR, this, "Now has the space plague."));
        } else {
            crewEvent.invoke(new CrewEventArgs(EventType.INFO, this, "Has been cured of the space plague."));
        }
    }
}
