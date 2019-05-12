package client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginWindow {

    private final ClientApp app;

    @FXML
    private TextField loginText;

    @FXML
    private void handleLogin() throws IOException {
        System.out.println("LOGIN! " + loginText.getText());
        app.startRoomSelect();

    }

    public LoginWindow(ClientApp app) {
        this.app = app;
    }

}
