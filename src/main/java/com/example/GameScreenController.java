package com.example;

import java.io.IOException;
import java.util.List;

import com.example.gamestate.Board;
import com.example.gamestate.Game;
import com.example.gamestate.Player;
import com.example.gamestate.Terrain;
import com.example.gamestate.Tile;
import com.example.items.Item;
import com.example.rats.Rat;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class accountable for displaying and logic of game. 
 * @author Jan Michalec, Toby Dickenson, Fjoraldo Gordoni
 *
 */
public class GameScreenController{

    @FXML
    AnchorPane gamePane;
    @FXML
    AnchorPane viewPane;
    @FXML
    VBox inventoryPane;
    @FXML
    ListView<ImageView> inventory = new ListView<>();
    @FXML
    Player currentPlayer;
    @FXML
    Button exitButton;
    @FXML
    Button pauseButton;
    @FXML
    Button showScoreBoard;
    @FXML
    Text score;
    @FXML
    Button resumeButton;
    @FXML
  	ProgressBar progressBar;
    
    //Local copy of game
    Game game;
    
    //How many ticks per second.
    public static final int TICKS_PER_SECOND = 2;

    //Size of tile in pixels.
    private final static int TILE_SIZE = 32;
    
    //True if is paused.
    public boolean isPaused = false;
    
    //True if game ended.
    public boolean isEnded = false;
    
    //How many ticks have passed.
    static Timeline tickTimeline;
    
    //Final score after finished game.
    public static int finalScore;

    public static boolean hasGameBeenWon;

    /**
     * Method shows score board.
     * @throws Exception if error.
     */
    public void showScoreBoard() throws Exception {

        FXMLLoader score_loader = new FXMLLoader(getClass().getResource("ScoreBoard.fxml"));
        ScoreBoardController x = new ScoreBoardController();
        x.scores.add(game.getScore());
        x.currentPlayer = currentPlayer;
        score_loader.setController(x);
        Stage stage = (Stage) showScoreBoard.getScene().getWindow();
        AnchorPane scoreRoot = score_loader.load();
        Scene scene = new Scene(scoreRoot);
        stage.setScene(scene);
        stage.show();

    }
    
    /**
     * Method pause game.
     * @throws IOException if error.
     */
    public void pauseGame() throws IOException {
        if(!this.isPaused){
            tickTimeline.pause();
            System.out.println("game has paused");
            pauseButton.setText("Resume");
            this.isPaused = true;
        } else {
            tickTimeline.play();
            pauseButton.setText("Pause");
            System.out.println("game has resumed");
            this.isPaused = false;
        }
    }
    /**
     * Passes score to local variable.
     */
    public void pass_scores() {
        this.score.setText(String.valueOf(game.getScore()));
    }

    /**
     * Method returns final score.
     * @return int score.
     */
    public static void setFinalScore(){
        EndGameController.finalScore = finalScore;
        //return finalScore;
    }

    /**
     * Method place an item after event occurred. 
     * Item placed on selected Tile.
     * @param event event that occurred.
     */
    private void placeItem(MouseEvent event) {
        int selectedIndex = inventory.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        Item selectedItem = game.getInventory().get(selectedIndex);
        System.out.println(selectedItem);

        //place the selected item from the inventory on the tile that was clicked.
        int x = (int) (event.getX() / 32);
        int y = (int) (event.getY() / 32);

        if (checkIfPath(game.getBoard().getTile(x, y))) {
            boolean itemPlaced = game.getBoard().tryPlaceItem(selectedItem, x, y);

            // System.out.println(itemPlaced + " " + selectedItem + " " + x + "," + y);
            //if the item was placed successfully remove it from the inventory
            if (itemPlaced) {
                System.out.println("item placed: " + x + "," + y);
                game.getInventory().remove(selectedIndex);
            }
        }
        refreshInventory();
        draw();
    }

    /**
     * Method checks if Given Tile is Path.
     * @param tile Tile to check.
     * @return boolean true if yes.
     */
    private boolean checkIfPath(Tile tile) {
        return tile.getTerrain().equals(Terrain.Path);
    }

    /**
     * Method refreshes inventory on screen.
     */
    private void refreshInventory() {
        int selectedIndex = inventory.getSelectionModel().getSelectedIndex();
        inventory.getItems().clear();
        for (Item item : this.game.getInventory()) {
        	
            if (item != null) {
            	ImageView itemImage = new ImageView(item.getIconPath());
                inventory.getItems().add(itemImage);
                //System.out.println(itemImage);
                displayImageView(itemImage, 1 ,1);
            }
        }
        inventory.getSelectionModel().select(selectedIndex);
        inventory.getFocusModel().focus(selectedIndex);
    }

