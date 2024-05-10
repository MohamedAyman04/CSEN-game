package game.gui;

import game.engine.Battle;

import java.io.IOException;

public class Model {
    public Model (int numLanes, int initialResources) throws IOException {
        Battle battle = new Battle(0, 0, 100, numLanes, initialResources);
    }
}
