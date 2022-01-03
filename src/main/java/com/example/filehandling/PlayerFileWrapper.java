package com.example.filehandling;

import com.example.gamestate.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Allows the reading to and writing from of player.txt file,
 * containing functionality to load and save player profiles.
 * @author Anthony Stafford.
 * @version 1.0.
 */
public class PlayerFileWrapper extends TxtFileWrapper {

    /**
     * Delimiter for file handling.
     */
    private static final String DELIM = ",";

    /**
     * creates an instance of PlayerFileWrapper that can be used to handle
     * operations relating to the player file given by the path and filename
     * provided.
     *
     * @param path the folder where the player file is located.
     * @param fileName the name of the player file.
     */
    public PlayerFileWrapper(final String path, final String fileName) {
        super(path, fileName);
    }

    /**
     * @return Player objects for each player read from file.
     */
    public ArrayList<Player> loadPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        try {
            for (String line : this.readFromFile()) {
                String[] playerRecord = line.split(DELIM);
                players.add(new Player(playerRecord[0],
                    Integer.parseInt(playerRecord[1])));
            }
        } catch (NumberFormatException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return players;
    }
    /**
     * Saves player profile to file.
     * @param player to add
     */
    public void savePlayer(Player player) {
        String playerRecord = player.getName()
                + DELIM + player.getMaxLevel();
        this.writer.flush();
        this.writer.println(playerRecord);
        this.writer.close();
    }

}
