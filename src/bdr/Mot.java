package bdr;

import java.util.Objects;

public class Mot {

    @Override
    public String toString() {
        return mot;
    }

    private String mot = new String();

    public Mot(String mot) {
        if (mot == null) {
            this.mot = "";
            return;
        }
        this.mot = mot;
    }

    public String getMot() {
        return mot;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Mot)) return false;
        Mot mot1 = (Mot) o;
        return Objects.equals(mot, mot1.mot);
    }


}
