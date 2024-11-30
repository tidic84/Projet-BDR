package bdr.corpus;

import bdr.exceptions.Bm25Exception;

import java.util.HashMap;

public class Bm25 extends TfIdf {

    private double k1;
    private double b ;
    private double avgDI;

    public Bm25(double k1 , double b ) {
        this.k1 = k1;
        this.b = b;
    }
    public Bm25(){
        k1=1.5;
        b=0.75;

    }

    /**
     * Permettant dans un premier temps, de collecter le corpus à l'aide de la méthode vocabulaire
     * puis de traiter le Corpus pour remplir la HashMap de TF
     * et le vecteur de double IDF avec les valeurs adaptées.
     * @param corpus une instance de corpus contenant tous les documents
     * @return une instance de TFIDF
     */
    @Override
    public TfIdf processCorpus(Corpus corpus) {
        if (corpus == null || corpus.isEmpty()) {
            throw new Bm25Exception("La requête de l'utilisateur est nulle ou vide.");
        }
        vocabulaire(corpus);
        setTf(corpus);
        setIdf(corpus);

        double resultat = 0.0;
        for(Document document : corpus){
            resultat += document.size();
        }
        avgDI = resultat / corpus.size();
        return this;
    }

    /**
     * Cette méthode permet de remplir la hashmap tf avec comme clé le document, et en valeur la liste des tf par mot de ce document.
     * donc on calcule le tf grace a cette formule: Occurence du mot / Nb de mots dans le document
     * @param corpus une instance de corpus contenant tous les fichiers
     */
    @Override
    public void setTf(Corpus corpus) {
        int totalMots = Vocabulary.getVocab().size();

        for (Document document : corpus) {
            double[] tab = new double[totalMots];
            int nbMots = document.size();

            for (Mot mot : document) {
                if (mot == null) continue;
                if (!Vocabulary.vocabContains(mot)) continue;
                int id = Vocabulary.getId(mot);
                tab[id]++;
            }
            tf.put(document, tab);
        }
    }

    /**
     * Cette méthode permet de remplir le tableau idf qui correspond a la formule:
     * logarthme( 1 + ( total de document - le nombre de document contenant le terme + 0.5) / ( nombre de document contenant qi + 0.5 ) )
     * @param corpus une instance de corpus contenant tous les fichiers
     */
    @Override
    public void setIdf(Corpus corpus) {
        int totalMots = Vocabulary.getVocab().size();
        int N = corpus.size();
        double[] tab = new double[totalMots];
        tf.forEach((document, occ) -> {
            for (int i = 1; i < occ.length; i++) {
                if (occ[i] != 0) {
                    tab[i]++;
                }
            }
        });
        for (int i = 1; i < tab.length; i++) {
            if (tab[i] == 0) continue;
//            System.out.println("Mot: " + Vocabulary.getMot(i) + " Id:" + i + " N " + N + " / " + tab[i] + " = " + N / tab[i]);
            tab[i] = Math.log(1+(N-tab[i]+0.5)/(tab[i]+0.5));
        }
        idf = tab;
    }

    /**
     * Permet de calculer pour chaque document son rang par rapport a la formule suivante:
     * Rang du document = Pour chaque mot: Somme ( (nbOccurenceDansleDoc *(k+1) )/ (nbOccurenceDansleDoc + k1 * (1 - b + b * (tailleDoc / avgdl))))
     * avgdl c'est la moyenne de la taille des documents dans le corpus
     * @param queryFeatures renvoie le poid des mots de la query
     * @return eval: la hashmap qui contient le poid de chaque document.
     */
    @Override
    public HashMap<Document, Double> evaluate(double[] queryFeatures) {
        HashMap<Document,Double>  eval = new HashMap<>();
        tf.forEach((document, tab) -> {
            double score = 0.0;
            for(int i = 0; i < queryFeatures.length; i++) {
                if(queryFeatures[i] == 0) continue;
                // System.out.println("Mot: " + Vocabulary.getMot(i) + " idf: " + idf[i] + " tf: " + tab[i]);
                score += idf[i] * ((tab[i] * (k1 + 1)) /(tab[i] + (k1 * (1 - b + b * (document.size() / avgDI)))));
            }
            eval.put(document, score);

        });
        return eval ;
    }

}
