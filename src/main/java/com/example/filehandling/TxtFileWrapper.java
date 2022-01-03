package com.example.filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Anthony Stafford
 * @version 2.2
 *
 */
public class TxtFileWrapper {
    /**
     *
     */
    protected Scanner fileScanner; // used to read from file
    /**
     *
     */
    protected PrintWriter writer; // used to write to file
    /**
     *
     */
    protected String path; // where the file is stored
    /**
     *
     */
    protected String fileName;
    /**
     *
     */
    protected File file; // file to be written and read from
    /**
     *
     */
    private final String LINE_DELIMITER = "\n";

    /**
     * @param path - path of file to be written to
     */
    protected TxtFileWrapper(String path, String fileName) {
        URL url = getClass().getClassLoader().getResource(path
                    + "/" + fileName);
        if (url == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                this.file = new File(url.toURI());
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("file not valid");
            }
        }
        this.path = path;
        this.fileName = fileName;
        try {
            this.writer = new PrintWriter(new FileWriter(this.file, true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *
     * @return file path
     */
    protected String getFilePath() {
        return this.path.toString();
    }

    // !!!Assumes files stored in CSV format!!!
    /**
     * Reads data from file and stores it line by line.
     *
     * @return text read from file
     * @throws FileNotFoundException
     */
    protected ArrayList<String> readFromFile() throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        this.fileScanner = new Scanner(this.file);
        while (this.fileScanner.hasNext()) {
            lines.add(this.fileScanner.nextLine());
        }
        return lines;
    }

    //!!!This method may be obsolete. See player class for example.!!!
    //public abstract void writeToFile();
    //protected void writeToFile(String[] lines) {
    //int i;
    //for(i = 0;i < lines.length;i++) {
    //}
    //}
}
