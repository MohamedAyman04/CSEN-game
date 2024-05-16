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
                rectangle.setFill(Color.WHITE);
                break;
            case 3:
                rectangle.setFill(Color.BLACK);
                break;
            default:
                rectangle.setFill(Color.YELLOW);
        }
        return rectangle;
    }
}
