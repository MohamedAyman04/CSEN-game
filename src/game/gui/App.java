package game.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader;
        if (new File("intro.mp4").exists()) {
            fxmlLoader = new FXMLLoader(Controller.class.getResource("Intro.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Controller.class.getResource("Instructions.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Attack on Titan: Utopia");
        if (getClass().getResource("/assets/attack-on-titan-icon.jpg") != null) {
            Image icon = new Image(String.valueOf(getClass().getResource("/assets/attack-on-titan-icon.jpg")));
            stage.getIcons().add(icon);
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
