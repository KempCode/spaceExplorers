package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.CrewMember;

import javax.swing.*;
import java.awt.*;

/**
 * Only displays crew icon and name
 *
 * for use when selecting crew in SetupScreen
 */
public class BasicCrewDisplayPanel implements CustomComponent {
    private JLabel nameLabel;
    private JLabel iconLabel;
    private JPanel rootPanel;


    public BasicCrewDisplayPanel(CrewMember crewMember) {
        nameLabel.setText(crewMember.getName());
        iconLabel.setText("");
        iconLabel.setIcon(crewMember.getType().getIcon());
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }

}
