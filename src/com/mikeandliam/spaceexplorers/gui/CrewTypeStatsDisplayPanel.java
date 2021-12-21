package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.CrewMemberType;

import javax.swing.*;
import java.awt.*;

/**
 * This is a panel that displays the crew type's name, icon and stats display.
 * Also includes an 'add' button that is used to add this crew type to the player's crew.
 *
 * used in SetupScreen when selecting crew types to add to your crew
 */
public class CrewTypeStatsDisplayPanel implements CustomComponent {
    private JLabel typeNameLabel;
    private JLabel typeIconLabel;
    private JProgressBar resilienceProgressBar;
    private JProgressBar hungerProgressBar;
    private JProgressBar sleepinessProgressBar;
    private JButton addButton;
    private JPanel rootPanel;
    private JProgressBar searchAbilityBar;
    private JProgressBar repairAbilityBar;

    public CrewTypeStatsDisplayPanel(CrewMemberType type) {
        typeNameLabel.setText(type.getDisplayName());

        typeIconLabel.setText("");
        typeIconLabel.setIcon(type.getIcon());

        resilienceProgressBar.setMaximum(CrewMemberType.getStatMax(CrewMemberType::getHealthDegradation));
        resilienceProgressBar.setValue(type.getHealthDegradation());

        hungerProgressBar.setMaximum(CrewMemberType.getStatMax(CrewMemberType::getHungerDegradation));
        hungerProgressBar.setValue(type.getHungerDegradation());

        sleepinessProgressBar.setMaximum(CrewMemberType.getStatMax(CrewMemberType::getTirednessDegradation));
        sleepinessProgressBar.setValue(type.getTirednessDegradation());

        searchAbilityBar.setMaximum(CrewMemberType.getStatMax(CrewMemberType::getSearchSkillLevel));
        searchAbilityBar.setValue(type.getSearchSkillLevel());

        repairAbilityBar.setMaximum(CrewMemberType.getStatMax(CrewMemberType::getRepairSkillLevel));
        repairAbilityBar.setValue(type.getRepairSkillLevel());


    }

    /**
     * @return the 'Add' button below each crew type
     */
    public JButton getAddButton() {
        return addButton;
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }

}
