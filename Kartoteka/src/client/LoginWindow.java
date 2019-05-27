package client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import remoteinterface.ServerAPI;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class LoginWindow {

    private final ClientApp app;

    @FXML
    private TextField loginText;

    @FXML
    private TextField serverIP;

    @FXML
    private void handleLogin() throws IOException {
        ServerAPI server;
        String serverAddr = "rmi://"+serverIP.getText()+":1900";

        try {
            server = (ServerAPI) Naming.lookup(serverAddr + "/start");
        } catch (Exception e) {
            System.out.println("Server: \""+serverIP.getText()+"\" lookup error");
            return;
        }

        app.setServer(server, serverAddr);

        System.out.println("LOGIN! " + loginText.getText());
        app.startRoomSelect();

    }

    public LoginWindow(ClientApp app) {
        this.app = app;
    }

}
