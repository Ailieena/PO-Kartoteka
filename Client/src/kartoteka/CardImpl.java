package kartoteka;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CardImpl extends UnicastRemoteObject implements Card  {
    private int value;
    private int color;

    private Enumeration enumeration;

    public CardImpl() throws RemoteException {
        super();
        value = color = 0;
        enumeration = Constants.standardEnumeration;
    }

    public CardImpl(int value, int color) throws RemoteException {
        super();
        this.value = value;
        this.color = color;
        enumeration = Constants.standardEnumeration;
    }

    public CardImpl(int value, int color, Enumeration enumeration) throws RemoteException {
        super();
        this.value = value;
        this.color = color;
        this.enumeration = enumeration;
    }

    public CardImpl clone() {
        try {
            return new CardImpl(this.value, this.color);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int compareTo(CardImpl c) {
        return this.value - c.value;
    }

    @Override
    public String getValue() {
        return enumeration.getValue(value);
    }

    @Override
    public String getColor() {
        return enumeration.getColor(color);
    }

    public String toString() {
        return getValue() + " " + getColor();
    }

    public static int compareValues(CardImpl first, CardImpl second) {
        return first.value - second.value;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof CardImpl)) return false;
        return (this.value == ((CardImpl) o).value && this.color == ((CardImpl) o).color);
    }
}
