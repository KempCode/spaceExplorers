package com.mikeandliam.spaceexplorers.gui;

import java.awt.*;

/**
 * This is a custom version of the swing CardLayout
 * Normally, CardLayout sizes itself to the size of its largest card
 *
 * This version instead sizes itself to the size of the currently displayed card
 * This means the layout will change size every time the card is changed
 */
public class ResizingCardLayout extends CardLayout {
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Component currentlyDisplayedCard = null;

        // look through all cards and find the one that is currently visible (null if none visible)
        for (Component card : parent.getComponents()) {
            if (card.isVisible()) {
                currentlyDisplayedCard = card;
                break;
            }
        }

        // if there is a card currently being displayed,
        // resize the card layout to be the size of that card
        if (currentlyDisplayedCard != null) {
            return currentlyDisplayedCard.getPreferredSize();
        }

        // otherwise just let swing decide the layout size
        return super.preferredLayoutSize(parent);
    }
}
