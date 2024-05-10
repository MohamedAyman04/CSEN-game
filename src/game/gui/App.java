package game.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Intro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Attack on Titan: Utopia");
        Image icon = new Image("attack-on-titan-icon.jpg");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
