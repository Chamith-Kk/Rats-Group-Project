package com.example.gamestate;

import java.util.ArrayList;

import com.example.items.Item;
import com.example.rats.Rat;

/**
 * Represents an individual tile on the game board.
 *
 * @author Anthony Stafford
 * @version 1.0
 */
public class Tile {
    /**
     *
     */
    private final Terrain terrain; // type of terrain
    /**
     *
     */
    private Item item; // current item on tile
    /**
     *
     */
    private ArrayList<Rat> rats = new ArrayList<>(); // rats populating tile
    /**
     *
     */
    private static final String GRASS_TILE = "/images/tiles/grassTile.png";
    /**
     *
     */
    private static final String ROAD_TILE = "/images/tiles/roadTile.png";
    /**
     *
     */
    private static final String TUNNEL_TILE = "/images/tiles/tunnelTile.png";
    /**
     *
     */

	public Tile(String terrain) {
		this.terrain = getTerrainTypeFromString(terrain);
	}
	/**
	 * Creates new tile made of specified terrain.
	 *
	 * @param terrain - Specifies terrain displayed on tile.
	 */
	public Tile(Terrain terrain) {
		this.terrain = terrain;
	}

    /**
     *
     * @param terrain
     * @param rats
     */
    public Tile(Terrain terrain, ArrayList<Rat> rats) {
        this(terrain);
        this.rats = rats;
    }

	public Tile(Terrain terrain, ArrayList<Rat> rats, Item item) {
		this(terrain, rats);
		this.item = item;
	}

	/**
	 *
	 * @return Type of terrain displayed on tile.
	 */
	public Terrain getTerrain() {
		return this.terrain;
	}

	public String getIconPath() {
		switch (this.terrain){
			case Grass:
				return GRASS_TILE;
			case Path:
				return ROAD_TILE;
			case Tunnel:
				return TUNNEL_TILE;
			default:
				throw new IllegalArgumentException();
		}
	}
	/**
	 * Places specified item onto tile.
	 *
	 * @param item - Item to be placed on tile
	 */
	public void placeItem(Item item) {
		// TODO: should we allow many items on i single cell?
		// if no them we should throw an exception when we try to put to many items on one cell.
		this.item = item;
	}

	public void removeItem() {
		this.item = null;
	}

    /**
     *
     * @return
     */
    public boolean isPassable() {
        return this.terrain != Terrain.Grass;
    }

    /**
     *
     * @return Item currently on tile.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     *
     * @return All rats currently on tile.
     */
    public ArrayList<Rat> getRats() {
        return this.rats;
    }

    /**
     *
     * @param rats
     */
    public void setRats(ArrayList<Rat> rats) {
        this.rats = rats;
    }

    /**
     *
     * @param rat - Rat to be placed on tile.
     */
    public void placeRat(Rat rat) {
        this.rats.add(rat);
    }

	public Terrain getTerrainTypeFromString(String terrain) {
		switch(terrain) {
			case "G":
				return Terrain.Grass;
			case "T":
				return Terrain.Tunnel;
			case "R":
				return Terrain.Path;
			default:
				throw new IllegalArgumentException();
		}
	}
	/**
	 *
	 * @param i - index of rat to be removed from tile. Possibly breaking
	 *          encapsulation.
	 * @throws IndexOutOfBoundsException this method will throw an
	 * error if the index provided is less than 0
	 * or greater than the max index for a rat.
	 */
	public void removeRat(int i) throws IndexOutOfBoundsException {
		this.rats.remove(i);
	}
}
