package bdr;

import java.util.ArrayList;

public class Document extends ArrayList<Mot> {

    @Override
    public String toString() {
        return "Document [titre=" + titre + ", listeDoc=" + super.toString() + "]";
    }

    private Mot titre;

    public Document(Mot titre) {
        // Faire une verif
        super();
        this.titre = titre;
    }

    public void putMot(String leMot) {
        // Faire verif
        this.add(new Mot(leMot));
    }

    public int nbMots() {
        int n = 0;
        for (Mot mot : this) {
            n++;
        }
        return n;
    }

    public ArrayList<Mot> getListMot() {
        return this;
    }

    public String getTitre(){
        return titre.toString();
    }
}

