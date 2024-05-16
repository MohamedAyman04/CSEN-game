module CSEN.game {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires junit;
    requires javafx.media;

    exports game.tests;
    exports game.gui;
    exports game.engine;
    exports game.engine.lanes;
    exports game.engine.weapons;
    exports game.engine.exceptions;
    exports game.engine.weapons.factory;
    opens game.gui;
}