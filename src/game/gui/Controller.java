package game.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Stage stage;
    private Scene scene;
    private Model model;
    @FXML
    private Label Score;
    @FXML
    private Label Turn;
    @FXML
    private Label Phase;
    @FXML
    private Label Resources;
    private boolean easy = true;

    public void easyGameMode(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Instructions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        easy = true;
        stage.show();
    }

    public void hardGameMode(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Instructions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        easy = false;
        stage.show();
    }

    public void startMenu(ActionEvent event) throws IOException {
        mediaPlayer.stop();
        mediaPlayer.dispose();
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Start_Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void changeScore() {
        Score.setText("Score: " + model.getScore());
    }

    public void updateCurrentTurn() {
        Turn.setText("Current Turn: " + model.getCurrentTurn());
    }

    public void updatePhase() {
        Phase.setText("Current Phase: " + model.getPhase());
    }

    public void updateResources() {
        Resources.setText("Resources: " + model.getCurrentResources());
    }

    public void pass() {
        model.pass();
        updatePhase();
        updateResources();
        changeScore();
        updateCurrentTurn();
    }

    public void startGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Game.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("intro.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        if (mediaView != null) {
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        }
        try {
            if (easy)
                model = new Model(3, 250);
            else
                model = new Model(5, 125);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
