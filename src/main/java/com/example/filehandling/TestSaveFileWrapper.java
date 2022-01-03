package com.example.filehandling;

import java.io.FileNotFoundException;

import com.example.gamestate.Game;

public class TestSaveFileWrapper {
	
	public static void test() {
		String path = "C:\\Users\\aosta\\OneDrive\\Documents\\Uni\\CS230\\A2\\FileHandlingTesting";
		String fileName = "savedgametest.txt";
		SaveGameWrapper wrapperUnderTest = new SaveGameWrapper(path, fileName);
		Game gameHardCoded = new Game();
		try {
			Game savedGameFromFile = wrapperUnderTest.loadSavedGame();
			System.out.print(gameHardCoded.equals(savedGameFromFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		test();
	}
}
