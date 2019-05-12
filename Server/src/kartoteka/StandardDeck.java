package kartoteka;

import java.util.ArrayList;
import java.util.LinkedList;

public class StandardDeck extends Deck{

    public StandardDeck() {
        deck = new LinkedList<>();
        for(int i=0; i<4; i++) {
            for(int j=0; j<13; j++) {
                deck.add(new Card(j, i));
            }
        }
    }

}
