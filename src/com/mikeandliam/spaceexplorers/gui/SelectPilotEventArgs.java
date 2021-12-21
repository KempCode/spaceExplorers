package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.CrewMember;

/**
 * The args passed by the event that is fired when pilots are selected by the NextPlanetScreen
 */
public class SelectPilotEventArgs {
    private CrewMember pilot1;
    private CrewMember pilot2;

    public SelectPilotEventArgs(CrewMember pilot1, CrewMember pilot2) {
        this.pilot1 = pilot1;
        this.pilot2 = pilot2;
    }

    public CrewMember getPilot1() {
        return pilot1;
    }

    public CrewMember getPilot2() {
        return pilot2;
    }
}
