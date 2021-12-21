package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.GameEnvironment;

import javax.swing.*;
import java.awt.*;

/**
 * A Screen that lets the player buy items from a space outpost
 */
public class BuyItemsScreen implements GameScreen {
    private JPanel forSaleInventoryPanel;
    private JPanel playerInventoryPanel;
    private JPanel rootPanel;
    private JButton doneButton;
    private JTextField moneyTextField;

    private InventoryDisplayPanel myInventory;
    private InventoryDisplayPanel planetInventory;
    private GameEnvironment environment;

    public BuyItemsScreen(ScreenManager manager, GameEnvironment environment, MainGameScreen mainGameScreen) {
        this.environment = environment;

        myInventory = new InventoryDisplayPanel(environment.getInventory(), manager);
        playerInventoryPanel.add(myInventory.getRootContainer());

        planetInventory = new InventoryDisplayPanel(environment.getCurrentPlanet().getItemsForSale(), manager, true);
        forSaleInventoryPanel.add(planetInventory.getRootContainer());

        planetInventory.setCurrentlySelecting(true);

        planetInventory.getOnItemSelectedEvent().addHandler(item -> {
            environment.getCurrentPlanet().attemptPurchase(item);

            refresh();
        });

        doneButton.addActionListener(actionEvent -> manager.changeScreen(mainGameScreen));

        refresh();
    }

    private void refresh() {
        moneyTextField.setText(String.valueOf(environment.getMoney()));

        planetInventory.setEnabledFilter(item -> item.getPrice() <= environment.getMoney());
        planetInventory.refreshItems();
        myInventory.refreshItems();
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }
}
