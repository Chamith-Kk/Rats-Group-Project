package com.example.gamestate;

import java.util.Comparator;

/**
 * Contains information directly relating to the player and their profile.
 *
 * @author Anthony Stafford
 * @version 1.0
 *
 */
public class Player implements Comparator<Player> {
    /**
     *
     */
    private String name;
    /**
     *
     */
    private int maxLevel;

    /**
     * @param name
     * @param maxLevel
     */
    public Player(String name, int maxLevel) {
        this.name = name;
        this.maxLevel = maxLevel;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return max level
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     *
     * @param maxLevel
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     *
     * @return
     */
    public String toString() {
        return name + " - Max Level: " + maxLevel;
    }

	@Override
	public int compare(Player p1, Player p2) {
		if (p2.getMaxLevel() > p1.getMaxLevel()) {
			return 1;
		} else if (p2.getMaxLevel() < p1.getMaxLevel()) {
			return -1;
		} else {
			return 0;
		}
	}

}
