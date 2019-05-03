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

    public int compareTo(Card card) {
        return this.value - card.value;
    }

    public String toString() {
        return enumeration.values.get(this.value) + " " + enumeration.colors.get(this.color);
    }

    public static int compareValues(Card first, Card second) {
        return first.value - second.value;
    }
}
