package com.example.items;

import com.example.gamestate.Tile;
import com.example.rats.DeathRat;
import com.example.rats.Rat;

public class DeathRatSpawner extends Item {

    /**
     *
     */
    private static final String ICON_PATH = "images/rats/deathRight.png";

    /**
     *
     */
    public DeathRatSpawner() {
        super();
    }

    @Override
    public String getIconPath() {
        return ICON_PATH;
    }

    /**
     * apply any effects this item can can apply to any specific rat.;
     *
     * @param rat the rat that this item will apply its effects to
     * @return the new rat resulting form applying the item's effects.
     */
    @Override
    public Rat applyToRat(Rat rat) {
        return rat;
    }

    /**
     * apply any effects this instance of item can have to the board as a whole
     *
     * @param x     the x position of this item in the set of tile
     * @param y     the y position of this item in the set of tiles
     * @param tiles the set of tiles this item will act upon.
     * @return the set of tiles after the item's effects have been applied.
     */
    @Override
    public Tile[][] applyToTiles(int x, int y, Tile[][] tiles) {
        tiles[x][y].placeRat(new DeathRat());
        exhaust();
        return tiles;
    }
}
