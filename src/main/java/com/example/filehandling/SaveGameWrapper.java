/**
 * 
 */
package com.example.filehandling;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.example.gamestate.Board;
import com.example.gamestate.Game;
import com.example.gamestate.Player;
import com.example.gamestate.Tile;
import com.example.items.*;
import com.example.rats.*;

/**
 * Used to load a game from a saved game text file and to save a current game to
 * a new game save text file.
 * 
 * @author Anthony Stafford
 * @version 1.6
 *
 */
public class SaveGameWrapper extends LevelFileWrapper {
	// Delimiters
	private static final String ITEM_INFO_DELIM = ",";
	private static final String ITEM_DELIM = ";";
	private static final String RAT_COORDS_DELIM = ",";
	private static final String RAT_DETAILS_DELIM = ";";
	private static final String INVENTORY_DELIM = ",";
	private static final String QUEUE_ELEMENT_DELIM = ";";
	private static final String ITEM_SPAWN_DETAIL_DELIM = ",";
	// Line numbers
	private static final int PLAYER_NAME_LINE = 0;
	private static final int LEVEL_FILE_PATH_LINE = 1;
	private static final int LEVEL_FILE_NAME_LINE = 2;
	private static final int PAR_TIME_LINE = 3;
	private static final int SCORE_LINE = 4;
	private static final int ITEM_INFO_LINE = 5;
	private static final int QUEUE_ELEMENT_LINE = 6;
	private static final int INVENTORY_LINE = 7;
	// Rat detail array indices
	private static final int RAT_COORDS = 0;
	private static final int IS_BABY = 1;
	private static final int AGE = 2;
	private static final int SEX = 3;
	private static final int HEALTH = 4;
	private static final int WAIT_TIME = 5;
	private static final int IS_STERILE = 6;
	private static final int HEAD_DIRECTION = 7;
	private static final int IS_PREGNANT = 8;
	private static final int NUM_OF_BABIES = 9;
	private static final int KILLS_REMAINING = 10;

	// private static final String SAVED_RAT_FILE_PATH = "Some path"; // change
	// later
	// private static final String SAVED_RAT_FILE_NAME = "SavedRatsFromGame.txt";
	private String levelFileName;
	private String levelFilePath;
	/*
	 * txt file format is as follows: line 1: Player's name line 2: the path of the
	 * level file which is being used line 3: level file name line 4: items data
	 * line 5: rat data
	 */

	/**
	 * @param path
	 * @param fileName
	 */
	public SaveGameWrapper(String path, String fileName) {
		super(path, fileName);

	}

	/**
	 * Saves game details to file in specified and correct format.
	 * 
	 * @param currentBoard
	 * @param currentGameState
	 */
	public void saveGame(Board currentBoard, Game currentGameState) {
		String levelFilePath = currentGameState.getCurrentLevelFile().getFilePath();// used so that appropriate
																					// level may be reloaded.
		this.writer.flush();
		this.writer.println(currentGameState.getPlayer().getName());
		this.writer.println(levelFilePath);
		this.writer.println(this.levelFileName);
		this.writer.println(currentGameState.getParTime());
		this.writer.println(currentGameState.getScore());
		saveItems(currentGameState);
		saveItemSpawnQueue(currentGameState);
		saveInventory(currentGameState);
		saveRats(currentGameState);
		this.writer.flush();
		this.writer.close();
	}

