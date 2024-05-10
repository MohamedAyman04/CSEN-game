module CSEN.game {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires junit;
    requires javafx.media;

    exports game.tests;
    exports game.gui;
    opens game.gui;
}