package bdr.corpus;

import java.util.Vector;

public class TailleMot {

    /**
     * Méthode permettant de calculer le nombre de mot dans le corpus
     * @param corpus Le corpus
     * @return le nombre de mots
     */
    public int calculer(Corpus corpus) {
        Vector<Document> colDocuments = corpus;
        int totalmot = 0;
        for (Document document : colDocuments) {
            totalmot += document.size();
        }
        return totalmot;
    }
}

