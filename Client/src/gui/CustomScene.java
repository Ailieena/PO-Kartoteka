package gui;

import javafx.scene.Scene;

import java.io.FileNotFoundException;

public interface CustomScene {
    Scene getScene() throws FileNotFoundException;
}
