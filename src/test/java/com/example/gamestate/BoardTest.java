package com.example.gamestate;

import com.example.rats.AdultRat;
import com.example.rats.CardinalDirection;
import com.example.rats.Rat;
//import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    //  GGGG
    //  GPPG
    //  GGGG
    Tile[][] tileGrid = {
            {new Tile(Terrain.Path),new Tile(Terrain.Path)},
    };


    @Test
    void iterateTest() {
        Tile[][] tileGrid = {
                {new Tile(Terrain.Path),new Tile(Terrain.Path)},
        };

        Board testBoard = new Board(1, 2, tileGrid);
        Rat rat = new AdultRat(true);
        testBoard.placeRat(rat, 0, 0);
        assertEquals(1, testBoard.getTile(0,0).getRats().size());
        assertEquals(0, testBoard.getTile(0,1).getRats().size());
        testBoard.iterate();
        assertEquals(0, testBoard.getTile(0,0).getRats().size());
        assertEquals(1, testBoard.getTile(0,1).getRats().size());
    }

    @Test
    void RatFollowsPathTest() {
        Tile[][] tileGrid = {
                {new Tile(Terrain.Path),new Tile(Terrain.Path)},
                {new Tile(Terrain.Grass),new Tile(Terrain.Path)}
        };

        Board testBoard = new Board(2, 2, tileGrid);
        Rat rat = new AdultRat(true);
        testBoard.placeRat(rat, 0, 0);
        assertEquals(1, testBoard.getTile(0,0).getRats().size());
        assertEquals(0, testBoard.getTile(1,1).getRats().size());
        testBoard.iterate();
        testBoard.iterate();
        assertEquals(0, testBoard.getTile(0,0).getRats().size());
        assertEquals(1, testBoard.getTile(1,1).getRats().size());
    }
}