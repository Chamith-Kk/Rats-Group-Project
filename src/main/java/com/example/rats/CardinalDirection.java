package com.example.rats;

/**
 * This class is a enum, which holds directions.
 *
 * @author Jan Michalec
 *
 */
public enum CardinalDirection {
    NORTH, WEST, EAST, SOUTH;

    /**
     *
     * @return
     */
    public CardinalDirection left() {
        switch (this) {
            case NORTH:
                return CardinalDirection.WEST;
            case SOUTH:
                return CardinalDirection.EAST;
            case EAST:
                return CardinalDirection.NORTH;
            case WEST:
                return CardinalDirection.SOUTH;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @return
     */
    public CardinalDirection right() {
        switch (this) {
            case NORTH:
                return CardinalDirection.EAST;
            case SOUTH:
                return CardinalDirection.WEST;
            case EAST:
                return CardinalDirection.SOUTH;
            case WEST:
                return CardinalDirection.NORTH;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @return
     */
    public CardinalDirection behind() {
        switch (this) {
            case NORTH:
                return CardinalDirection.SOUTH;
            case SOUTH:
                return CardinalDirection.NORTH;
            case EAST:
                return CardinalDirection.WEST;
            case WEST:
                return CardinalDirection.EAST;
            default:
                throw new IllegalArgumentException();
        }
    }
}
