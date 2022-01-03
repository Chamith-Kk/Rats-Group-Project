package com.example.items;

import com.example.gamestate.Tile;
import com.example.rats.Rat;

public class NoEntry extends Item {

    /**
     *
     */
    private static final String ICON_PATH_TEMPLATE = "images/items/noEntry%d.png";
    /**
     *
     */
    private static final int STARTING_HP = 5;
    /**
     *
     */
    private int hp;

    /**
     *
     */
    public NoEntry() {
        super();
        hp = STARTING_HP;
    }

    @Override
    public String getIconPath() {
        return String.format(ICON_PATH_TEMPLATE, hp);
    }

    @Override
    public Rat applyToRat(Rat rat) {
        hp -= 1;
        if (hp == 0) {
            this.exhaust();
        }
        rat.setWaitTime(1);
        return rat;
    }

    @Override
    public Tile[][] applyToTiles(int x, int y, Tile[][] tiles) {
        return tiles;
    }
}
