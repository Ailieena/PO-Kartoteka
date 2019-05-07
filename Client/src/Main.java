import gui.Login;
import gui.Menu;
import gui.blackjack.OczkoGameScreen;
import javafx.application.Application;
import javafx.stage.Stage;
import lobby.EntryPoint;
import oczko.OczkoGameClient;
import oczko.OczkoGameClientImpl;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main extends Application {

    int width = 900;
    int height = 600;
    String title = "Kartoteka";

    @Override
    public void start(Stage stage) throws FileNotFoundException, RemoteException, NotBoundException, MalformedURLException {

        EntryPoint ep = (EntryPoint) Naming.lookup("rmi://localhost:1900"+
                "/start");

        Menu menu = new Menu(stage, ep);
        Login login = new Login(stage, menu);
        stage.setScene(login.getScene());
        stage.setTitle(title);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}