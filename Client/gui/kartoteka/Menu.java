package kartoteka;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Menu implements CustomScene {

    Scene scene;
    GridPane grid;

    int width = 400;
    int height = 400;

    Menu(Stage stage, GameScreen gs) {
        grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(25);
        grid.setPadding(new Insets(25, 25, 25, 25));

        scene = new Scene(grid, width, height);

        Button b1 = new Button("Oczko");
        Button b2 = new Button("Gra 2");
        Button b3 = new Button("Gra 3");
        Button b4 = new Button("Gra 4");

        b1.setMinWidth(80);
        b2.setMinWidth(80);
        b3.setMinWidth(80);
        b4.setMinWidth(80);

        grid.add(b1, 0, 0);
        grid.add(b2, 0, 1);
        grid.add(b3, 1, 0);
        grid.add(b4, 1, 1);

        b1.setOnAction(e->{
            stage.setScene(gs.getScene());
        });


    }

    @Override
    public Scene getScene() {
        return scene;
    }
}