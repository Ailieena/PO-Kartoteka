package poker;

import game.GameClient;

import java.rmi.RemoteException;

public class PokerGameClient extends GameClient implements PokerClient {

    private PokerServer game;

    protected PokerGameClient(PokerServer game) throws RemoteException {
        super(game);
        this.game = game;
    }

    @Override
    public int getPot() throws RemoteException {
        return game.getPot();
    }

    @Override
    public int[] getCoins() throws RemoteException {
        return game.getCoins(this);
    }

    @Override
    public int getMaxBet() throws RemoteException {
        return game.getMaxBet();
    }

    @Override
    public int[] getCurrentBet() throws RemoteException {
        return game.getCurrentBet(this);
    }

    @Override
    public int getRound() throws RemoteException {
        return game.getRound();
    }
}
