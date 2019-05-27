package game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class GameServer extends UnicastRemoteObject implements Game {

    private int maxPlayers;
    private Client[] clients;
    Client host;

    protected GameServer(int maxPlayers) throws RemoteException {
        this.maxPlayers = maxPlayers;
        clients = new Client[maxPlayers];
    }

    @Override
    public Client getClient() throws RemoteException {
        for (int i = 0; i < maxPlayers; i++) {
            if (clients[i] == null) {
                clients[i] = getNewClient();
                onJoin(i);
                if (host == null) {
                    host = clients[i];
                }
                return clients[i];
            }
        }
        return null;
    }

    @Override
    public void disconnect(Client c) throws RemoteException {
        clients[getClientId(c)] = null;
    }

    @Override
    public int getClientId(Client c) throws RemoteException {
        for (int i = 0; i < maxPlayers; i++) {
            if (clients[i] == c) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean step(Client c, int action) throws RemoteException {
        int id = getClientId(c);
        if (id != getCurrentPlayerId()) return false;
        if (!step(id, action)) return false;
        if (isGameOver()) {
            onGameOver();
        }
        return true;
    }

    @Override
    public boolean isClientsTurn(Client c) throws RemoteException {
        return getCurrentPlayerId() == getClientId(c);
    }

    @Override
    public CardSet[] getPlayersCards(Client c) throws RemoteException {
        return getPlayersCards(getClientId(c));
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getPlayersNumber() {
        int count = 0;

        for (int i = 0; i < maxPlayers; i++) {
            if (clients[i] != null) count++;
        }

        return count;
    }

    public boolean isInGame(int id) {
        return clients[id] != null;
    }

    public Client getNewClient() throws RemoteException {
        return new GameClient(this);
    }

    public abstract void onJoin(int id);

    public abstract int nextPlayer();

    public abstract void reset();

    public abstract boolean step(int id, int action);

    public abstract void onGameOver();

    public abstract CardSet[] getPlayersCards(int id);

}
