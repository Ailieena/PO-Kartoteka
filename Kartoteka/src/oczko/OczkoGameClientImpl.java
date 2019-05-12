package oczko;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

public class OczkoGameClientImpl extends UnicastRemoteObject implements OczkoGameClient {

    private Consumer<OczkoTurnDTOInterface> onNextTurn;

    public OczkoGameClientImpl(Consumer<OczkoTurnDTOInterface> onNextTurn) throws RemoteException {
        super();
        this.onNextTurn = onNextTurn;
    }

    @Override
    public void nextTurn(OczkoTurnDTOInterface dto) throws RemoteException {
        onNextTurn.accept(dto);
    }


    @Override
    public String getId() throws RemoteException {
        return "1";
    }
}
