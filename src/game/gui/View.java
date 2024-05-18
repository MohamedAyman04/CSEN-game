package game.gui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class View {
    public Node spawnTitans(double x, double y, double height, int code) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(5);
        rectangle.setHeight(height);
        rectangle.setX(x);
        rectangle.setY(y);
        switch (code) {
            case 1:
                rectangle.setFill(Color.RED);
                break;
            case 2:
                rectangle.setFill(Color.DARKGRAY);
                break;
            case 3:
                rectangle.setFill(Color.BLACK);
                break;
            default:
                rectangle.setFill(Color.DARKORANGE);
        }
        return rectangle;
    }

    public Label count(int count, double x, double y) {
        Label label = new Label("x " + count);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-weight: bold");
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }


    public Rectangle createWeapon(double x, double y, String type) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(20);
        rectangle.setHeight(10);
        rectangle.setX(x);
        rectangle.setY(y);
        switch (type){
            case "Piercing Cannon":
                rectangle.setFill(Color.PURPLE);
                break;
            case "Sniper Cannon":
                rectangle.setFill(Color.RED);
                break;
            case "VolleySpread Cannon":
                rectangle.setFill(Color.GREEN);
                break;
            default:
                rectangle.setFill(Color.ORANGE);
        }
        return rectangle;
    }

    public Node laneLost(double x, double y) {
        Label label = new Label("Lane Lost");
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setTextFill(Color.RED);
        label.setFont(Font.font("Verdana", 25));
        return label;
    }
}
