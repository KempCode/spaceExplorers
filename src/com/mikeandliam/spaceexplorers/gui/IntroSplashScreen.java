package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.GameEnvironment;
import com.mikeandliam.spaceexplorers.gui.GameScreen;
import com.mikeandliam.spaceexplorers.gui.MainGameScreen;
import com.mikeandliam.spaceexplorers.gui.ScreenManager;

import javax.swing.*;
import java.awt.*;

/**
 * intro screen shown when starting a game
 */
public class IntroSplashScreen implements GameScreen {
    private JPanel rootPanel;
    private JLabel introTextLabel;
    private JButton continueButton;

    public IntroSplashScreen(ScreenManager manager, GameEnvironment environment) {
        introTextLabel.setText(GameEnvironment.startText);

        continueButton.addActionListener(actionEvent -> manager.changeScreen(new MainGameScreen(manager, environment)));
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }
}
