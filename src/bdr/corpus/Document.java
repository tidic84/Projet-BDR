package bdr.corpus;

import java.util.ArrayList;

public class Document extends ArrayList<Mot> {

    @Override
    public String toString() {
        return "Document [titre=" + titre + ", listeDoc=" + super.toString() + "]";
    }

    private Mot titre;

    /**
     * Constructeur simple permettant d'attribuer le titre.
     * @param titre un objet mot contenant un titre
     */
    public Document(Mot titre) {
        super();
        if(titre == null) return;
        this.titre = titre;
    }

    /**
     * Permet d'ajouter un mot a l'arrayList
     * @param leMot un string quelconque
     */
    public void putMot(String leMot) {
        this.add(new Mot(leMot));
    }

    /**
     * Une méthode qui retourne le titre
     * @return l'attribut titre
     */
    public String getTitre(){
        return titre.toString();
    }
}

