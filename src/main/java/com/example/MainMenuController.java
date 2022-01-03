package com.example;
import java.io.IOException;
import java.util.ArrayList;

import com.example.filehandling.PlayerFileWrapper;
import com.example.gamestate.MOTD;
import com.example.gamestate.Player;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class makes MainMenu window.
 *
 * @author Jan Michalec.
 */
public class MainMenuController {

	@FXML
	public Button newPlayer;
	@FXML
	private Button createPlayer;
	@FXML
	private Button editPlayers;
	@FXML
	private Button newGame;
	@FXML
	private Button resumeGame;
	@FXML
	private Text messageBox;
	@FXML
	private ComboBox<Player> playerList;
	
	//Collection of players that are in the system.
	private ArrayList<Player> playersToDisplay = new ArrayList<>();
	
	//Method to keep time for MOTD.
	private Timeline tickTimeline;

	public void initialize() {
		
		PlayerFileWrapper readPlayer = new PlayerFileWrapper("data", "Players.txt");
		uploadSavedProfiles(readPlayer.loadPlayers());
		showMessage();
		tickTimeline = new Timeline(new KeyFrame(Duration.seconds(30), event -> showMessage()));
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		tickTimeline.play();
		checkButtons();
		
		playerList.setOnAction(e -> checkButtons());
	}

	/**
	 * Method shows Message Of the day on a screen.
	 */
	public void showMessage() {

		messageBox.setText(MOTD.getMOTD());
		messageBox.setWrappingWidth(250);

		TranslateTransition tt =  new TranslateTransition();
		tt.setDuration(Duration.seconds(3));
		tt.setNode(messageBox);
		tt.setToX(200);

		tt.setAutoReverse(true);
		tt.setCycleCount(Animation.INDEFINITE);
		tt.play();
	}
	/**
	 * Makes new Scene with game.
	 * @throws IOException if error.
	 */
	public void newGame() throws IOException {
		GameScreenController x = new GameScreenController();
		x.currentPlayer = playerList.getValue();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
		Stage stage =(Stage)newGame.getScene().getWindow();
		loader.setController(x);
		BorderPane gameRoot = loader.load();
		Scene scene = new Scene(gameRoot);
		stage.setUserData(playerList.getValue());
		stage.setScene(scene);
		stage.show();
		
	}
	/**
	 * Resumes game from save.
	 */
	public void resumeGame() {
		// TODO
	}
	
	/**
	 * Makes new window to create Profile
	 * @throws IOException
	 */
	public void createProfile() throws IOException {	
		
		// Create a FXML loader for loading the Player Control FXML file.
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewPlayer.fxml"));        
		BorderPane profileRoot = fxmlLoader.load();
		
		NewPlayerController newPlayerController = fxmlLoader.getController();
		newPlayerController.getList(playersToDisplay);
		
		Scene profileScene = new Scene(profileRoot, 250, 75);		    
		Stage editStage = new Stage();
		editStage.setScene(profileScene);
		editStage.setTitle("New profile");
		editStage.initModality(Modality.APPLICATION_MODAL);
		editStage.showAndWait();
		refreshProfilesList();
	}
	
	/**
	 * Uploads saved profiles to collection.
	 * @param savedPlayers collection to which save is done.
	 */
	private void uploadSavedProfiles(ArrayList<Player> savedPlayers) {
		playersToDisplay = savedPlayers;
		refreshProfilesList();
	}
	
	/**
	 * Upload profilesList to list on screen.
	 */
	private void refreshProfilesList() {
		// Clear the displayed list
		playerList.getItems().clear();
		for (Player p : playersToDisplay) {
			playerList.getItems().add(p);
		}
		checkButtons();
	}


	/**
	 * Check if buttons can be clicked.
	 */
	public void checkButtons() {
		if(playerList.getValue() == null) {
			newGame.setDisable(true);
			resumeGame.setDisable(true);
		} else {
			newGame.setDisable(false);
		}
		
		//Check if there is some saved game for this profile
		//Yeah sure we will implement it
	}
}
