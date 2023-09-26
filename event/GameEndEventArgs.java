package com.mikeandliam.spaceexplorers.event;

public class GameEndEventArgs {
    private String shipName;
    private int daysToComplete;
    private boolean allPartsFound;
    private int Score;

    /**
     *
     * @param shipName name of ship in game
     * @param daysToComplete the amount of days taken to complete the game
     * @param allPartsFound true if all required transporter parts were found in the game
     * @param score the players score on game completion
     */
    public GameEndEventArgs(String shipName, int daysToComplete, boolean allPartsFound, int score) {
        this.shipName = shipName;
        this.daysToComplete = daysToComplete;
        this.allPartsFound = allPartsFound;
        Score = score;
    }

    /**
     * @return the name of ship in the game
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * @return the days the player took to complete the game
     */
    public int getDaysToComplete() {
        return daysToComplete;
    }

    /**
     * @return true if all required transporter parts were found in the game
     */
    public boolean getAllPartsFound() {
        return allPartsFound;
    }

    /**
     * @return the players score on game completion
     */
    public int getScore() {
        return Score;
    }
}
