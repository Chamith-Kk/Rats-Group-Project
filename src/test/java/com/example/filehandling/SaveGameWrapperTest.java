package com.example.filehandling;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.gamestate.Game;

class SaveGameWrapperTest {
	SaveGameWrapper wrapperUnderTest;
	Game gameHardCoded;
	Game savedGameFromFile;
	String path = "C:\\Users\\aosta\\OneDrive\\Documents\\Uni\\CS230\\A2\\FileHandlingTesting";
	String fileName = "savedgametest.txt";
	@BeforeEach
	void setUp() throws Exception {
		wrapperUnderTest = new SaveGameWrapper(path, fileName);
		gameHardCoded = new Game();
	}
	
	@Test
	void test() {
		try {
			setUp();
			savedGameFromFile = wrapperUnderTest.loadSavedGame();
			assertEquals(gameHardCoded, savedGameFromFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
