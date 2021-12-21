package com.mikeandliam.spaceexplorers.gui;

import com.mikeandliam.spaceexplorers.event.Event;
import com.mikeandliam.spaceexplorers.inventory.InventoryItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * Custom component that displays the player's inventory
 */
public class InventoryDisplayPanel implements CustomComponent {
    private JPanel itemsPanel;
    private JPanel rootPanel;
    private JLabel titleLabel;

    private ScreenManager manager;
    private ArrayList<InventoryItem> items;
    private boolean showPrices;
    private boolean isCurrentlySelecting = false;
    private Function<InventoryItem, Boolean> enabledFilter = inventoryItem -> true;
    private Event<InventoryItem> onItemSelectedEvent = new Event<>();

    public InventoryDisplayPanel(ArrayList<InventoryItem> items, ScreenManager manager) {
        this(items, manager, false);
    }

    public InventoryDisplayPanel(ArrayList<InventoryItem> items, ScreenManager manager, boolean showPrices) {
        this.items = items;
        this.manager = manager;
        this.showPrices = showPrices;
        refreshItems();
    }

    public void refreshItems() {
        itemsPanel.removeAll();
        itemsPanel.setLayout(new GridLayout(items.size() / 4 + 1, 4));

        for (InventoryItem inventoryItem : items) {
            JButton itemButton = new JButton("");

            if(showPrices) {
                itemButton.setText("$" + String.valueOf(inventoryItem.getPrice()));
            }

            itemButton.setIcon(inventoryItem.getIcon());
            itemButton.setEnabled(enabledFilter.apply(inventoryItem));
            itemButton.setToolTipText(String.format(
                    "<html><b>%s</b><br>%s<br>Costs $%s</html>",
                    inventoryItem.getDisplayName(),
                    inventoryItem.getDescription(),
                    inventoryItem.getPrice()
            ));

            if (isCurrentlySelecting) {
                itemButton.addActionListener(e -> onItemSelectedEvent.invoke(inventoryItem));
            }


            itemsPanel.add(itemButton);
        }

        if (isCurrentlySelecting) {
            titleLabel.setText("Select Item:");
            titleLabel.setForeground(Color.CYAN);
        } else {
            titleLabel.setText("Inventory");
            titleLabel.setForeground(Color.black);
        }

        manager.getFrame().pack();
    }

    public void setCurrentlySelecting(boolean currentlySelecting) {
        isCurrentlySelecting = currentlySelecting;
    }

    public boolean isCurrentlySelecting() {
        return isCurrentlySelecting;
    }

    public void setEnabledFilter(Function<InventoryItem, Boolean> enabledFilter) {
        this.enabledFilter = enabledFilter;
    }

    public Event<InventoryItem> getOnItemSelectedEvent() {
        return onItemSelectedEvent;
    }

    @Override
    public Container getRootContainer() {
        return rootPanel;
    }
}
