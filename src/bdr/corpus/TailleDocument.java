package bdr.corpus;

import java.util.Vector;

public class TailleDocument {

    /**
     * Calcule le nombre de document dans le corpus (classe et méthode un peu inutile puisqu'il nous suffit de faire corpus.size() pour récupérer le nombre de documents dans le corpus)
     * @param corpus Le corpus contenant les documents a compter
     * @return le nombre de documents dans le corpus
     */
    public int calculer(Corpus corpus) {
        int n = 0;
        Vector<Document> colDocuments = corpus;

        for (Document document : colDocuments) {
            n++;
        }
        return n;

    }

}
