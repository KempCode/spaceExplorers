package com.mikeandliam.spaceexplorers.event;

import com.mikeandliam.spaceexplorers.CrewMember;

/**
 * The info stored in this class is passed from the GameEnvironment to either the UI or commandline when a crew event happens
 */
public class CrewEventArgs {
    //either INFO or ERROR
    private EventType type;
    //the crew member that caused the event
    private CrewMember member;

    private String text;

    /**
     * @param type the type of event
     * @param member the CrewMember that triggered this event
     * @param text text describing the event
     */
    public CrewEventArgs(EventType type, CrewMember member, String text) {
        this.type = type;
        this.member = member;
        this.text = text;
    }

    /**
     * @return the type of event that is being fired (INFO or ERROR)
     */
    public EventType getType() {
        return type;
    }

    /**
     * @return the member that caused this event
     */
    public CrewMember getMember() {
        return member;
    }

    /**
     * @return Text describing the event
     */
    public String getText() {
        return text;
    }
}
