package gui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lobby.EntryPoint;
import oczko.OczkoGame;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Menu implements CustomScene {

    Scene scene;
    VBox grid = new VBox();

    private final Button createNewRoom = new Button("Nowa gra w oczko");

    int width = 400;
    int height = 400;

    private Stage stage;
    EntryPoint ep;

    public Menu(Stage stage, EntryPoint ep) throws RemoteException {
        this.stage = stage;
        this.ep = ep;

        grid.setAlignment(Pos.CENTER);
        grid.setSpacing(25);
        grid.setPadding(new Insets(25, 25, 25, 25));

        scene = new Scene(grid, width, height);

        createNewRoom.setOnAction(e -> {
            try {
                ep.create();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            loadRooms();
        });

        loadRooms();

    }

    private void joinRoom(int roomNo) throws FileNotFoundException, RemoteException, MalformedURLException, NotBoundException {
        System.out.println("Joining " + roomNo);
        OczkoGame game = (OczkoGame) Naming.lookup("rmi://localhost:1900" + "/game/" + roomNo);

        /*   !!
        OczkoGameScreen gs = new OczkoGameScreen(game);
        stage.setScene(gs.getScene());
        */// !!
    }

    private void loadRooms() {
        List<Integer> rooms = null;
        try {
            rooms = ep.list(" ");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(rooms);

        List<Button> buttons = new ArrayList<>();
        for(int roomNo : rooms) {
            Button b = new Button("Pokój " + roomNo);

            b.setOnAction(e-> {
                try {
                    joinRoom(roomNo);
                    // TODO: Error handling!
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                } catch (NotBoundException ex) {
                    ex.printStackTrace();
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            });

            buttons.add(b);

        }

        buttons.add(createNewRoom);


        grid.getChildren().clear();
        grid.getChildren().addAll(buttons);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}