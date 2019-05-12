package kartoteka;

public class Card {
    int value;
    int color;

    Enumeration enumeration;

    public Card() {
        value = color = 0;
        enumeration = Constants.standardEnumeration;
    }

    public Card(int value, int color) {
        this.value = value;
        this.color = color;
        enumeration = Constants.standardEnumeration;
    }

    public Card(int value, int color, Enumeration enumeration) {
        this.value = value;
        this.color = color;
        this.enumeration = enumeration;
    }

    public Card clone() {
        return new Card(this.value, this.color);
    }

    public int compareTo(Card c) {
        return this.value - c.value;
    }

    public String getValue() {
        return enumeration.getValue(value);
    }

    public String getColor() {
        return enumeration.getColor(color);
    }

    public String toString() {
        return getValue() + " " + getColor();
    }

    public static int compareValues(Card first, Card second) {
        return first.value - second.value;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Card)) return false;
        return (this.value == ((Card) o).value && this.color == ((Card) o).color);
    }
}
