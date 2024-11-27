package bdr;

import java.util.ArrayList;

public class Document extends ArrayList<Mot> {

    @Override
    public String toString() {
        return "Document [titre=" + titre + ", listeDoc=" + this + "]";
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

    public ArrayList<Mot> getListMot() {
        return this;
    }
}