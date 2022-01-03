package com.example.items;

import java.util.ArrayList;

import com.example.gamestate.Tile;
import com.example.rats.Rat;

public class Poison extends Item {

	private static final String ICON_PATH = "images/items/poison.png";

    /**
     *
     */
    public Poison() {
        super();
    }

    @Override
    public String getIconPath() {
        return ICON_PATH;
    }

    @Override
    public Rat applyToRat(Rat rat) {
        rat.kill();
        this.exhaust();
        return rat;
    }

    @Override
    public Tile[][] applyToTiles(int x, int y, Tile[][] tiles) {
        return tiles;
    }

}
