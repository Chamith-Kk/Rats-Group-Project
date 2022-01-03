package com.example;

import java.util.ArrayList;

import com.example.gamestate.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class makes newPlayer window.
 *
 * @author Jan Michalec.
 *
 * @since 2021/11/24
 *
 */
public class NewPlayerController {

	@FXML Button submit;
	@FXML TextField textField;
	@FXML Pane rootPane;
	Player newPlayer;
	ArrayList<Player> players;
	
	public void initialize() {
		submit.setOnAction(e ->  handleSubmitButtonAction());
	}

	/**
	 * Method returns Collection of Player already existing.
	 * @param playersToDisplay collection of Player.
	 */
	public void getList(ArrayList<Player> playersToDisplay) {
		this.players = playersToDisplay;
	}

	/**
	 * Method gets new player and return to window.
	 */
	public void handleSubmitButtonAction() {
		newPlayer = new Player(textField.getText(),1);
		players.add(newPlayer);
		closeWindow();
	}

	/**
	 * Method closes window.
	 */
	private void closeWindow() {
		Stage stage = (Stage) rootPane.getScene().getWindow();
	    stage.close();
	}
}
