package kartoteka;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameScreen implements CustomScene {

    Scene scene;

    int width = 900;
    int height = 600;

    GameScreen() throws FileNotFoundException {
        //Group group = new Group();
        //Card card = new Card("img/ace.png", group);
        Pane group = new Pane();
        group.setStyle("-fx-background-color: #3a9b71");
        Card card1 = new Card("Client/img/ace.png", group);
        card1.set(400, 300);

        card1.setMovable(true);

        scene = new Scene(group, width, height);

    }

    public Scene getScene() {
        return scene;
    }
}
