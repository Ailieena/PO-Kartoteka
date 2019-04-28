import java.rmi.RemoteException;

public class Oczko implements Game
{
	@Override
	public int getId() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGameType() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void join(GameClientInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void play() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCard(GameClientInterface dummyGame) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean gameOver() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasMaxPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
