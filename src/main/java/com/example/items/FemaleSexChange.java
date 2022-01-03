package com.example.items;

import com.example.gamestate.Tile;
import com.example.rats.AdultRat;
import com.example.rats.BabyRat;
import com.example.rats.Rat;

public class FemaleSexChange extends Item {

    /**
     *
     */
    private static final String ICON_PATH = "/images/items/femaleChange.png";

    /**
     *
     */
    public FemaleSexChange() {
        super();
    }

    @Override
    public String getIconPath() {
        return ICON_PATH;
    }

    @Override
    public Rat applyToRat(Rat rat) {
        this.exhaust();
        if (rat instanceof AdultRat) {
            return new AdultRat(rat, false);
        } else if (rat instanceof BabyRat) {
            return new BabyRat((BabyRat) rat, false);
        } else {
            return rat;
        }
    }

    @Override
    public Tile[][] applyToTiles(int x, int y, Tile[][] tiles) {
        return tiles;
    }
}
