package com.example.filehandling;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.gamestate.Player;

class PlayerFileWrapperTest {
	protected Player testPlayer;
	protected PlayerFileWrapper playerFileWrapper;
	protected String path = "C:\\Users\\aosta\\OneDrive\\Documents\\Uni\\CS230\\A2\\FileHandlingTesting";
	protected String fileName = "Players.txt";
	public void setUp() {
		testPlayer = new Player("Anthony", 99);
		playerFileWrapper = new PlayerFileWrapper(path, fileName);
	}
	@Test
	void writeToFileTest() {
		setUp();
		playerFileWrapper.savePlayer(testPlayer);
	}

}
