package com.example.filehandling;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class LevelFileWrapperTest {
	protected String path;
	protected LevelFileWrapper levelFileWrapper;
	protected ArrayList<String> linesFromFile;
	protected ArrayList<String> lines;//as read from file in TxtFileWrapper.
	//uses example file data from design report.
	protected void setUp() {
		path = "C:\\Users\\aosta\\OneDrive\\Documents\\Uni\\CS230\\A2\\FileHandlingTesting";
		levelFileWrapper = new LevelFileWrapper(path, "Level1.txt");
		lines = new ArrayList<>();
		lines.add("10");
		lines.add("120");
		lines.add("2;4;8;16;32;64;128");
		lines.add("1,1,M;2,3,F;3,2,M;8,1,F");
		lines.add("15;7");
		lines.add("GGGGGGGGGGGGGGG");
		lines.add("GPPPTTPPPTTPPPG");
		lines.add("GGGPGGGGPGGPGPG");
		lines.add("GPPPGGPPPGGPGPG");
		lines.add("GPGGGGGGPGGPGPG");
		lines.add("GPPPTTPPPTTPPPG");
		lines.add("GGGGGGGGGGGGGGG");
		try {
			linesFromFile = levelFileWrapper.readFromFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@Test
	void testRead() {
		setUp();
		assert(lines.equals(linesFromFile));
	}

}
