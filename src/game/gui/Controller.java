package game.gui;

import game.engine.exceptions.InsufficientResourcesException;

import game.engine.exceptions.InvalidLaneException;

import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.weapons.WeaponRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
    @FXML
    private ProgressBar wall1;
    @FXML
    private ProgressBar wall2;
    @FXML
    private ProgressBar wall3;
    @FXML
    private ProgressBar wall4;
    @FXML
    private ProgressBar wall5;
    @FXML
    private Label gameOver;
    @FXML
    private Label finalScore;
    @FXML
    private Button startMenu;
    private static boolean easy;
    private static double[] weaponDistance = {100, 100, 100, 100, 100};

    public void easyGameMode(ActionEvent event) throws IOException {
        easy = true;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("easy.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        model = new Model(3, 250);
        for (int i=0; i<model.getLanes().size(); i++) {
            lanes.add((Rectangle) root.getChildren().get(i));
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void hardGameMode(ActionEvent event) throws IOException {
        easy = false;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        model = new Model(5, 125);
        for (int i=0; i<model.getLanes().size(); i++) {
            lanes.add((Rectangle) root.getChildren().get(i));
        }
        scene = new Scene(root);
        stage.setScene(scene);
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

    public void instructions(ActionEvent event) throws IOException {
        mediaPlayer.stop();
        mediaPlayer.dispose();
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("Instructions.fxml"));
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

    public void updateWallHealth() {
        ArrayList<Lane> controlLanes = new ArrayList<>(model.getLanes());
        ProgressBar[] arr = new ProgressBar[] {wall1, wall2, wall3, wall4, wall5};
        double y = lanes.get(0).getHeight();
        for (int i=0; i<controlLanes.size(); i++) {
            Lane lane = controlLanes.get(i);
            double progress = (double) lane.getLaneWall().getCurrentHealth() / 10000;
            arr[i].setProgress(progress);
            if (progress < 0.3333333) {
                arr[i].setStyle("-fx-accent: red");
            } else if (progress < 0.666666) {
                arr[i].setStyle("-fx-accent: yellow");
            }
            if (lane.isLaneLost()) {
                Node node = view.laneLost(400, lanes.get(i).getLayoutY() + (y/5));
                root.getChildren().add(node);
            }
        }
    }

    public void isGameOver() {
        if (model.isGameOver()) {
            gameOver.setVisible(true);
            gameOver.toFront();
            gameOver.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            gameOver.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
            finalScore.setText("final score: " + model.getScore());
            finalScore.setVisible(true);
            finalScore.toFront();
            startMenu.setVisible(true);
            startMenu.toFront();
        }
    }

    public void updateAll() {
        root.getChildren().removeAll(errors);
        showTitanOnLane(lanes.get(0).getHeight());
        updateDangerLevel();
        updatePhase();
        updateResources();
        changeScore();
        updateCurrentTurn();
        updateWallHealth();
        isGameOver();
    }

    public void pass() {
        model.pass();
        updateAll();
    }

    public void showTitanOnLane(double y) {
        ArrayList<Lane> controlLanes = new ArrayList<>(model.getLanes());
        int counter = 0;
        root.getChildren().removeAll(removal);
        for (Lane lane: controlLanes) {
            PriorityQueue<Titan> temp2 = new PriorityQueue<>();
            while (!lane.getTitans().isEmpty()) {
                Titan titan = lane.getTitans().poll();
                temp2.add(titan);
                if (titan != null) {
                    double distance = (titan.getDistance() * 4.69) + 231;
                    Node node;
                    ProgressBar progressBar = new ProgressBar(1);
                    progressBar.setStyle("-fx-accent: green");
                    if (titan instanceof PureTitan) {
                        node = view.spawnTitans(distance, lanes.get(counter).getLayoutY() + (y / 5), titan.getHeightInMeters(), 1);
                        progressBar.setProgress((double) titan.getCurrentHealth() / 100);
                        if ((double) titan.getCurrentHealth() / 100 < 0.333333) {
                            progressBar.setStyle("-fx-accent: red");
                        } else if ((double) titan.getCurrentHealth() / 100 < 0.66666) {
                            progressBar.setStyle("-fx-accent: yellow");
                        }
                    } else if (titan instanceof AbnormalTitan) {
                        node = view.spawnTitans(distance, lanes.get(counter).getLayoutY() + (y / 5), titan.getHeightInMeters(), 2);
                        progressBar.setProgress((double) titan.getCurrentHealth() / 100);
                        if ((double) titan.getCurrentHealth() / 100 < 0.333333) {
                            progressBar.setStyle("-fx-accent: red");
                        } else if ((double) titan.getCurrentHealth() / 100 < 0.66666) {
                            progressBar.setStyle("-fx-accent: yellow");
                        }
                    } else if (titan instanceof ArmoredTitan) {
                        node = view.spawnTitans(distance, lanes.get(counter).getLayoutY() + (y / 5), titan.getHeightInMeters(), 3);
                        progressBar.setProgress((double) titan.getCurrentHealth() / 200);
                        if ((double) titan.getCurrentHealth() / 200 < 0.333333) {
                            progressBar.setStyle("-fx-accent: red");
                        } else if ((double) titan.getCurrentHealth() / 200 < 0.66666) {
                            progressBar.setStyle("-fx-accent: yellow");
                        }
                    } else {
                        node = view.spawnTitans(distance, lanes.get(counter).getLayoutY() + (y / 5), titan.getHeightInMeters(), 4);
                        progressBar.setProgress((double) titan.getCurrentHealth() / 1000);
                        if ((double) titan.getCurrentHealth() / 1000 < 0.333333) {
                            progressBar.setStyle("-fx-accent: red");
                        } else if ((double) titan.getCurrentHealth() / 1000 < 0.66666) {
                            progressBar.setStyle("-fx-accent: yellow");
                        }
                    }
                    progressBar.setLayoutX(distance - 10);
                    progressBar.setLayoutY(lanes.get(counter).getLayoutY() + (y / 5) + 20);
                    progressBar.setPrefSize(20, 13);
                    removal.add(node);
                    removal.add(progressBar);
                    root.getChildren().add(node);
                    root.getChildren().add(progressBar);
                }
            }
            while(!temp2.isEmpty()) {
                lane.addTitan(temp2.poll());
            }
            counter++;
        }
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
                        ArrayList<Lane> controlLanes = new ArrayList<>(model.getLanes());
                        double x = 180;
                        double y = lanes.get(0).getHeight();
                        switch (id) {
                            case "B1":
                                model.purchaseWeapon(code, controlLanes.get(0));
                                root.getChildren().add(view.createWeapon(x + weaponDistance[0], lanes.get(0).getLayoutY() - (y/4), type));
                                weaponDistance[0] += 30;
                                break;
                            case "B2":
                                model.purchaseWeapon(code, controlLanes.get(1));
                                root.getChildren().add(view.createWeapon(x + weaponDistance[1], lanes.get(1).getLayoutY() - (y/4), type));
                                weaponDistance[1] += 30;
                                break;
                            case "B3":
                                model.purchaseWeapon(code, controlLanes.get(2));
                                root.getChildren().add(view.createWeapon(x + weaponDistance[2], lanes.get(2).getLayoutY() - (y/4), type));
                                weaponDistance[2] += 30;
                                break;
                            case "B4":
                                model.purchaseWeapon(code, controlLanes.get(3));
                                root.getChildren().add(view.createWeapon(x + weaponDistance[3], lanes.get(3).getLayoutY() - (y/4), type));
                                weaponDistance[3] += 30;
                                break;
                            default:
                                model.purchaseWeapon(code, controlLanes.get(4));
                                root.getChildren().add(view.createWeapon(x + weaponDistance[4], lanes.get(4).getLayoutY() - (y/4), type));
                                weaponDistance[4] += 30;
                        }
                        root.getChildren().remove(h);
                        updateAll();
                    } catch (InvalidLaneException | InsufficientResourcesException e) {
                        Label l = new Label(e.getMessage());
                        l.setTextFill(Color.RED);
                        l.setFont(Font.font("Verdana", 25));
                        h.getChildren().add(l);
                        root.getChildren().add(h);
                        errors.add(h);
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
        lanes = new ArrayList<>();
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
