package com.mikeandliam.spaceexplorers.gui;

import javax.swing.*;

/**
 * Something that manages GameScreens.
 */
public interface ScreenManager {
    /**
     * Hide the current screen and show newScreen
     */
    void changeScreen(GameScreen newScreen);

    /**
     * The main JFrame object of the game can be retrieved using this method
     *
     * can be used to repack the UI, etc.
     */
    JFrame getFrame();
}
