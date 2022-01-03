package com.example.items;

import com.example.gamestate.Tile;
import com.example.rats.Rat;

public class Gas extends Item {
    /**
     *
     */
    private static final int TIMER_INIT = 3;
    /**
     *
     */
    private static final String ICON_PATH = "images/items/gas.png";
    /**
     *
     */
    private int timer;

    /**
     *
     */
    public Gas() {
        super();
        timer = TIMER_INIT;
    }

    /**
     *
     * @param timer
     */
    public Gas(int timer) {
        super();
        this.timer = timer;
    }

    @Override
    public String getIconPath() {
        return ICON_PATH;
    }

    @Override
    public Rat applyToRat(Rat rat) {
        rat.takeDamage();
        return rat;
    }

    @Override
    public Tile[][] applyToTiles(int x, int y, Tile[][] tiles) {
        if (timer > 0) {
            Tile[] adjacentTiles = {
                    tiles[x + 1][y],
                    tiles[x - 1][y],
                    tiles[x][y + 1],
                    tiles[x][y - 1]
            };

            timer -= 1;
            for (Tile tile : adjacentTiles) {
                //TODO: should we allow multiple items per tile? gass will be blocked by other items otherwise.
                tile.placeItem(new Gas(timer));
            }
        } else {
            exhaust();
        }
        return tiles;
    }
}