	/**
	 * writes baby rat to save game file in correct format.
	 * 
	 * @param rat - rat to be written to file
	 * @param sex - sex of baby rat
	 * @param x   - x coord
	 * @param y   - y coord
	 */
	private void writeRatToFile(BabyRat rat, String sex, int x, int y) {
		writer.println(x + RAT_COORDS_DELIM + y + RAT_DETAILS_DELIM);
		writer.print(true + RAT_DETAILS_DELIM);// is baby? line
		writer.print(rat.getAge() + RAT_DETAILS_DELIM);
		if (rat.isMale()) {
			writer.print("M" + RAT_DETAILS_DELIM);
		} else {
			writer.print("F" + RAT_DETAILS_DELIM);
		}
		writer.print(rat.getHealth() + RAT_DETAILS_DELIM);
		writer.print(rat.getWaitTime() + RAT_DETAILS_DELIM);
		writer.print(rat.isSterile() + RAT_DETAILS_DELIM);
		writer.print(rat.getHeadDirection() + RAT_DETAILS_DELIM);
		writer.print(false + RAT_DETAILS_DELIM);// not pregnant
		writer.print(0 + RAT_DETAILS_DELIM);// no babies
		writer.print(0);// no kills remaining as not death rat
		writer.println();// set up new line for next write
	}

	/**
	 * writes adult rat to save game file in correct format.
	 * 
	 * @param rat - rat to be written to file
	 * @param x   - x coord
	 * @param y   - y coord
	 */
	private void writeRatToFile(AdultRat rat, int x, int y) {
		writer.println(x + RAT_COORDS_DELIM + y + RAT_DETAILS_DELIM);
		writer.print(false + RAT_DETAILS_DELIM);// is baby? line
		writer.print(-1 + RAT_DETAILS_DELIM);
		if (rat.isMale()) {
			writer.print("M" + RAT_DETAILS_DELIM);
		} else {
			writer.print("F" + RAT_DETAILS_DELIM);
		}
		writer.print(rat.getHealth() + RAT_DETAILS_DELIM);
		writer.print(rat.getWaitTime() + RAT_DETAILS_DELIM);
		writer.print(rat.isSterile() + RAT_DETAILS_DELIM);
		writer.print(rat.getHeadDirection() + RAT_DETAILS_DELIM);
		writer.print(rat.isPregnant() + RAT_DETAILS_DELIM);// false if male.
		writer.print(rat.getBabies() + RAT_DETAILS_DELIM);// 0 if male.
		writer.print(0);// no kills remaining as not death rat
		writer.println();// set up new line for next write.
	}

	/**
	 * writes death rat to save game file in correct format.
	 * 
	 * @param rat - rat to be written to file
	 * @param x   - x coord
	 * @param y   - y coord
	 */
	private void writeRatToFile(DeathRat rat, int x, int y) {
		writer.println(x + RAT_COORDS_DELIM + y + RAT_DETAILS_DELIM);
		writer.print(true + RAT_DETAILS_DELIM);// is baby? line
		writer.print(-1 + RAT_DETAILS_DELIM);
		writer.print("D" + RAT_DETAILS_DELIM);
		writer.print(rat.getHealth() + RAT_DETAILS_DELIM);
		writer.print(rat.getWaitTime() + RAT_DETAILS_DELIM);
		writer.print(rat.isSterile() + RAT_DETAILS_DELIM);
		writer.print(rat.getHeadDirection() + RAT_DETAILS_DELIM);
		writer.print(false + RAT_DETAILS_DELIM);// not pregnant
		writer.print(0 + RAT_DETAILS_DELIM);// no babies
		writer.print(rat.getKillsRemaining());
		writer.println();// set up new line for next write.
	}

	/**
	 * adds all rats to save game file.
	 * 
	 * @param currentGame
	 */
	private void saveRats(Game currentGame) {
		Tile[][] currentTiles = currentGame.getBoard().getTileGrid();
		for (int i = 0; i < currentTiles.length; i++) {
			for (int j = 0; j < currentTiles[i].length; j++) {
				ArrayList<Rat> currentRatsOnTile = currentTiles[i][j].getRats();
				for (int k = 0; k < currentRatsOnTile.size(); k++) {
					Rat rat = currentRatsOnTile.get(i);
					if (rat.getClass().equals(AdultRat.class)) {
						writeRatToFile((AdultRat) rat, i, j);
					} else if (rat.getClass().equals(BabyRat.class)) {
						writeRatToFile((BabyRat) rat, "sex", i, j);// need to change literal here
					} else if (rat.getClass().equals(DeathRat.class)) {
						writeRatToFile((DeathRat) rat, i, j);
					}
				}
			}
		}

	}
	
