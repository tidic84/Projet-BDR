package bdr;

import java.util.Arrays;
import java.util.HashMap;

public class TfIdf {
    HashMap<Document, double[]> tf = new HashMap<Document, double[]>();
    double[] idf;

    public TfIdf() {

    }

    public void vocabulaire(Corpus corpus) {
        Integer integer = 0 ;
        for (Document document : corpus.getCollDocuments()) {
            for (Mot mot : document.getListMot()) {
                if( Vocabulary.vocabContains(mot) )  continue;
                if(Vocabulary.stopWordContains(mot))  continue;
                Vocabulary.add(mot, integer);
                integer++;
            }}
    }

    public TfIdf processCorpus(Corpus corpus) {

        vocabulaire(corpus);
        System.out.println(Vocabulary.getVocab()); // <--------------  Vocab
        HashMap<Mot,Integer> vocab = Vocabulary.getVocab();

        for(Document document: corpus ){
            double[] tab = new double[200000];
            int nbMots= document.nbMots();
            for(Mot mot : document){
                if(mot == null) continue;
                if(!Vocabulary.vocabContains(mot)) continue;

                int id = Vocabulary.getId(mot);
                tab[id]++ ;
            }
            for(int i = 0 ; i < tab.length ; i++) {
                if(tab[i] == 0) continue;
                tab[i] /= nbMots;
            }
            tf.put(document,tab);
        }
        System.out.println(tf.values().toArray());

//        int nbDoc = corpus.getNbDocuments();
//        double[] tab = new double[200000];
//        tf.forEach((document, occ)-> {
//            for(int i = 0; i < 100; i++) {
//                if(occ[i] != 0) {
//                    tab[i]++;
//                }
//            }
//        });
//        for(int i = 0; i < tab.length; i++) {
//            if(tab[i]==0) continue;
//            System.out.println("Id:" + i + nbDoc + " / " + tab[i] + " = " + nbDoc / tab[i]);
//            tab[i] = nbDoc / tab[i];
//        }
        return this ;
    }



}
