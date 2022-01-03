package com.example.gamestate;

import com.example.items.Item;
import com.example.rats.CardinalDirection;
import com.example.rats.Rat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class will represent the state of the game board and the all rats and
 * items that are on it.
 *
 * @author Toby Dickenson
 */
public class Board {
    private final int width;
    private final int height;
    private Tile[][] tileGrid;
    private static Random random = new Random();

    /**
     * constructs a board of the given width and height.
     *
     * @param width the width of the board in tiles.
     * @param height the height of the board in tiles.
     * @param tiles the set of tiles that make are contained in the board.
     */
    public Board(int width, int height, Tile[][] tiles) {
        this.width = width;
        this.height = height;
        this.tileGrid = tiles;
    }

    /**
     * place a rat onto a tile in this board at the give coordinates.
     *
     * @param rat the instance of rat to be placed in the board.
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public void placeRat(Rat rat, int x, int y) {
        this.tileGrid[x][y].placeRat(rat);
    }

    /**
     * Attempt to place and instance of Item on the board. no item is placed
     * on the board if there is already an item at the given coordinates or
     * the instance of item being placed is null.
     *
     * @param item the instance of rat to be placed on the board.
     * @param x x coordinate.
     * @param y y coordinate.
     * @return returns true if the item was placed on the tile successfully.
     */
    public boolean tryPlaceItem(Item item, int x, int y) {
        System.out.println(item);
        if (item != null && this.tileGrid[x][y].getItem() == null) {
            this.tileGrid[x][y].placeItem(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * counts the number of rats currently on the board.
     *
     * @return the total number of rats on the board.
     */
    public int countPopulation() {
        int ratCount = 0;
        for (Tile[] row : tileGrid) {
            for (Tile tile : row) {
                ratCount += tile.getRats().size();
            }
        }
        return ratCount;
    }

    private int[] getAdjacentCoordinates(int x, int y,
                                         CardinalDirection direction) {
        switch (direction) {
            case NORTH:
                return new int[]{x, y - 1};
            case EAST:
                return new int[]{x + 1, y};
            case SOUTH:
                return new int[]{x, y + 1};
            case WEST:
                return new int[]{x - 1, y};
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * get the instance of tile at the given coordinates from this board.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @return the tile at the coordinates.
     */
    public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
        return this.tileGrid[x][y];
    }

    /**
     *
     * @return the width of the board in tiles.
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return the height of the board in tiles
     */
    public int getHeight() {
        return height;
    }

    /**
     * iterates the state of the board and all its rats and items.
     *
     * @return the score gained while iterating the state of the board.
     */
    public int iterate() {
        iterateItems();
        return iterateRats();
    }

    /**
     * @return the tiles stored by this instance of board as a TIle[][].
     */
    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    private boolean checkValidMove(int x, int y, CardinalDirection direction) {
        int[] tileCoordinates = getAdjacentCoordinates(x, y, direction);
        try {
            Tile tile = getTile(tileCoordinates[0], tileCoordinates[1]);
            return tile.isPassable();
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private void iterateItems() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile currentTile = this.tileGrid[x][y];
                Item currentItem = currentTile.getItem();
                if (currentItem != null) {
                    if (!currentItem.isExhausted()) {
                        this.tileGrid = currentItem.applyToTiles(x,
                                y,
                                this.tileGrid);
                    }
                    if (currentItem.isExhausted()) {
                        currentTile.removeItem();
                    }
                }
            }
        }
    }

    private Tile[][] getTileGridWithoutRats() {
        Tile[][] newTileGrid = new Tile[this.width][this.height];
        for (int i = 0; i < this.tileGrid.length; i++) {
            for (int j = 0; j < this.tileGrid[i].length; j++) {
                Tile oldTile = tileGrid[i][j];
                Terrain newTerrain = oldTile.getTerrain();
                Item newItem = oldTile.getItem();
                ArrayList<Rat> newRats = new ArrayList<>();

                newTileGrid[i][j] = new Tile(newTerrain, newRats, newItem);
            }
        }
        return newTileGrid;
    }

    /**
     * steps through the set of tiles and updates the internal state of any rats
     * for each rat that is killed some score will be
     * added to the total to be returned by the function.
     *
     * @return the accumulated score form all rats
     * killed during a specific call of this function.
     */
    private int iterateRats() {
        Tile[][] newTileGrid = getTileGridWithoutRats();
        int scoreGained = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile currentTile = this.tileGrid[x][y];
                List<Rat> rats = currentTile.getRats();

                for (int i = 0; i < rats.size(); i++) {
                    Rat rat = rats.get(i);
                    rats = rat.act(rats);
                }

                for (Rat rat : rats) {
                    CardinalDirection headDirection = rat.getHeadDirection();
                    ArrayList<CardinalDirection> validMoves = new ArrayList<>();
                    CardinalDirection[] moves = {
                            headDirection,
                            headDirection.left(),
                            headDirection.right()
                    };
                    for (CardinalDirection move : moves) {
                        if (checkValidMove(x, y, move)) {
                            validMoves.add(move);
                        }
                    }

                    CardinalDirection nextMove;
                    if (validMoves.size() == 0) {
                        nextMove = headDirection.behind();
                    } else {
                        int randomMove = random.nextInt(validMoves.size());
                        nextMove = validMoves.get(randomMove);
                    }

                    int[] newCoordinates = getAdjacentCoordinates(x,
                            y,
                            nextMove);
                    int newX = newCoordinates[0];
                    int newY = newCoordinates[1];

                    Rat newRat = rat.iterate();
                    if (newRat != null) {
                        newTileGrid[x][y].placeRat(newRat);
                    }

                    if (rat.isDead()) {
                        scoreGained += rat.getPointsValue();
                    } else if (rat.isReadyToMove()) {
                        Item tileItem = newTileGrid[newX][newY].getItem();
                        if (tileItem != null) {
                            rat = tileItem.applyToRat(rat);
                        }
                        if (rat.isReadyToMove()) {
                            newTileGrid[newX][newY].placeRat(rat);
                        }
                        rat.setHeadDirection(nextMove);
                    } else {
                        newTileGrid[x][y].placeRat(rat);
                    }
                }
            }
        }
        this.tileGrid = newTileGrid;
        return scoreGained;
    }
}