	private void saveInventory(Game currentGameState) {
		for(Item item: currentGameState.getInventory()) {
			this.writer.print(item.getClass().getSimpleName());
		}
	}
	
	private List<Item> loadInventory() throws FileNotFoundException{
		List<Item> inventory = new ArrayList<>();
		String[] inventoryArray = this.readFromFile().get(INVENTORY_LINE).split(INVENTORY_DELIM);
		for(String itemName: inventoryArray) {
			Item item = getItem(itemName);
			inventory.add(item);
		}
		return inventory;
	}

	/**
	 * writes each item and their x and y coordinates to the file in the
	 * correctFormat
	 * 
	 * @param currentGame
	 */
	private void saveItems(Game currentGame) {
		Tile[][] tiles = currentGame.getBoard().getTileGrid();
		// String itemDetails[] = null;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				String itemName = tiles[i][j].getClass().getSimpleName();
				this.writer.flush();
				// uses .print and not println as we want it all on same line
				this.writer.print(itemName + ITEM_INFO_DELIM + i + ITEM_INFO_DELIM + j + ITEM_DELIM);// uses .print and
																										// not println
																										// as we want it
																										// all on same line
				this.writer.flush();
				this.writer.close();
			}
		}
		this.writer.println();// set new line for next write.
	}

	private void saveItemSpawnQueue(Game currentGame) {
		Queue<ItemSpawn> currentItemSpawnQueue = currentGame.getItemSpawnQueue();
		for (ItemSpawn itemSpawn : currentItemSpawnQueue) {
			this.writer.print(itemSpawn.getItem().getClass().getSimpleName() + "," + itemSpawn.getWaitTime() + ";");
		}
		this.writer.println();// sets next line.
	}

	/**
	 * @throws FileNotFoundException
	 */
	private void setLevelFilePath() throws FileNotFoundException {
		this.levelFilePath = this.readFromFile().get(LEVEL_FILE_PATH_LINE);
	}

	/**
	 * Searches for player record in player file.
	 * 
	 * @param name of player
	 * @return Player object
	 */
	private Player getPlayer(String name) {
		PlayerFileWrapper playerFileWrapper = new PlayerFileWrapper("data", "Players.txt");
		Player foundPlayer = null;
		for (Player player : playerFileWrapper.loadPlayers()) {
			if (player.getName().equals(name)) {
				foundPlayer = player;
			}
		}
		return foundPlayer;
	}

	/**
	 * Loads rats from file to corresponding tile on board.
	 * 
	 * @param board - board which game will use
	 * @throws FileNotFoundException
	 */
	private void loadRats(Board board) throws FileNotFoundException {
		ArrayList<String> savedRats = this.readFromFile();
		/*
		 * remove data from start of the array read from file so that only rat data is
		 * present. Can probably change this to for loop.
		 */
		savedRats.remove(0);// remove player name
		savedRats.remove(0);// remove level file path
		savedRats.remove(0);// remove level file name
		savedRats.remove(0);// remove par time
		savedRats.remove(0);// remove score
		savedRats.remove(0);// remove items info
		savedRats.remove(0);//remove item spawn queue
		savedRats.remove(0);//remove inventory
		// Tile[][] tileGrid = board.getTileGrid();
		for (int i = 0; i < savedRats.size(); i++) {
			// for each individual rat get its data
			String savedRat[] = savedRats.get(i).split(",");
			String ratSex = savedRat[SEX];
			String coords[] = savedRat[RAT_COORDS].split(RAT_DETAILS_DELIM);
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);
			boolean isBaby = Boolean.parseBoolean(savedRat[IS_BABY]);
			int babyRatAge = Integer.parseInt(savedRat[AGE]);
			int health = Integer.parseInt(savedRat[HEALTH]);
			int waitTime = Integer.parseInt(savedRat[WAIT_TIME]);
			boolean isSterile = Boolean.parseBoolean(savedRat[IS_STERILE]);
			String headDirectionString = savedRat[HEAD_DIRECTION];
			CardinalDirection headDirection = null;
			switch (headDirectionString) {
			case "NORTH":
				headDirection = CardinalDirection.NORTH;
				break;
			case "SOUTH":
				headDirection = CardinalDirection.SOUTH;
				break;
			case "EAST":
				headDirection = CardinalDirection.EAST;
				break;
			case "WEST":
				headDirection = CardinalDirection.WEST;
				break;

			}

			boolean isPregnant = Boolean.parseBoolean(savedRat[IS_PREGNANT]);
			int numOfBabies = Integer.parseInt(savedRat[NUM_OF_BABIES]);
			int killsRemaining = Integer.parseInt(savedRat[KILLS_REMAINING]);
			// add rat to tile.
			if (isBaby) {
				String babySex = savedRat[2];
				switch (babySex) {
				case "M":
					BabyRat maleBabyRat = new BabyRat(true);
					maleBabyRat.setAge(babyRatAge);
					maleBabyRat.setHealth(health);
					maleBabyRat.setWaitTime(waitTime);
					maleBabyRat.setHeadDirection(headDirection);
					board.placeRat(maleBabyRat, x, y);
					break;
				case "F":
					BabyRat femaleBabyRat = new BabyRat(false);
					femaleBabyRat.setAge(babyRatAge);
					femaleBabyRat.setHealth(health);
					femaleBabyRat.setWaitTime(waitTime);
					femaleBabyRat.setHeadDirection(headDirection);
					board.placeRat(femaleBabyRat, x, y);
				}
			} else {
				switch (ratSex) {
				case "M":
					AdultRat maleRat = new AdultRat(true);
					maleRat.setHealth(health);
					maleRat.setWaitTime(waitTime);
					if (isSterile) {
						maleRat.sterilise();
					}
					maleRat.setHeadDirection(headDirection);
					board.placeRat(maleRat, x, y);
					break;
				case "F":
					AdultRat femaleRat = new AdultRat(false);
					board.placeRat(femaleRat, x, y);
					femaleRat.setHealth(health);
					femaleRat.setWaitTime(waitTime);
					if (isSterile) {
						femaleRat.sterilise();
					}
					femaleRat.setHeadDirection(headDirection);
					femaleRat.setIsPregnant(isPregnant);
					femaleRat.setBabies(numOfBabies);
					break;
				case "D":
					DeathRat deathRat = new DeathRat();
					deathRat.setHealth(health);
					deathRat.setWaitTime(waitTime);
					deathRat.setHeadDirection(headDirection);
					deathRat.setKillsRemaining(killsRemaining);
					board.placeRat(deathRat, x, y);

				}
			}
		}
	}

	/**
	 * 
	 * @return score read from file
	 */
	private int getScoreFromFile() {
		int score = 0;
		try {
			score = Integer.parseInt(this.readFromFile().get(SCORE_LINE));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;
	}

	/**
	 * 
	 * @return par time read from file.
	 */
	private int getParTimeFromFile() {
		int parTime = 0;
		try {
			parTime = Integer.parseInt(this.readFromFile().get(PAR_TIME_LINE));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parTime;
	}
	private Item getItem(String itemName) {
		Item item = null;
		switch (itemName) {
		case "Bomb":
			item = new Bomb();
			break;
		case "FemaleSexChange":
			item = new FemaleSexChange();
			break;
		case "Gas":
			item = new Gas();
			break;
		case "MaleSexChange":
			item = new MaleSexChange();
			break;
		case "NoEntry":
			item = new NoEntry();
			break;
		case "Poison":
			item = new Poison();
			break;
		case "Sterilisation":
			item = new Sterilisation();
			break;
		}
		return item;
	}

	private Queue<ItemSpawn> loadItemSpawnQueue() throws FileNotFoundException {
		Queue<ItemSpawn> itemSpawnQueue = new LinkedList<>();
		String[] itemSpawnArray = this.readFromFile().get(QUEUE_ELEMENT_LINE).split(QUEUE_ELEMENT_DELIM);
		for (String itemSpawnString : itemSpawnArray) {
			String[] itemSpawnInfo = itemSpawnString.split(ITEM_SPAWN_DETAIL_DELIM);
			Item item = getItem(itemSpawnInfo[0]);
			ItemSpawn itemSpawn = new ItemSpawn(item, Integer.parseInt(itemSpawnInfo[1]));
			itemSpawnQueue.add(itemSpawn);
		}
		return itemSpawnQueue;
	}

	/**
	 * loads a new board and updates the current gamestate, corresponding to the
	 * data read from the game save txt file.
	 *
	 * @return game containing data loaded from file.
	 * @throws FileNotFoundException
	 */
	public Game loadSavedGame() throws FileNotFoundException {
		Game savedGame = null;
		LevelFileWrapper levelFileWrapper = new LevelFileWrapper(this.levelFilePath, this.levelFileName);
		int levelWidth = levelFileWrapper.getLevelWidth();
		int levelHeight = levelFileWrapper.getLevelHeight();
		Tile[][] tileGrid = new Tile[levelWidth][levelHeight];
		Board levelBoard = new Board(levelWidth, levelHeight, tileGrid);
		Player player = getPlayer(this.readFromFile().get(PLAYER_NAME_LINE));
		int parTime = getParTimeFromFile();
		int score = getScoreFromFile();
		int populationLimit = levelFileWrapper.getPopulationLimit();
		loadRats(levelBoard);
		loadItems(levelBoard);
		savedGame = new Game(parTime, populationLimit, loadItemSpawnQueue(), levelBoard, player, score);
		savedGame.setInventory(loadInventory());
		return savedGame;
	}

	/**
	 * Adds items read from file to correct tile on level board whilst also adding
	 * items and corresponding wait times to a queue.
	 * 
	 * @param levelBoard
	 * @return Item spawn timings in queue.
	 * @throws FileNotFoundException
	 */
	private void loadItems(Board levelBoard) throws FileNotFoundException {
		String[] items = this.readFromFile().get(ITEM_INFO_LINE).split(";");
		for (String item : items) {
			String[] currentItem = item.split(",");
			int x = Integer.parseInt(currentItem[1]);
			int y = Integer.parseInt(currentItem[2]);
			String itemType = currentItem[0];
			switch (itemType) {
			case "Bomb":
				Bomb bomb = new Bomb();
				levelBoard.tryPlaceItem(bomb, x, y);
				break;
			case "FemaleSexChange":
				FemaleSexChange femaleSexChange = new FemaleSexChange();
				levelBoard.tryPlaceItem(femaleSexChange, x, y);
				break;
			case "Gas":
				Gas gas = new Gas();
				levelBoard.tryPlaceItem(gas, x, y);
				break;
			case "MaleSexChange":
				MaleSexChange maleSexChange = new MaleSexChange();
				levelBoard.tryPlaceItem(maleSexChange, x, y);
				break;
			case "NoEntry":
				NoEntry noEntry = new NoEntry();
				levelBoard.tryPlaceItem(noEntry, x, y);
				break;
			case "Poison":
				Poison poison = new Poison();
				levelBoard.tryPlaceItem(poison, x, y);
				break;
			case "Sterilisation":
				Sterilisation sterilisation = new Sterilisation();
				levelBoard.tryPlaceItem(sterilisation, x, y);
				break;
			}
		}
	}

	public String getLevelFilePath() {
		return levelFilePath;
	}
}
