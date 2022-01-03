package com.example.items;

import com.example.GameScreenController;
import com.example.gamestate.Terrain;
import com.example.gamestate.Tile;
import com.example.rats.CardinalDirection;
import com.example.rats.Rat;

public class Bomb extends Item {

    private static final int FUZE_TIME = 8;
    private static final String IMAGE_PATH_TEMPLATE = "images/items/bomb%d.png";
    private int timer;

    /**
     *
     */
    public Bomb() {
        super();
        this.timer = FUZE_TIME;
    }

    @Override
    public Rat applyToRat(Rat rat) {
        return rat;
    }

    @Override
    public Tile[][] applyToTiles(int x, int y, Tile[][] tiles) {

        if (this.timer > 0) {
            this.timer -= 1;
        } else {
            for (CardinalDirection direction : CardinalDirection.values()) {
                explodeInDirection(x, y, tiles, direction);
            }
            this.exhaust();
        }
        return tiles;
    }

    private void explodeInDirection(int x, int y,
                  Tile[][] tiles, CardinalDirection direction) {
        int xTileNum = x;
        int yTileNum = y;
        int boardWidth = tiles.length;
        int boardHeight = tiles[0].length;


        Tile tile;
        do {
            tile = tiles[xTileNum][yTileNum];
            for (Rat rat : tile.getRats()) {
                rat.kill();
            }
            switch (direction) {
                case NORTH:
                    yTileNum += 1;
                    break;
                case EAST:
                    xTileNum += 1;
                    break;
                case SOUTH:
                    yTileNum -= 1;
                    break;
                case WEST:
                    xTileNum -= 1;
                    break;
            }
        } while (tile.getTerrain() != Terrain.Grass &&
                xTileNum < boardWidth &&
                xTileNum >= 0 &&
                yTileNum < boardHeight &&
                yTileNum >= 0
        );
    }

    /**
     *
     * @return
     */
    public String getIconPath() {
        System.out.println(timer);
        return String.format(IMAGE_PATH_TEMPLATE, timer/ GameScreenController.TICKS_PER_SECOND);
    }
}
