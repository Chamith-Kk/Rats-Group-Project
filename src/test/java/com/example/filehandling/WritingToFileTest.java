package com.example.filehandling;

import com.example.gamestate.Player;

public class WritingToFileTest {
	public static void main(String[] args) {
		Player player = new Player("Anthony", 64);
		String filePath = "C:\\Users\\aosta\\OneDrive\\Documents\\Uni\\CS230\\A2\\FileHandlingTesting";
		String fileName = "Players.txt";
		PlayerFileWrapper playerFileWrapper = new PlayerFileWrapper(filePath, fileName);
		playerFileWrapper.savePlayer(player);
	}
}
