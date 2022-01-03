
package com.example.filehandling;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.example.gamestate.Board;
import com.example.gamestate.Tile;
import com.example.rats.BabyRat;

/**
 * Used to read data from level file. Core functionality also includes offering
 * methods to load a new level into a game.
 *
 * @author Anthony Stafford
 * @version 1.0.5
 *
 */
public class LevelFileWrapper extends TxtFileWrapper {
    /**
     *
     */
    protected static final int RAT_INFO_LINE = 0;
    /**
     *
     */
    protected static final String ITEM_SPAWN_RATE_TIMER_DELIM = ";";
    /**
     *
     */
    protected static final String RAT_START_LOCATION_DELIM = ",";
    /**
     *
     */
    protected static final String RAT_STARTING_SEX_DELIM = ";";
    /**
     *
     */
    protected static final String WIDTH_HEIGHT_DELIM = ";";
    /**
     *
     */
    protected static final int DIMENSIONS_LINE = 4;
    /**
     *
     */
    protected static final int LAYOUT_LINE = 5;
    /**
     *
     */
    protected static final int RAT_POPULATION_LINE = 3;
    /**
     *
     */
    protected static final int ITEM_SPAWN_RATE_TIMER_LINE = 2;
    /**
     *
     */
    private static final int POPULATION_LIMIT_LINE = 0;

    /**
     * @param path
     */
    public LevelFileWrapper(String path, String fileName) {
        super(path, fileName);
    }

    private int getDelimIndex(String line, String delimitter) {
        return line.indexOf(delimitter);
    }

    /**
     *
     * @return height of level to be created. Returns -1 if error occurs.
     */
    protected int getLevelHeight() {
        int height = -1;
        try {
            String line = this.readFromFile().get(DIMENSIONS_LINE);
            height = Integer.parseInt(line.substring(0,
                    getDelimIndex(line, WIDTH_HEIGHT_DELIM)));
        } catch (NumberFormatException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return height;
    }

/**
 *
 * @return width of level to be created. Returns -1 if error occurs.
 */
    protected int getLevelWidth() {
        int width = -1;
        try {
            String line = this.readFromFile().get(DIMENSIONS_LINE);
            width = Integer.parseInt(line.substring(getDelimIndex(line,
                    WIDTH_HEIGHT_DELIM)));
        } catch (NumberFormatException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return width;
    }

    /**
     *
     * @param tiles
     * @throws FileNotFoundException
     */
    protected void setTiles(Tile[][] tiles) throws FileNotFoundException {
        // creates sub list as arraylist containing only the layout data.
        ArrayList<String> layoutLines;
        layoutLines = (ArrayList<String>) this.readFromFile().subList(LAYOUT_LINE,
                this.readFromFile().size());
        for (int i = 0; i < layoutLines.size(); i++) {
            for (int j = 0; j < this.getLevelWidth(); j++) {
                tiles[i][j] = new Tile(layoutLines.get(j));
            }
        }
    }

    /**
     * places each baby rat on their initial tile.
     *
     * @param tiles
     * @throws FileNotFoundException
     */
    protected void setBabyRats(Tile[][] tiles) throws FileNotFoundException {
        String[] babyRats = this.readFromFile().get(RAT_INFO_LINE).split(RAT_STARTING_SEX_DELIM);
        for (int i = 0; i < babyRats.length; i++) {
            String[] currentRat = babyRats[i].split(RAT_START_LOCATION_DELIM);
            int x = Integer.parseInt(currentRat[0]);
            int y = Integer.parseInt(currentRat[1]);
            boolean isMale = currentRat[2].equals("M");
            tiles[x][y].placeRat(new BabyRat(isMale));
        }
    }

    /**
     *
     * @return item spawn rates as specified in file.
     * @throws FileNotFoundException
     */
    public int[] getItemSpawnRate() throws FileNotFoundException {
        String[] tickRatesFromFile = this.readFromFile().get(ITEM_SPAWN_RATE_TIMER_LINE).split(ITEM_SPAWN_RATE_TIMER_DELIM);
        int[] itemTickRates = new int[tickRatesFromFile.length - 1];
        for (int i = 0; i <= itemTickRates.length; i++) {
            itemTickRates[i] = Integer.parseInt(tickRatesFromFile[i]);
        }
        return itemTickRates;
    }

	public int getPopulationLimit() {
		int populationLimit = 0;
		try {
			populationLimit = Integer.parseInt(this.readFromFile().get(POPULATION_LIMIT_LINE));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return populationLimit;
	}

    /**
     * Creates a new instance of the Board
     * corresponding to the data read from level
     * text file.
     *
     * @return Game board ready to be used.
     */
    public Board loadLevel() {
        Tile tiles[][] = new Tile[getLevelWidth()][getLevelHeight()];
        Board board = null;
        try {
            setTiles(tiles);
            setBabyRats(tiles);
            board = new Board(Integer.parseInt(this.readFromFile().get(RAT_POPULATION_LINE)),
                    0, tiles);
        } catch (NumberFormatException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return board;
    }

}
