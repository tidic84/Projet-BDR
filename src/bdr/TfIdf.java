package bdr;

import java.util.HashMap;

public class TfIdf {

    HashMap<Document, double[]> tf = new HashMap<Document, double[]>();
    double[] idf;

    public void TfIdf() {

    }

    public void vocabulaire(Corpus corpus) {
        Vocabulary vocab = new Vocabulary("./ressources/stemmed.vocab");
        for (Document document : corpus.getCollDocuments()) {
            double[] tab = new double[0];
            int nbMot = document.nbMots(document);
            for (Mot mot : document.getListMot()) {
                try {
                    if (mot.toString() == " ") continue;
                    HashMap<Mot, Integer> recherche = vocab.getRecherche();
                    System.out.println(mot);
                    int id = vocab.getId(mot);
                    tab[id]++;
                } catch (NullPointerException e) {
                    System.out.println("Erreur " + e);
                }
            }
            for (int i = 0; i < tab.length; i++) {
                tab[i] = tab[i] / nbMot;
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(tab.length);
            for (double t : tab) {
                System.out.println("------------------------");
                System.out.println(t);
            }
            tf.put(document, tab);
        }
    }

    @Override
    public String toString() {
//        String res = "[";
//        for(double[] value: tf.values()) {
//            System.out.println(value[1]);
//        }
//        return tf.values().toString();
        return"tg";
    }

}