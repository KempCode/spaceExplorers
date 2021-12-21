package com.mikeandliam.spaceexplorers.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Manages the different screens of the game.
 * Each screen must implement GameScreen. the screen can be changed by calling changeScreen(newScreen)
 */
public class GuiManager implements ScreenManager {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private int gameScreenNameCounter = 0;
    private HashMap<GameScreen, String> gameScreenNamesMap = new HashMap<>();

    /**
     * Open application window and start the game
     */
    public void run() {

////        set nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }



        GameScreen startScreen = new StartScreen(this);

        frame = new JFrame("Space Explorers");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Resources.CBD.getImage(), new Point(0, 0), "cursor"));
        frame.setResizable(false);

        // make tooltips
        ToolTipManager.sharedInstance().setInitialDelay(0);

        cardLayout = new ResizingCardLayout();
        mainPanel = new JPanel(cardLayout);

        frame.setContentPane(mainPanel);

        //start with startScreen
        changeScreen(startScreen);

        frame.setVisible(true);
    }

    @Override
    public void changeScreen(GameScreen newScreen) {
        if (!gameScreenNamesMap.containsKey(newScreen)) {
            String newScreenName = String.valueOf(gameScreenNameCounter);
            gameScreenNameCounter++;
            gameScreenNamesMap.put(newScreen, newScreenName);
            mainPanel.add(newScreen.getRootContainer());
            cardLayout.addLayoutComponent(newScreen.getRootContainer(), newScreenName);
        }

        cardLayout.show(mainPanel, gameScreenNamesMap.get(newScreen));
        frame.pack();
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }
}
