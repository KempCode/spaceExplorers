package com.mikeandliam.spaceexplorers;

import javax.swing.*;
import java.util.function.Function;

/**
 * The different types the crew members can have.
 * Each value of this enum is a type that CrewMembers can have.
 */
public enum CrewMemberType {
    CONGOLESE_PYGMY_WARRIOR("Congolese Pygmy Warrior", 10, 5, 5, 5, 0, Resources.PYGMY),
    COWBOY("Cowboy", 5, 5, 5, 5, 100, Resources.COWBOY),
    ALIEN("Illegal Alien", 5, 5, 5, 5, 5, Resources.ALIEN),
    MONKEY("Monkey", 5, 5, 5, 5, 5, Resources.MONKEY),
    MULLET("Mullet man", 5, 5, 5, 5, 5, Resources.MULLET),
    CAPTAIN("Captain Ben", 5, 5, 5, 5, 5, Resources.CAPTAIN),
    JEREMY("Jeremy", 5, 5, 5, 5, 5, Resources.JEREMY);




    private String displayName;
    private int healthDegradation;
    private int hungerDegradation;
    private int tirednessDegradation;
    private int repairSkillLevel;
    private int searchSkillLevel;

    private ImageIcon icon;

    CrewMemberType(String displayName,
                   int healthDegradation,
                   int hungerDegradation,
                   int tirednessDegradation,
                   int repairSkillLevel,
                   int searchSkillLevel,
                   ImageIcon icon) {
        this.displayName = displayName;
        this.healthDegradation = healthDegradation;
        this.hungerDegradation = hungerDegradation;
        this.tirednessDegradation = tirednessDegradation;
        this.repairSkillLevel = repairSkillLevel;
        this.searchSkillLevel = searchSkillLevel;
        this.icon = icon;
    }

    /**
     * returns the maximum value (between all crew types) of a certain statistic
     * A statistic can be healthDegradation, tiredness, etc.
     *
     * @param getStatLambda a lambda that takes a crew type and returns the stat
     *                      For example, to find the max repairSkill, pass in CrewMemberType::getRepairSkill
     * @return the max value of the requested statistic across all crew types
     * <p>
     * eg.
     * to get max healthDegradation across all types:
     * CrewMemberType.getStatMax(CrewMemberType::getHealthDegradation)
     */
    public static int getStatMax(Function<CrewMemberType, Integer> getStatLambda) {
        int max = 0;
        for (CrewMemberType type : CrewMemberType.values()) {
            int crewStat = getStatLambda.apply(type);
            if (crewStat > max) {
                max = crewStat;
            }
        }
        return max;
    }

    /**
     * @return the name of this type (NOTE: This is the name of the TYPE, not the name of the crew member themself)
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return The amount of health CrewMembers with this type will lose each day
     */
    public int getHealthDegradation() {
        return healthDegradation;
    }

    /**
     * @return The amount of health CrewMembers with this type will gain each day
     */
    public int getHungerDegradation() {
        return hungerDegradation;
    }

    /**
     * @return The amount of tiredness CrewMembers with this type will gain each day
     */
    public int getTirednessDegradation() {
        return tirednessDegradation;
    }

    /**
     * @return the skill CrewMembers of this type have at repairing the ship. Determines how much the ship is repaired by
     */
    public int getRepairSkillLevel() {
        return repairSkillLevel;
    }

    /**
     * @return The skill CrewMembers of this type have at searching for items. Determines how likely they are to find an item.
     */
    public int getSearchSkillLevel() {
        return searchSkillLevel;
    }

    /**
     * @return The icon for this type. This icon is also used by any CrewMembers of this type
     */
    public ImageIcon getIcon() {
        return icon;
    }
}
