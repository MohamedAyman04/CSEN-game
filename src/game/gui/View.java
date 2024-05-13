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
}
