package com.example.items;

import com.example.gamestate.Tile;
import com.example.rats.Rat;

public abstract class Item {
    /**
     *
     */
    private boolean exhausted = false;

    /**
     *
     * @return
     */
    public abstract String getIconPath();

    /**
     * check if the instance of item is exhausted/ used up.
     *
     * @return a boolean that is true when the item has been "used up".
     */
    public boolean isExhausted() {
        return this.exhausted;
    }

    /**
     *
     */
    public void exhaust() {
        this.exhausted = true;
    }

    /**
     * apply any effects this item can can apply to any specific rat.
     *
     * @param rat the rat that this item will apply its effects to.
     * @return the new rat resulting form applying the item's effects.
     */
    public abstract Rat applyToRat(Rat rat);

    /**
     * apply any effects this instance.
     * of item can have to the board as a whole.
     *
     * @param x     the x position of this item in the set of tile.
     * @param y     the y position of this item in the set of tiles.
     * @param tiles the set of tiles this item will act upon.
     * @return the set of tiles after the item's effects have been applied.
     */
    public abstract Tile[][] applyToTiles(int x, int y, Tile[][] tiles);
}
