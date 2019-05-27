package poker;

import game.Game;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerTest {
    public static void main(String[] args) {
        try {
            Game g = new Poker(5, 5, 10, 500);
            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900/game", g);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
