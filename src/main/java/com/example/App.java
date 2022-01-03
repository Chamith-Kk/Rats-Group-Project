package com.example;


import com.example.gamestate.Game;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

	// Constants for the main window
	private static final int MAIN_WINDOW_WIDTH = 600;
	private static final int MAIN_WINDOW_HEIGHT = 600;
	private static final String WINDOW_TITLE = "Rat Game";
	Game score;

	public void start(Stage mainStage) {
		try {

			// Load the main scene.
			BorderPane root = (BorderPane)(loadFXML("MainMenu"));
			Scene scene = new Scene(root, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
			
			// Place the main scene on stage and show it.
			mainStage.setResizable(false);
			mainStage.setScene(scene);
			mainStage.setTitle(WINDOW_TITLE);
			mainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}