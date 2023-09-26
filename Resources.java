package com.mikeandliam.spaceexplorers;

import javax.swing.*;

/**
 * Contains all the image files for the GUI.
 * They are consolidated here to that images are type-safe throughout the GUI code (ie. not specified by a string)
 */
public class Resources {
    public static ImageIcon INFO_ICON = getIcon("resources/infoIcon.png");
    public static ImageIcon ERROR_ICON = getIcon("resources/errorIcon.png");

    public static ImageIcon ANAESTHETIC = getIcon("resources/anaesthetic.png");
    public static ImageIcon APPLE = getIcon("resources/apple.png");
    public static ImageIcon BANANA = getIcon("resources/banana.png");
    public static ImageIcon BANDAID = getIcon("resources/bandaid.png");
    public static ImageIcon PIZZA = getIcon("resources/bizza.png");
    public static ImageIcon CARROT = getIcon("resources/carrot.png");
    public static ImageIcon CBD = getIcon("resources/cbd.png");
    public static ImageIcon NACHOES = getIcon("resources/nachos.png");
    public static ImageIcon VODKA = getIcon("resources/vodka.png");
    public static ImageIcon CURE = getIcon("resources/cure.png");
    public static ImageIcon TRANSPORTER = getIcon("resources/transporter.png");

    public static ImageIcon ALIEN = getIcon("resources/alien.png");
    public static ImageIcon CAPTAIN = getIcon("resources/captain.png");
    public static ImageIcon COWBOY = getIcon("resources/cowboy.png");
    public static ImageIcon MONKEY = getIcon("resources/monkey.png");
    public static ImageIcon MULLET = getIcon("resources/mullet.png");
    public static ImageIcon PYGMY = getIcon("resources/pygmy.png");
    public static ImageIcon JEREMY = getIcon("resources/jeremy.png");


    /**
     * loads an ImageIcon from a resource path using java's ResourceLoader
     */
    private static ImageIcon getIcon(String path) {
        return new ImageIcon(Resources.class.getResource(path), "Description here");
    }
}
