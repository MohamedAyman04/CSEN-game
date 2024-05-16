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
    public Node bWeapon(double x, double y,int code) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(6);
        rectangle.setHeight(4);
        rectangle.setX(x);
        rectangle.setY(y);
        switch (code){
            case 1 :rectangle.setFill(Color.BLACK);
            case 2 :rectangle.setFill(Color.RED);
            case 3 :rectangle.setFill(Color.GREEN);
            case 4 :rectangle.setFill(Color.ORANGE);
        }
        return rectangle;
    }
}
