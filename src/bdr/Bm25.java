package bdr;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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


    @Override
    public TfIdf processCorpus(Corpus corpus) {
//        super.processCorpus(corpus);
        vocabulaire(corpus);
        System.out.println(Vocabulary.getVocab()); // <--------------  Vocab
        setTf(corpus);
        setIdf(corpus);

        double resultat = 0.0;
        for(Document document : corpus){
            resultat += document.getNbMots();
        }
        avgDI = resultat / corpus.getNbDocuments();
        return this;
    }

    @Override
    public void setTf(Corpus corpus) {
        int totalMots = Vocabulary.getVocab().size();
        for (Document document : corpus) {
            double[] tab = new double[totalMots];
            int nbMots = document.getNbMots();
            for (Mot mot : document) {
                if (mot == null) continue;
                if (!Vocabulary.vocabContains(mot)) continue;

                int id = Vocabulary.getId(mot);
                tab[id]++;
            }
            tf.put(document, tab);
        }
    }

    @Override
    public void setIdf(Corpus corpus) {
        int totalMots = Vocabulary.getVocab().size();
        int N = corpus.getNbDocuments();
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

    @Override
    public HashMap<Document, Double> evaluate(double[] queryFeatures) {
        HashMap<Document,Double>  eval = new HashMap<>();
        tf.forEach((document, tab) -> {
            double score = 0.0;
            for(int i = 0; i < queryFeatures.length; i++) {
                if(queryFeatures[i] == 0) continue;
               // System.out.println("Mot: " + Vocabulary.getMot(i) + " idf: " + idf[i] + " tf: " + tab[i]);
                score += idf[i] * ((tab[i] * (k1 + 1)) /(tab[i] + (k1 * (1 - b + b * (document.getNbMots() / avgDI)))));
            }
            eval.put(document, score);

        });
        return eval ;
    }


}
