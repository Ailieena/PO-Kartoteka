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

public class Main extends Application {

    int width = 900;
    int height = 600;
    String title = "Kartoteka";

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GameScreen gs = new GameScreen();
        Menu menu = new Menu(stage, gs);
        Login login = new Login(stage, menu);
        stage.setScene(login.getScene());
        stage.setTitle(title);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}