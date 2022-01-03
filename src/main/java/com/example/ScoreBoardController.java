package com.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.gamestate.Player;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Class give us scoretable.
 * @author Cham Kotage.
 *
 */

public class ScoreBoardController implements Initializable {
        @FXML Button mainMenu;
        @FXML private ListView<String> high_scores;
        @FXML private Label scores_label;
        @FXML Text score;


        Player currentPlayer;
        ArrayList<Integer> scores = new ArrayList<>();

        /**
         * Method Get us ScoreBoard.
         * @throws Exception if error.
         */
        public void handle_main_btn() throws Exception{

            Parent scores_load = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Stage scores_loading =(Stage)mainMenu.getScene().getWindow();
            scores_loading.setScene(new Scene(scores_load,750,500));
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        high_scores.getItems().addAll(currentPlayer.getName() + " " +  String.valueOf(scores));
    }
}
