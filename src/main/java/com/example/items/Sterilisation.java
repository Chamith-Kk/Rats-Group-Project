package com.example.items;

import java.util.List;

import com.example.gamestate.Tile;
import com.example.rats.Rat;

public class Sterilisation extends Item {

	private static final String ICON_PATH = "images/items/sterilisation.png";
	private static final int RADIUS = 2;
	private static final int ACTIVE_TIME = 3;
	private int timeRemaining;

    /**
     *
     */
    public Sterilisation() {
        super();
        timeRemaining = ACTIVE_TIME;
    }

    @Override
    public String getIconPath() {
        return ICON_PATH;
    }

    @Override
    public Rat applyToRat(Rat rat) {
        return rat;
    }

    @Override
    public Tile[][] applyToTiles(int xPosition,
        int yPosition, Tile[][] tiles) {
        if (timeRemaining > 0) {
            int maxX = xPosition + RADIUS;
            int maxY = yPosition + RADIUS;
            int minX = xPosition + RADIUS;
            int minY = yPosition + RADIUS;
            for (int y = minY; y < maxY; y++) {
                for (int x = minX; x < maxX; x++) {
                    Tile tile = tiles[x][y];
                    List<Rat> rats = tile.getRats();
                    for (Rat rat : rats) {
                        rat.sterilise();
                    }
                }
            }
            timeRemaining -= 1;
        } else {
            exhaust();
        }
        return tiles;
    }
}
