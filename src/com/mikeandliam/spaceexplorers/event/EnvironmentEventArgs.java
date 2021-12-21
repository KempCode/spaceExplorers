package com.mikeandliam.spaceexplorers.event;

/**
 * The info stored in this class is passed on to the UI or commandline when an environment event happens
 */
public class EnvironmentEventArgs {
    //INFO or ERROR
    private EventType type;
    private String text;

    public EnvironmentEventArgs(EventType type, String text) {
        this.type = type;
        this.text = text;
    }

    /**
     * @return the type of event that happened
     */
    public EventType getType() {
        return type;
    }

    /**
     * @return text describing this event
     */
    public String getText() {
        return text;
    }
}