    /**
     * Method calls other method to draw whole board.
     */
    private void draw() {
        Board board = game.getBoard();
        gamePane.getChildren().clear();
        this.score.setText(String.valueOf(game.getScore()));
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                //Draw Tiles
                Tile tile =  board.getTile(x, y);
                drawTileTerrain(tile, x, y);
                drawItemOnTile(tile.getItem(),x,y);
                if (tile.getTerrain() != Terrain.Tunnel) {
                    drawRatsOnTile(tile.getRats(), x, y);
                }
            }
        }
        refreshInventory();
        //	System.out.println("I have printed the board");
    }

    /**
     * Draws tile on scene and calls method to check if
     * anything else.
     *
     * @param tile Tile to be drawn.
     * @param x    coord x.
     * @param y    coord y.
     */
    private void drawTileTerrain(Tile tile, int x, int y) {
        ImageView tileIcon = new ImageView(tile.getIconPath());
        displayImageView(tileIcon, x, y);
    }

    /**
     * Draws item if any.
     *
     * @param item the item to be drawn at the given location.
     * @param x    coord x.
     * @param y    coord y.
     */
    private void drawItemOnTile(Item item, int x, int y) {
        if (item != null) {
            ImageView itemIcon = new ImageView(item.getIconPath());
            displayImageView(itemIcon, x, y);
        }
    }

    /**
     * Draws rats if any.
     *
     * @param rats the list of rats to be drawn at the given location.
     * @param x    coord x.
     * @param y    coord y.
     */
    private void drawRatsOnTile(List<Rat> rats, int x, int y) {
        for (Rat rat : rats) {
            ImageView ratIcon = new ImageView(rat.getIconPath());
            if(checkIfPath(game.getBoard().getTile(x, y))) {
            	displayImageView(ratIcon, x, y);
            }
        }
    }

    /**
     * Draws image on screen.
     *
     * @param icon image.
     * @param x    coord x.
     * @param y    coord y.
     */
    private void displayImageView(ImageView icon, int x, int y) {
        icon.setY(y * TILE_SIZE);
        icon.setX(x * TILE_SIZE);
        gamePane.getChildren().add(icon);
    }

    /**
     * Method to exit to MainMenu.
     * @throws IOException if error.
     */
    public void exitButton() throws IOException {
        //TODO Save game
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) exitButton.getScene().getWindow();
        BorderPane gameRoot = loader.load();
        Scene scene = new Scene(gameRoot);
        stage.setScene(scene);
        stage.show();
        tickTimeline.stop();
    }

    /**
     * Method updates ProgressBar according to population.
     */
    public void updateProgressBar(){
        double currentPopulation = this.game.getBoard().countPopulation();
        double maxPopulation = this.game.getPopulationLimit();
        double populationPercentage = currentPopulation / maxPopulation;
        progressBar.setProgress(populationPercentage);
        //System.out.println("Population limit" + game.getPopulationLimit());
        //System.out.println("The game population is: "+ populationPercentage);
    }

    public void initialize() {

        this.game = new Game();
        refreshInventory();
        draw();
            
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            try {
                tick();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
		tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();

        tickTimeline.play();

        gamePane.setOnMouseClicked(event -> {
            placeItem(event);
            event.consume();
        });
    }

    /**
     * Method gets ending screen of Game.
     * @throws IOException if error.
     */
    public void buildEndGameScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndGameScreen.fxml"));
        BorderPane profileRoot = fxmlLoader.load();

        Scene profileScene = new Scene(profileRoot, 312, 242);
        Stage editStage = new Stage();
        editStage.setScene(profileScene);
        editStage.setTitle("End of Game!");
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.show();
        finalScore = this.game.getScore();
        System.out.println(finalScore);

        Stage stage = (Stage) gamePane.getScene().getWindow();
        stage.close();
    }

    /**
     * Method iterate through game.
     * @throws IOException if error.
     */
    public void tick() throws IOException{
        updateProgressBar();
        draw();
        this.isEnded = this.game.iterate();
        if(this.isEnded){
            if(this.game.getBoard().countPopulation() == 0){
                hasGameBeenWon = true;
                System.out.println("YOU WON! : game ended");
                tickTimeline.stop();
                setFinalScore();
                buildEndGameScreen();

            } else {
                hasGameBeenWon = false;
                System.out.println("YOU LOST : game ended");
                tickTimeline.stop();
                setFinalScore();
                buildEndGameScreen();
            }

        }


	}
}
