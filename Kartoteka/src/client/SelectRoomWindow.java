package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import remoteinterface.ServerAPI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class SelectRoomWindow {

    @FXML
    private GridPane roomGrid;

    private final ClientApp app;
    private ServerAPI server;

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void createNewRoom() throws RemoteException {
        server.create();
        loadRooms();
    }

    public SelectRoomWindow(ClientApp app) {
        this.app = app;
    }

    public void addRoom(int roomID, EventHandler<ActionEvent> onClick) {
        Button b = new Button("Join");
        b.setOnAction(onClick);
        roomGrid.addRow(roomGrid.getRowCount(), new Label("Oczko " + roomID), b);
    }

    public void loadRooms() throws RemoteException {
        List<Integer> roomList = server.list(" ");
        System.out.println(roomList);
        roomGrid.getChildren().clear();
        for (int roomID : roomList) {
            this.addRoom(roomID, (ev) -> {
                try {
                    app.startGame(roomID);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } finally {
                    ev.consume();
                }
            });
        }
    }

    public void setServer(ServerAPI server) {
        this.server = server;
    }
}
