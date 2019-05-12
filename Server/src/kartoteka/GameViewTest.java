package kartoteka;

public class GameViewTest {
    public static void main(String[] args) {
        Deck std = new StandardDeck();
        Deck empty = new Deck();
        Game g = new Game(8, std, empty) {
            int actualPlayer = 0;
            @Override
            public void step(Player p, int move) {

            }

            @Override
            public int nextPlayer() {
                actualPlayer = (actualPlayer + 1) % 2;
                while(getPlayer(actualPlayer) == null) {
                    actualPlayer = (actualPlayer + 1) % 2;
                }
                return actualPlayer;
            }

            @Override
            public int getActualPlayer() {
                return actualPlayer;
            }

            @Override
            public boolean isGameOver() {
                return false;
            }

            @Override
            public int getWinner() {
                return -1;
            }
        };
        Player p1 = new Player(g);
        Player p2 = new Player(g);
        g.joinGame(p1, 0);
        g.joinGame(p2, 1);
        g.getDeck().shuffle();
        for(int i=0; i<5; i++)
            p1.addCard(g.getDeck().takeCard());
        for(int i=0; i<5; i++)
            p2.addCard(g.getDeck().takeCard());
        GameView gv = g.getGameView(0);

        for(int i : gv.getHandSizes()) {
            System.out.println(i);
        }
        System.out.println(gv.getPlayerHand());
        System.out.println(gv.getPlayerId());
        System.out.println(gv.getWinner());
        System.out.println(gv.isGameOver());

    }
}
