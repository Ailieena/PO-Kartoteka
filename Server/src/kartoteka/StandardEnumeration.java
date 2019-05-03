package kartoteka;

import java.util.HashMap;

public class StandardEnumeration extends Enumeration {


    public StandardEnumeration() {

        colors = new HashMap<>();
        values = new HashMap<>();

        colors.put(0, "Pik");
        colors.put(1, "Kier");
        colors.put(2, "Karo");
        colors.put(3, "Trefl");

        values.put(0, "2");
        values.put(1, "3");
        values.put(2, "4");
        values.put(3, "5");
        values.put(4, "6");
        values.put(5, "7");
        values.put(6, "8");
        values.put(7, "9");
        values.put(8, "10");
        values.put(9, "Walet");
        values.put(10, "Dama");
        values.put(11, "Król");
        values.put(12, "As");
    }
}
