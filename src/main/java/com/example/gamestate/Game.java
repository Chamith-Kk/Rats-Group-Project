package com.example.gamestate;

import com.example.GameScreenController;
import com.example.filehandling.LevelFileWrapper;
import com.example.items.Bomb;
import com.example.items.DeathRatSpawner;
import com.example.items.FemaleSexChange;
import com.example.items.Gas;
import com.example.items.Item;
import com.example.items.ItemSpawn;
import com.example.items.MaleSexChange;
import com.example.items.NoEntry;
import com.example.items.Poison;
import com.example.items.Sterilisation;
import com.example.rats.BabyRat;
import com.example.rats.Rat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Controls back-end of game at runtime.
 *
 * @author Anthony Stafford, Jackson Blankson
 * @version 1.2
 */
public class Game {
    /**
     *
     */
    private static final int INVENTORY_CAPACITY = 4;
    /**
     *
     */
    private final int parTime;
    /**
     *
     */
    private final int populationLimit;
    /**
     *
     */
    private final List<Item> inventory = new ArrayList<>();
    /**
     *
     */
    private final Board board;
    /**
     *
     */
    private final Queue<ItemSpawn> itemSpawnQueue;


	/**
     *
     */
    private int tickCount;
    /**
     *
     */
    private Player player;
    /**
     *
     */
    private int score;
    /**
     *
     */
    private LevelFileWrapper levelFileWrapper;

    /**
     * a default constructor to load an example level.
     */
    public Game() {
        this.populationLimit = 7;
        this.parTime = 120;
        ArrayList<Rat> rats1 = new ArrayList<>();
        ArrayList<Rat> rats2 = new ArrayList<>();
        ArrayList<Rat> rats3 = new ArrayList<>();
        ArrayList<Rat> rats4 = new ArrayList<>();

        rats1.add(new BabyRat(true));
        rats1.add(new BabyRat(false));
        rats2.add(new BabyRat(true));
        rats3.add(new BabyRat(true));
        rats4.add(new BabyRat(true));
        this.itemSpawnQueue = new LinkedList<>();
        this.itemSpawnQueue.add(new ItemSpawn(new FemaleSexChange(), 4));
        this.itemSpawnQueue.add(new ItemSpawn(new DeathRatSpawner(), 8));
        this.itemSpawnQueue.add(new ItemSpawn(new Gas(), 16));
        this.itemSpawnQueue.add(new ItemSpawn(new NoEntry(), 20));
        this.itemSpawnQueue.add(new ItemSpawn(new Bomb(), 5));
        this.itemSpawnQueue.add(new ItemSpawn(new Bomb(), 6));
        this.itemSpawnQueue.add(new ItemSpawn(new Bomb(), 8));
        this.itemSpawnQueue.add(new ItemSpawn(new Bomb(), 10));
        this.itemSpawnQueue.add(new ItemSpawn(new Bomb(), 15));

        Tile[][] tileGrid = {
                {new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path, rats2), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path, rats2), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path, rats3), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path, rats4), new Tile(Terrain.Path, rats1), new Tile(Terrain.Tunnel), new Tile(Terrain.Tunnel), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Path), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass)},
                {new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Path), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass), new Tile(Terrain.Grass)}
        };
        this.inventory.add(new MaleSexChange());
        this.inventory.add(new Bomb());
        this.inventory.add(new Sterilisation());
        this.inventory.add(new Poison());

        //this.board.placeRat(new BabyRat(true), 2, 2);
        this.board = new Board(11, 12, tileGrid);
        this.score = 0;
    }


    /**
     * @param parTime the time after which no bonus points are available
     *                on level completion.
     * @param populationLimit the size of rat population that will cause
     *                        the player to lose.
     * @param itemSpawnQueue a queue of ItemSpawns that defines the order
     *                       items will spawn in and how long it will take
     *                       for them to spawn.
     * @param board the instance of board that represents all the tiles
     *              and their state.
     */
    public Game(int parTime, int populationLimit,
                Queue<ItemSpawn> itemSpawnQueue, Board board) {
        this.parTime = parTime;
        this.populationLimit = populationLimit;
        this.itemSpawnQueue = itemSpawnQueue;
        this.board = board;
        this.score = 0;
    }

    /**
     * @param parTime the time after which no bonus points are available
     *                on level completion.
     * @param populationLimit the size of rat population that will cause
     *                        the player to lose.
     * @param itemSpawnQueue a queue of ItemSpawns that defines the order
     *                       items will spawn in and how long it will take
     *                       for them to spawn.
     * @param board the instance of board that represents all the tiles
     *              and their state.
     * @param player the player associated with this instance of game.
     * @param score the current score to be shown when the game is loaded.
     */
    public Game(int parTime, int populationLimit,
                Queue<ItemSpawn> itemSpawnQueue, Board board,
                Player player, int score) {
        this(parTime, populationLimit, itemSpawnQueue, board);
        this.player = player;
        this.score = score;
    }

    /**
     * @return the instance of board from this class.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return a queue of ItemSpawns that defines the order
     *         items will spawn in and how long it will take
     *         for them to spawn.
     */
    public Queue<ItemSpawn> getItemSpawnQueue() {
		return itemSpawnQueue;
	}

    /**
     * @return the object representation of the active player in this game.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param player set the representation of the player who is playing
     *               instance of this game.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the set of currently available items.
     */
    public List<Item> getInventory() {
        return this.inventory;
    }

    /**
     * @param inventory the set of currently available items.
     */
    public void setInventory(List<Item> inventory) {
        this.inventory.addAll(inventory);
    }

    /**
     * @return gets the current total score for this game.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @return the time after which no bonus points are available
     *         on level completion.
     */
    public int getParTime() {
		return parTime;
	}

    /**
     * @return gets population limit.
     */
    public int getPopulationLimit(){
        return this.populationLimit;
    }

    /**
     * @return true if the win/loss conditions have been met.
     */
    public boolean iterate() {
        boolean gameEnded = false;
        int scoreGained = board.iterate();
        int population = board.countPopulation();
        System.out.println("population: " + population);
        this.score += scoreGained;
        this.tickCount += 1;

        ItemSpawn nextItemSpawn = itemSpawnQueue.peek();
        if (nextItemSpawn != null) {
            System.out.println(nextItemSpawn.getWaitTime());
            if (inventory.size() < INVENTORY_CAPACITY) {
                if (nextItemSpawn.getWaitTime() <= 0) {
                    inventory.add(nextItemSpawn.getItem());
                    itemSpawnQueue.remove();
                } else {
                    nextItemSpawn.decreaseWaitTime();
                }
            }

            if (population >= populationLimit) {
                // lose the game
                this.score = 0;
                gameEnded = true;
            }
            if (population == 0) {
                // win the game
                int playTime = tickCount/GameScreenController.TICKS_PER_SECOND;
                if (playTime < parTime) {
                    this.score += (parTime - playTime);
                }
                gameEnded = true;
            }
        }
        return gameEnded;
    }

    /**
     * @return the file wrapper associated with this level
     */
    public LevelFileWrapper getCurrentLevelFile() {
        return levelFileWrapper;
    }

}
