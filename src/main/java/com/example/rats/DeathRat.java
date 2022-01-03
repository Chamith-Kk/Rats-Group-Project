package com.example.rats;

import java.util.List;

/**
 * This class holds the contents of the DeathRat Object.
 *
 * @author Cham Kotage, Jan Michalec
 * @version 1.3
 * @since 2021/12/04
 */

public class DeathRat extends Rat {

    /**
     *
     */
    private static final int MOVE_INTERVAL = 2;
    /**
     *
     */
    private static final int INITIAL_WAIT_TIME = 5;
    /**
     *
     */
    private static final boolean IS_STERILE = true;
    /**
     *
     */
    private static final String ICON_PATH_TEMPLATE = "/images/rats/death%s.png";


    /**
     * How many rats can DeathRat kills at the beginning.
     */
    private static final int KILL_CAP = 5;
    // starting with 5 kills makes no sense.

    /**
     * How many more rats can DeathRat kills.
     */
    private int killsRemaining = KILL_CAP;

    /**
     *
     * @return the numbers of kills this death rat can get before it dies.
     */
    public int getKillsRemaining() {
        return killsRemaining;
    }

    /**
     *
     */
    public DeathRat() {
        super(MOVE_INTERVAL, INITIAL_WAIT_TIME, IS_STERILE);
    }

    @Override
    public String getIconPath() {
        String direction;
        switch (this.getHeadDirection()) {
            case NORTH:
                direction = "Up";
                break;
            case EAST:
                direction = "Right";
                break;
            case WEST:
                direction = "Left";
                break;
            case SOUTH:
                direction = "Down";
                break;
            default:
                throw new NullPointerException();
        }
        return String.format(ICON_PATH_TEMPLATE, direction);
    }

    /**
     * increment the state of the rat and return any new rats it creates.
     * @return the rat created while iterating this one if one exists.
     */
    @Override
    public Rat iterate() {
        super.iterate();
        return null;
    }

    /**
     * kill any rats sharing a tile with this one.
     * once this rats has killed 5 others it will die
     *
     * @param rats the collection of rats this method should be applied to.
     * @return a collections of rats
     * with the resulting state from this rat's action.
     */
    @Override
    public List<Rat> act(List<Rat> rats) {
        for (Rat rat : rats) {
            if (!rat.equals(this) && killsRemaining > 0 && !rat.isDead()) {
                rat.kill();
                killsRemaining -= 1;
            }
        }
        if (this.killsRemaining <= 0) {
            this.kill();
        }
        return rats;
    }

    /**
     * Checks killCount and Health.
     *
     * @return false if either is 0
     */
    @Override
    public boolean isDead() {
        return killsRemaining == 0 || super.isDead();
    }

    /**
     * Points get after killing DeathRat.
     *
     * @return int
     */
    @Override
    public int getPointsValue() {
        return 0;
    }

    /**
     *
     * @param killsRemaining sets the number of kills this deathrat can get before is dies.
     */
    public void setKillsRemaining(int killsRemaining) {
        this.killsRemaining = killsRemaining;
    }
}
