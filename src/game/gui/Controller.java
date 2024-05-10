package game.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Application implements Initializable {
    @Override
    public void start (Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Intro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Attack on Titan: Utopia");
        Image icon = new Image("attack-on-titan-icon.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Stage stage;
    private Scene scene;
    private Model model;

    public void easyGameMode(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Instructions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        model = new Model(3, 250);
        stage.show();
    }

    public void hardGameMode(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Instructions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        model = new Model(5, 125);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("intro.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        if (mediaView != null) {
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
