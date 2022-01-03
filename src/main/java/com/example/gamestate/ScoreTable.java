/**
 * 
 */
package com.example.gamestate;

import java.util.ArrayList;

/**
 * @author Anthony Stafford
 * @version 1.2
 */
public class ScoreTable {

	private ArrayList<Player> players;
	/**
	 * 
	 * @param players
	 */
	public ScoreTable(ArrayList<Player> players) {
		this.players = players;
		this.sortPlayers();
	}
	
	private void sortPlayers() {
		//TODO logic for sorting players in order of high score
	}
	/**
	 * 
	 * @return players sorted by highest level
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	

}
