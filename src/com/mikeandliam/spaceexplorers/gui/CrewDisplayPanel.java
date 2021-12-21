package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.GameEnvironment;

import javax.swing.*;
import java.awt.*;

/**
 * A custom component that displays crew icon, name, and stats
 */
public class CrewDisplayPanel implements CustomComponent {
    private JPanel rootPanel;
    private JLabel iconLabel;
    private JLabel nameLabel;
    private JProgressBar healthBar;
    private JProgressBar hungerBar;
    private JProgressBar tirednessBar;
    private JProgressBar actionsRemainingBar;
    private JButton actionButton;
    private JCheckBox hasSpacePagueCheckBox;


    private CrewMember crewMember;
    private GameEnvironment environment;
    private MainGameScreen mainGameScreen;

    public CrewDisplayPanel(CrewMember crewMember, GameEnvironment environment, MainGameScreen mainGameScreen) {
        this.crewMember = crewMember;
        this.environment = environment;
        this.mainGameScreen = mainGameScreen;

        initializeUI();
    }

    private void initializeUI() {
        iconLabel.setText("");
        iconLabel.setIcon(crewMember.getType().getIcon());
        nameLabel.setText(crewMember.getName());

        initializePopupMenu();

        crewMember.getStatusUpdatedEvent().addHandler(e -> updateStats());
        updateStats();
    }

    private void initializePopupMenu() {
        JPopupMenu menu = new JPopupMenu("Crew Actions");

        JMenuItem useItemMenuItem = new JMenuItem("Use item from inventory");
        useItemMenuItem.addActionListener(e -> mainGameScreen.selectItemToUse(crewMember));
        menu.add(useItemMenuItem);

        JMenuItem sleepMenuItem = new JMenuItem("Sleep");
        sleepMenuItem.addActionListener(e -> environment.crewSleep(crewMember));
        menu.add(sleepMenuItem);

        JMenuItem repairShieldsMenuItem = new JMenuItem("Repair Shields");
        repairShieldsMenuItem.addActionListener(e -> environment.crewRepairShields(crewMember));
        menu.add(repairShieldsMenuItem);

        JMenuItem searchPlanetMenuItem = new JMenuItem("Search planet for spaceship parts");
        searchPlanetMenuItem.addActionListener(e -> environment.crewSearchForParts(crewMember));
        menu.add(searchPlanetMenuItem);

        actionButton.addActionListener(actionEvent -> menu.show(actionButton, 0, 0));
    }

    private void updateStats() {
        healthBar.setValue(crewMember.getHealth());
        hungerBar.setValue(crewMember.getHunger());
        tirednessBar.setValue(crewMember.getTiredness());
        actionsRemainingBar.setValue(crewMember.getActionsRemaining());
        hasSpacePagueCheckBox.setSelected(crewMember.getHasSpacePlague());
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }
}
