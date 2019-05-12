package kartoteka;

import java.rmi.RemoteException;
import java.util.LinkedList;

public class StandardDeck extends Deck{

    public StandardDeck() throws RemoteException {
        deck = new LinkedList<>();
        for(int i=0; i<4; i++) {
            for(int j=0; j<13; j++) {
                deck.add(new CardImpl(j, i));
            }
        }
    }



}
