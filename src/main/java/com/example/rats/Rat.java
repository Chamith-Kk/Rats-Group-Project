package com.example.rats;

import java.util.List;

/**
 * This class represents abstract Rat.
 *
 * @author Jan Michalec
 * @since 09/11/2021
 */
public abstract class Rat {

    /**
     * the Rat's max heath.
     */
    private static final int MAX_HEALTH = 3;
    /**
     * How many ticks the rat will wait before moving again.
     */
    private final int moveInterval;
    /**
     * Health of Rat.
     */
    private int health;
    /**
     * Time until the rat can move next.
     */
    private int waitTime;

    /**
     * The direction the rat is heading.
     */
    private CardinalDirection headDirection;

    /**
     * Capability of rat to copulate.
     */
    private boolean isSterile;

    protected Rat(Rat oldRat) {
        this(oldRat.getMoveInterval(), oldRat.waitTime,
            oldRat.isSterile, oldRat.getHealth(),
            oldRat.getHeadDirection());
    }

    protected Rat(int moveInterval,
        int initialWaitTime, boolean isSterile) {
        this.moveInterval = moveInterval;
        this.waitTime = initialWaitTime;
        this.isSterile = isSterile;
        this.health = MAX_HEALTH;
        this.headDirection = CardinalDirection.NORTH;
    }

    protected Rat(int moveInterval,
            int initialWaitTime, boolean isSterile,
            int health, CardinalDirection headDirection) {
        this.moveInterval = moveInterval;
        this.waitTime = initialWaitTime;
        this.isSterile = isSterile;
        this.health = health;
        this.headDirection = headDirection;
    }

    // GETTERS AND SETTERS

    /**
     *
     * @return the rat's health
     */
    public int getHealth() {
        return health;
    }

	public void setHealth(int health) {
		this.health = health;
	}

    /**
     *
     * @return how often (in ticks) the rat will move
     */
    public int getMoveInterval() {
        return moveInterval;
    }

    /**
     *
     * @return how many ticks until the rat will move again.
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     *
     * @param waitTime how many ticks until the rat will move again.
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     *
     * @return the path that point to the image associated with this Rat.
     */
    public abstract String getIconPath();

    /**
     * increment the state of the rat and return any new rats it creates.
     *
     * @return the rat created while iterating this one if one exists.
     */
    public Rat iterate() {
        if (waitTime > 0) {
            waitTime -= 1;
        } else {
            waitTime = moveInterval;
        }
        return null;
    }

    /**
     *
     * @param rats a list of rats for this one to act upon
     * @return the state of the list of rats after being acted upon.
     */
    public abstract List<Rat> act(List<Rat> rats);

    /**
     *
     * @return the direction the rat is facing.
     */
    public CardinalDirection getHeadDirection() {
        return headDirection;
    }

    /**
     *
     * @param headDirection the direction the rat will face.
     */
    public void setHeadDirection(CardinalDirection headDirection) {
        this.headDirection = headDirection;
    }

    /**
     * Set Rat to unable to copulate.
     */
    public void sterilise() {
        isSterile = true;
    }

    /**
     * Checks Rat's availability to copulate.
     *
     * @return The value sterilised.
     */
    public boolean isSterile() {
        return isSterile;
    }

    /**
     * Decreases health of Rat by 1.
     */
    public void takeDamage() {
        this.health -= 1;
    }

    /**
     * Set health to 0.
     */
    public void kill() {
        health = 0;
    }

    /**
     * Check if conditions for death apply.
     *
     * @return true if there are
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Points get after killing the Rat.
     *
     * @return int
     */
    public abstract int getPointsValue();

    /**
     *
     * @return true if the rat is ready to move this tick.
     */
    public boolean isReadyToMove() {
        return waitTime == 0;
    }
}
