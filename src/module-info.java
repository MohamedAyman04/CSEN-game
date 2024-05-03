module CSEN.game {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires junit;

    exports game.tests;
    exports game.gui;
    opens game.gui;
}