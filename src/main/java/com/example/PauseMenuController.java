package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Method Pauses Game.
 * @author Cham Kotage, Fjoraldo Gordoni.
 *
 */
public class PauseMenuController {
    @FXML Button resumeButton;

    /**
     * Method Resume game.
     * @throws Exception if error.
     */
    public void handle_resume_btn() throws Exception{
            Parent resume_load = FXMLLoader.load(getClass().getResource("GameScreen.fxml"));

            Stage resume_window =(Stage)resumeButton.getScene().getWindow();
            resume_window.setScene(new Scene(resume_load,750,500));
            GameScreenController.tickTimeline.play();
        }
    }



