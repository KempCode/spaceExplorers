package com.mikeandliam.spaceexplorers.gui;

import javax.swing.*;
import java.awt.*;

/**
 * The title screen
 */
public class StartScreen implements GameScreen {
    private JPanel rootPanel;
    private JButton startButton;
    private JButton quitButton;
    private JSlider daysSlider;
    private JLabel daysValueLabel;

    public StartScreen(ScreenManager manager) {

        startButton.addActionListener(event ->
                //go to the next screen
                manager.changeScreen(new SetupScreen(manager, daysSlider.getValue()))
        );

        quitButton.addActionListener(event -> {
            manager.getFrame().setVisible(false);
            manager.getFrame().dispose();
        });

        daysSlider.addChangeListener(event ->
                daysValueLabel.setText(String.valueOf(daysSlider.getValue()) + " days.")
        );
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }

}
