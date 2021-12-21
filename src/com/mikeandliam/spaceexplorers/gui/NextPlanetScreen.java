package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.GameEnvironment;
import com.mikeandliam.spaceexplorers.event.Event;

import javax.swing.*;
import java.awt.*;

/**
 * Screen that prompts the player to select two pilots to go to the next planet
 */
public class NextPlanetScreen implements GameScreen {
    private JButton cancelButton;
    private JPanel rootPanel;

    private CrewMember pilot1 = null;

    private Event<SelectPilotEventArgs> onSelectPilotEvent = new Event<>();


    public NextPlanetScreen(ScreenManager manager, GameEnvironment environment, MainGameScreen mainGameScreen) {
        for (CrewMember crewMember : environment.getCrew()) {
            JButton crewButton = new JButton(crewMember.getName(), crewMember.getType().getIcon());
            crewButton.setEnabled(crewMember.getActionsRemaining() > 0);

            crewButton.addActionListener(e -> {
                if (pilot1 == null) {
                    pilot1 = crewMember;
                    crewButton.setEnabled(false);
                } else {
                    onSelectPilotEvent.invoke(new SelectPilotEventArgs(pilot1, crewMember));
                    manager.changeScreen(mainGameScreen);
                }
            });

            rootPanel.add(crewButton);

        }

        cancelButton.addActionListener(e -> manager.changeScreen(mainGameScreen));
    }

    /**
     * The event that fires when the two pilots are selected
     */
    public Event<SelectPilotEventArgs> getOnSelectPilotEvent() {
        return onSelectPilotEvent;
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }
}
