package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class controls EndGameScreen
 *
 * @author Fjoraldo Gordoni
 *
 * @since 2021/11/24
 *
 */
public class EndGameController {

	@FXML Button playAgain, scoreboard, exitToMainMenu;
	@FXML Text textScore;
	@FXML Pane rootPane;
	@FXML
	ImageView messageImage;
	public static int finalScore;
	
	
	public void initialize() {
		setFinalScoreMessage();
		if (GameScreenController.hasGameBeenWon){
			setMessageImageToWinning();
		} else {
			setMessageImageToLosing();
		}
	}


	public void setMessageImageToWinning(){
		messageImage.setImage(new Image("images/title/winningMessage.png"));
	}

	public void setMessageImageToLosing(){
		messageImage.setImage(new Image("images/title/losingMessage.png"));
	}


	public void setFinalScoreMessage(){
		//finalScore = GameScreenController.getFinalScore();

		this.textScore.setText("Your final score is: " + finalScore);
	}

	public void exitToMainMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) exitToMainMenu.getScene().getWindow();
        BorderPane menuRoot = loader.load();
        Scene scene = new Scene(menuRoot);
        stage.setScene(scene);
        stage.show();
		closeWindow();
		//System.out.println("exited to main menu");
	}

	public void playAgain() throws IOException {
		GameScreenController x = new GameScreenController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
        Stage stage = (Stage) playAgain.getScene().getWindow();
		loader.setController(x);
        BorderPane gameRoot = loader.load();
        Scene scene = new Scene(gameRoot);
        stage.setScene(scene);
        stage.show();
		closeWindow();
		
	}

	public void displayScoreboard() throws IOException{
		FXMLLoader score_loader = new FXMLLoader(getClass().getResource("ScoreBoard.fxml"));
        Stage stage = (Stage) scoreboard.getScene().getWindow();
        AnchorPane scoreRoot = score_loader.load();
        Scene scene = new Scene(scoreRoot);
        stage.setScene(scene);
        stage.show();

	}

	private void closeWindow() {
		Stage stage = (Stage) rootPane.getScene().getWindow();
	    stage.close();
	}
}
