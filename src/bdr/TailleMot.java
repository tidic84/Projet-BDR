package bdr;

import java.util.Vector;

public class TailleMot implements Calculer {

    public int calculer(Corpus corpus) {
        Vector<Document> colDocuments = corpus.getCollDocuments();
        int totalmot = 0;
        for (Document document : colDocuments) {
            int n = 0;
            for (Mot mot : document.getListMot()) {
                n++;
            }
            totalmot += n;
        }
        return totalmot;
    }
}

