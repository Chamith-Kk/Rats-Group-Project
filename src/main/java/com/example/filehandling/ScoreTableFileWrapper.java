/**
 *
 */
package com.example.filehandling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.gamestate.Player;
import com.example.gamestate.ScoreTable;
/**
 * @author Anthony Stafford
 * @version 1.0.1
 */
public class ScoreTableFileWrapper extends TxtFileWrapper {
    /**
     *
     * @param path
     * @param fileName
     */
    public ScoreTableFileWrapper(String path, String fileName) {
        super(path, fileName);
        // TODO Auto-generated constructor stub
    }

//public ScoreTable load() {
//Player dummyPlayer = null;
//ArrayList<Player> sortedPlayers;
//PlayerFileWrapper playerFileWrapper = new PlayerFileWrapper(FILE_PATH);;
//sortedPlayers = playerFileWrapper.loadPlayers();
//}
    /**
     * saves scoretable to file
     * @param scoreTable to be saved
     */
    public void save(ScoreTable scoreTable) {
        for (Player player: scoreTable.getPlayers()) {
            this.writer.println(player.getName()
                + " " + String.valueOf(player.getMaxLevel()));
        }
    }

    /**
     *
     * @return
     */
    public ScoreTable loadScoreTable() {
        PlayerFileWrapper playerFileWrapper = new PlayerFileWrapper("some file path", "Players.txt");
        ArrayList<Player> players = playerFileWrapper.loadPlayers();
        return new ScoreTable(players);
    }
}
