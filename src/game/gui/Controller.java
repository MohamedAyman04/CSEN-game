package game.gui;

import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import game.engine.weapons.factory.WeaponFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;



public class Controller implements Initializable {

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Stage stage;
    private Scene scene;
    private Model model;
    private static ArrayList<Rectangle> lanes;
    private static ArrayList<Node> removal;
    private static AnchorPane root;
    private View view;
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
        double distance = model.getTitanSpawnDistance();
        double h = lanes.get(0).getHeight();
        showTitanOnLane(distance, h);
        updatePhase();
        updateResources();
        changeScore();
        updateCurrentTurn();
    }

    public void showTitanOnLane(double distance, double y) {
        PriorityQueue<Lane> controlLanes = model.getLanes();
        PriorityQueue<Lane> temp = new PriorityQueue<>();
        int counter = 0;
        while (!removal.isEmpty()) {
            root.getChildren().remove(removal.removeLast());
        }
        while(!controlLanes.isEmpty()) {
            Lane lane = controlLanes.poll();
            PriorityQueue<Titan> temp2 = new PriorityQueue<>();
            System.out.println(lane.getTitans().size());
            while (!lane.getTitans().isEmpty()) {
                Titan titan = lane.getTitans().poll();
                temp2.add(titan);
                if (titan != null) {
                    Node node;
                    if (titan instanceof PureTitan)
                        node = view.spawnTitans(titan.getDistance(), lanes.get(counter).getLayoutY() + (y/5), titan.getHeightInMeters(), 1);
                    else if (titan instanceof ArmoredTitan)
                        node = view.spawnTitans(titan.getDistance(), lanes.get(counter).getLayoutY() + (y/5), titan.getHeightInMeters(), 2);
                    else if (titan instanceof AbnormalTitan)
                        node = view.spawnTitans(titan.getDistance(), lanes.get(counter).getLayoutY() + (y/5), titan.getHeightInMeters(), 3);
                    else
                        node = view.spawnTitans(titan.getDistance(), lanes.get(counter).getLayoutY() + (y/5), titan.getHeightInMeters(), 4);
                    removal.add(node);
                    root.getChildren().add(node);
                }
            }
            while(!temp2.isEmpty()) {
                lane.addTitan(temp2.poll());
            }
            temp.add(lane);
            counter++;
        }
        while (!temp.isEmpty())
            controlLanes.add(temp.poll());
    }

    public void startGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        lanes = new ArrayList<>();
        for (int i=0; i<model.getNumLanes(); i++) {
            lanes.add((Rectangle) root.getChildren().get(i));
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void handel_create_weapon_List(ActionEvent event) throws IOException {
        HashMap w =  model.getWeaponShop();
        ListView<Button> listView = new ListView<>();
        Button b ;
        ArrayList arr = new ArrayList<>();
        for (int i = 0; i < w.size(); i++) {
            WeaponRegistry item = (WeaponRegistry) w.get(i+1);
            String weapon = "Name: "+item.getName()+" Damage: "+item.getDamage()+" Price: "+item.getPrice();
            String type="";
            switch (i+1){
                case 1: type="Piercing Cannon";
                break;
                case 2: type="Sniper Cannon";
                break;
                case 3: type="VolleySpread Cannon";
                break;
                case 4: type="Wall trap";
                break;
            }
            weapon = weapon+ " type: "+type;
            b = new Button(weapon);
            arr.add(i,b);
        }
        ObservableList items = FXCollections.observableArrayList(
                arr
        );
        listView.setItems(items);
        listView.setPrefSize(150, 150);
        VBox r = new VBox();
        r.getChildren().add(listView);
        root.getChildren().add(r);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = stage.getScene();
        scene.setRoot(root);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("intro.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        view = new View();
        removal = new ArrayList<>();
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
            System.out.println(e.getMessage());
        }
    }
}
