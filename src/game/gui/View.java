package game.gui;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class View {
    public Node spawnTitans(double x, double y, double height) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(5);
        rectangle.setHeight(height);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(Color.RED);
        return rectangle;
    }
    public Node createWeapon(double x, double y,String type) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(6);
        rectangle.setHeight(4);
        rectangle.setX(x);
        rectangle.setY(y);
        switch (type){
            case "Piercing Cannon" :rectangle.setFill(Color.BLACK);
            case "Sniper Cannon" :rectangle.setFill(Color.RED);
            case "VolleySpread Cannon" :rectangle.setFill(Color.GREEN);
            case " Wall trap" :rectangle.setFill(Color.ORANGE);
        }
        return rectangle;
    }
}
