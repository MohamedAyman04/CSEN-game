package game.gui;

import game.engine.exceptions.InsufficientResourcesException;

import game.engine.exceptions.InvalidLaneException;

import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.weapons.WeaponRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private static ArrayList<Node> errors;
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
    @FXML
    private Button B1;
    @FXML
    private Button B2;
    @FXML
    private Button B3;
    @FXML
    private Button B4;
    @FXML
    private Button B5;
    @FXML
    private Label Lane1;
    @FXML
    private Label Lane2;
    @FXML
    private Label Lane3;
    @FXML
    private Label Lane4;
    @FXML
    private Label Lane5;
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

    public void updateDangerLevel() {
        ArrayList<Integer> dangerLevels = model.DangerLevel();
        Lane1.setText("Danger Level: " + dangerLevels.get(0));
        Lane2.setText("Danger Level: " + dangerLevels.get(1));
        Lane3.setText("Danger Level: " + dangerLevels.get(2));
        if (dangerLevels.size() > 3) {
            Lane4.setText("Danger Level: " + dangerLevels.get(3));
            Lane5.setText("Danger Level: " + dangerLevels.get(4));
        }
    }

    public void updateAll() {
        showTitanOnLane(lanes.get(0).getHeight());
        updateDangerLevel();
        updatePhase();
        updateResources();
        changeScore();
        updateCurrentTurn();
    }

    public void pass() {
        model.pass();
        updateAll();
    }

    public void showTitanOnLane(double y) {
        PriorityQueue<Lane> controlLanes = new PriorityQueue<>(model.getLanes());
        int counter = 0;
        root.getChildren().removeAll(removal);
        while(!controlLanes.isEmpty()) {
            Lane lane = controlLanes.poll();
            PriorityQueue<Titan> temp2 = new PriorityQueue<>();
            while (!lane.getTitans().isEmpty()) {
                Titan titan = lane.getTitans().poll();
                temp2.add(titan);
                if (titan != null) {
                    Node node;
                    if (titan instanceof PureTitan)
                        node = view.spawnTitans(titan.getDistance(), lanes.get(counter).getLayoutY() + (y/5), titan.getHeightInMeters(), 1);
                    else if (titan instanceof AbnormalTitan)
                        node = view.spawnTitans(titan.getDistance(), lanes.get(counter).getLayoutY() + (y/5), titan.getHeightInMeters(), 2);
                    else if (titan instanceof ArmoredTitan)
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
            counter++;
        }
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


    public void handle_create_weapon_List(ActionEvent event) {
        HashMap<Integer, WeaponRegistry> w =  model.getWeaponShop();
        ListView<Object> listView = new ListView<>();
        ArrayList<Object> arr = new ArrayList<>();

        VBox r = new VBox();
        for (int i = 0; i < w.size(); i++) {
            WeaponRegistry item = w.get(i+1);
            String weapon = "Name: " + item.getName();
            String type;
            switch (i+1){
                case 1:
                    type = "Piercing Cannon";
                    break;
                case 2:
                    type = "Sniper Cannon";
                    break;
                case 3:
                    type="VolleySpread Cannon";
                    break;
                default:
                    type="Wall trap";
            }
            weapon += ", type: " + type + ", Price: " + item.getPrice() + ", Damage: " + item.getDamage();
            Button b = new Button(weapon);
            int code = i+1;
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event1) {
                    HBox h = new HBox();
                    h.setLayoutX(250);
                    h.setLayoutY(100);
                    try {
                        Button btn = (Button) event.getSource();
                        String id = btn.getId();
                        PriorityQueue<Lane> temp = new PriorityQueue<>(model.getLanes());
                        Lane[] array = temp.toArray(new Lane[0]);
                        int x = 180;
                        double y = lanes.get(0).getHeight();
                        switch (id) {
                            case "B1":
                                model.purchaseWeapon(code, array[0]);
                                root.getChildren().add(view.createWeapon(x, lanes.get(0).getLayoutY() + (y/5), type));
                                break;
                            case "B2":
                                model.purchaseWeapon(code, array[1]);
                                root.getChildren().add(view.createWeapon(x, lanes.get(1).getLayoutY() + (y/5), type));
                                break;
                            case "B3":
                                model.purchaseWeapon(code, array[2]);
                                root.getChildren().add(view.createWeapon(x, lanes.get(2).getLayoutY() + (y/5), type));
                                break;
                            case "B4":
                                model.purchaseWeapon(code, array[3]);
                                root.getChildren().add(view.createWeapon(x, lanes.get(3).getLayoutY() + (y/5), type));
                                break;
                            default:
                                model.purchaseWeapon(code, array[4]);
                                root.getChildren().add(view.createWeapon(x, lanes.get(4).getLayoutY() + (y/5), type));
                        }
                        for (Lane lane: temp) {
                            System.out.println(lane.getWeapons());
                        }
                        System.out.println("-------------------");
                        root.getChildren().remove(h);
                        updateAll();
                    } catch (InvalidLaneException | InsufficientResourcesException e) {
                        Label l = new Label(e.getMessage());
                        l.setTextFill(Color.RED);
                        l.setFont(Font.font("Verdana", 25));
                        h.getChildren().add(l);
                        root.getChildren().add(h);
                    }
                    r.getChildren().remove(listView);
                }
            });
            arr.add(i, b);
        }
        ObservableList<Object> items = FXCollections.observableArrayList(arr);
        listView.setItems(items);
        listView.setPrefSize(480, 150);
        r.getChildren().add(listView);
        root.getChildren().add(r);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("intro.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        view = new View();
        removal = new ArrayList<>();
        errors = new ArrayList<>();
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
