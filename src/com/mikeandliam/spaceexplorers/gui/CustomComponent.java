package com.mikeandliam.spaceexplorers.gui;

import java.awt.*;

/**
 * Custom components are their own class (that implements this interface)
 *
 * They must also provide a root container,
 * which is a Java Swing container that contains all of UI components of this class.
 */
public interface CustomComponent {
    Container getRootContainer();
}
