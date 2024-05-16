package game.gui;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    public Node createWeapon(double x, double y, String type) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(20);
        rectangle.setHeight(10);
        rectangle.setX(x);
        rectangle.setY(y);
        switch (type){
            case "Piercing Cannon":
                rectangle.setFill(Color.BLACK);
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
}
