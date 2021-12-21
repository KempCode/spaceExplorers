package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Screen that allows the player to create a game with custom crew, ship, etc.
 */
public class SetupScreen implements GameScreen {
    private ScreenManager manager;

    //UI bindings:
    private JPanel rootPanel;
    private JPanel crewTypesPanel;
    private JPanel crewListPanel;
    private JTextField shipNameTextField;
    private JLabel crewEmptyLabel;
    private JButton startGameButton;
    private JLabel shipDisplayIconLabel;

    private int dayCount;
    private ArrayList<CrewMember> currentCrew = new ArrayList<>();

    // we keep a list of all the add crew member buttons so that when the crew number limit has been reached
    // they can all be disabled
    private ArrayList<JButton> addCrewButtons = new ArrayList<>();

    public SetupScreen(ScreenManager manager, int dayCount) {
        this.manager = manager;
        this.dayCount = dayCount;

        initialiseUI();
    }

    private void initialiseUI() {
        for (CrewMemberType type : CrewMemberType.values()) {
            CrewTypeStatsDisplayPanel newCrewStatsDisplay = new CrewTypeStatsDisplayPanel(type);
            addCrewButtons.add(newCrewStatsDisplay.getAddButton());
            newCrewStatsDisplay.getAddButton().addActionListener(actionEvent -> {
                Optional<String> maybeName = CrewNameDialog.getCrewName(newCrewStatsDisplay.getAddButton());
                maybeName.ifPresent(name -> {
                    //if a new crew member has been added
                    addCrew(new CrewMember(type, name));

                    //disable adding any more crew if the limit has been met
                    if (currentCrew.size() >= 4) {
                        for (JButton button : addCrewButtons) {
                            button.setEnabled(false);
                        }
                    }
                });
            });
            crewTypesPanel.add(newCrewStatsDisplay.getRootContainer());
        }

        startGameButton.addActionListener(e -> {
                    if (currentCrew.size() < 2) {
                        JOptionPane.showMessageDialog(this.rootPanel, "Must select at least 2 Crew.");
                    } else {
                        Ship ship = new Ship(shipNameTextField.getText());
                        GameEnvironment environment = new GameEnvironment(currentCrew, ship, dayCount);

                        manager.changeScreen(new IntroSplashScreen(manager, environment));
                    }
                }
        );

    }

    private void addCrew(CrewMember crewMember) {
        crewEmptyLabel.setVisible(false);

        currentCrew.add(crewMember);

        crewListPanel.add(new BasicCrewDisplayPanel(crewMember).getRootContainer());
        manager.getFrame().pack();
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }

}
