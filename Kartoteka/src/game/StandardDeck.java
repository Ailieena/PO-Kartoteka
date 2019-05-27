package game;

public class StandardDeck extends CardSet {

    public StandardDeck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                addCard(new Card(j, i));
            }
        }
    }
}
