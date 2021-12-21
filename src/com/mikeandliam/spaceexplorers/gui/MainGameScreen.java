package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.CrewMember;
import com.mikeandliam.spaceexplorers.GameEnvironment;
import com.mikeandliam.spaceexplorers.Resources;
import com.mikeandliam.spaceexplorers.event.CrewEventArgs;
import com.mikeandliam.spaceexplorers.event.EnvironmentEventArgs;
import com.mikeandliam.spaceexplorers.event.EventType;
import com.mikeandliam.spaceexplorers.inventory.CrewUsableInventoryItem;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Main screen when playing the game
 */
public class MainGameScreen implements GameScreen {
    private JPanel rootPanel;
    private JButton buyItemsButton;
    private JButton nextPlanetButton;
    private JButton nextDayButton;
    private JPanel crewPanel;
    private JLabel shipIconLabel;
    private JPanel notificationsPanel;
    private JScrollPane notificationsScrollPane;
    private JProgressBar shieldStrengthBar;
    private JPanel inventoryPanel;
    private JLabel shipNameLabel;
    private JLabel foundPartsLabel;

    private InventoryDisplayPanel inventoryDisplay;

    private ScreenManager manager;
    private GameEnvironment environment;

    public MainGameScreen(ScreenManager manager, GameEnvironment environment) {
        this.manager = manager;
        this.environment = environment;

        inventoryDisplay = new InventoryDisplayPanel(environment.getInventory(), manager);

        initializeUI();
        initializeListeners();

        // wire up events to show in the notifications box
        environment.getCrewEvent().addHandler(this::addCrewNotification);
        environment.getEnvironmentEvent().addHandler(this::addEnvironmentNotification);
    }

    private void initializeListeners() {
        nextDayButton.addActionListener(e -> environment.nextDay(true));

        nextPlanetButton.addActionListener(e -> {
            NextPlanetScreen planetScreen = new NextPlanetScreen(manager, environment, this);

            planetScreen.getOnSelectPilotEvent().addHandler(args ->
                    environment.crewPilotToNewPlanet(args.getPilot1(), args.getPilot2())
            );

            manager.changeScreen(planetScreen);
        });

        buyItemsButton.addActionListener(e -> manager.changeScreen(new BuyItemsScreen(manager, environment, this)));

        environment.getShip().getOnShieldStrengthChangeEvent()
                .addHandler(e -> shieldStrengthBar.setValue(environment.getShip().getShieldHealth()));

        environment.getGameEndEvent().addHandler(e -> manager.changeScreen(new EndScreen(manager, e)));
    }

    private void initializeUI() {
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));

        shipNameLabel.setText("Ship: " + environment.getShip().getName());


        crewPanel.setLayout(new BoxLayout(crewPanel, BoxLayout.Y_AXIS));

        for (CrewMember crewMember : environment.getCrew()) {
            crewPanel.add(new CrewDisplayPanel(crewMember, environment, this).getRootContainer());
        }

        inventoryPanel.add(inventoryDisplay.getRootContainer());

        updateFoundPartsLabel();
    }

    /**
     * Add a notification to the notification box
     *
     * @param icon icon to display next to notification
     * @param text the notification text
     * @param type INFO or ERROR, the notification/event type (errors displayed in red)
     */
    private void addNotification(ImageIcon icon, String text, EventType type) {
        Box newPanel = Box.createHorizontalBox();
        newPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

        JLabel iconLabel = new JLabel("");
//        iconLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));
        iconLabel.setIcon(icon);
//        icon.setIconTextGap(5);
        newPanel.add(iconLabel);


        JLabel textLabel = new JLabel(text);
        switch (type) {
            case INFO:
                textLabel.setForeground(Color.BLACK);
                break;
            case ERROR:
                textLabel.setForeground(Color.RED);
                break;
        }
        newPanel.add(textLabel);

        newPanel.add(Box.createHorizontalGlue());

        notificationsPanel.add(newPanel);

        //just in case
        inventoryDisplay.refreshItems();
        updateFoundPartsLabel();

        manager.getFrame().pack();

        JScrollBar verticalScrollBar = notificationsScrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    /**
     * Adds notification based on CrewEventArgs
     *
     * @param args the CrewEventArgs to construct a notification from
     */
    private void addCrewNotification(CrewEventArgs args) {
        String text = String.format("%s: %s", args.getMember().getName(), args.getText());
        addNotification(args.getMember().getType().getIcon(), text, args.getType());
    }

    /**
     * Adds notification based on EnvironmentEventArgs
     *
     * @param args the EnvironmentEventArgs to construct the notification from
     */
    private void addEnvironmentNotification(EnvironmentEventArgs args) {
        ImageIcon icon = null;
        switch (args.getType()) {
            case INFO:
                icon = Resources.INFO_ICON;
                break;
            case ERROR:
                icon = Resources.ERROR_ICON;
                break;
        }
        addNotification(icon, args.getText(), args.getType());
    }

    public void selectItemToUse(CrewMember member) {
        inventoryDisplay.setCurrentlySelecting(true);
        inventoryDisplay.setEnabledFilter(item -> item instanceof CrewUsableInventoryItem);
        inventoryDisplay.refreshItems();

        inventoryDisplay.getOnItemSelectedEvent().addHandler(selectedItem -> {
            inventoryDisplay.setCurrentlySelecting(false);
            inventoryDisplay.getOnItemSelectedEvent().clearHandlers();

            // we can safely cast here because inventoryDisplay has already filtered enabled items by
            // item -> item instanceof CrewUsableInventoryItem
            environment.crewUseItem(member, (CrewUsableInventoryItem) selectedItem);

            //make all items enabled again once the crew item is selected
            inventoryDisplay.setEnabledFilter(item -> true);
            inventoryDisplay.refreshItems();
        });
    }

    private void updateFoundPartsLabel() {
        foundPartsLabel.setText(String.format(
                "Found %d of %d transporter parts.",
                environment.getNumTransporterPartsFound(),
                environment.getNumTransporterPartsRequired()
        ));
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }
}
