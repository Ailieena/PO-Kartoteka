package poker;

import game.CardSet;

import java.rmi.Naming;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        try {
            PokerServer g = (PokerServer) Naming.lookup("rmi://localhost:1900/game");
            PokerClient c = (PokerClient) g.getClient();

            Scanner s = new Scanner(System.in);
            int k, id;

            while (true) {
                if (c.isGameActive()) {

                    if (c.isYourTurn()) {
                        CardSet[] cs = c.getPlayersCards();
                        id = c.getPlayersId();
                        System.out.println(c.getBoardCards());
                        System.out.println(cs[id]);
                        System.out.println("Pot: " + c.getPot() + ", Max bet: " + c.getMaxBet() + ", Coins: " + c.getCoins() + ", Current bet: " + c.getCurrentBet() + ", Round: " + c.getRound());
                        System.out.println("Twoj ruch: ");
                        k = s.nextInt();
                        c.step(k);
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
