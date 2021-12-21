package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.event.GameEndEventArgs;

import javax.swing.*;
import java.awt.*;

/**
 * Screen displayed when the game ends
 */
public class EndScreen implements GameScreen{
    private JPanel rootPanel;
    private JTextField shipNameField;
    private JTextField daysToCompleteField;
    private JCheckBox allPiecesFoundCheckBox;
    private JTextField scoreField;
    private JButton mainMenuButton;

    public EndScreen(ScreenManager manager, GameEndEventArgs gameEndEventArgs) {
        shipNameField.setText(gameEndEventArgs.getShipName());
        daysToCompleteField.setText(String.valueOf(gameEndEventArgs.getDaysToComplete()));
        allPiecesFoundCheckBox.setSelected(gameEndEventArgs.getAllPartsFound());
        scoreField.setText(String.valueOf(gameEndEventArgs.getScore()));

        mainMenuButton.addActionListener(e -> manager.changeScreen(new StartScreen(manager)));
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }
}
