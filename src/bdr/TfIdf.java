package bdr;

import com.sun.webkit.dom.DocumentImpl;

import java.util.*;

import static java.lang.Math.sqrt;

public class TfIdf {
    HashMap<Document, double[]> tf = new HashMap<Document, double[]>();
    double[] idf;

    public TfIdf() {

    }

    public void vocabulaire(Corpus corpus) {
        Integer integer = 0;
        for (Document document : corpus.getCollDocuments()) {
            for (Mot mot : document.getListMot()) {
                if (Vocabulary.vocabContains(mot)) continue;
                if (Vocabulary.stopWordContains(mot)) continue;
                Vocabulary.add(mot, integer);
                integer++;
            }
        }
    }

    public TfIdf processCorpus(Corpus corpus) {
        vocabulaire(corpus);
        int totalMots = Vocabulary.getVocab().size();
        System.out.println(Vocabulary.getVocab()); // <--------------  Vocab
        HashMap<Mot, Integer> vocab = Vocabulary.getVocab();

        for (Document document : corpus) {
            double[] tab = new double[totalMots];
            int nbMots = document.nbMots();
            for (Mot mot : document) {
                if (mot == null) continue;
                if (!Vocabulary.vocabContains(mot)) continue;

                int id = Vocabulary.getId(mot);
                tab[id]++;
            }
            for (int i = 1; i < tab.length; i++) {
                if (tab[i] == 0) continue;
                tab[i] /= nbMots;
            }
            tf.put(document, tab);
        }

        int nbDoc = corpus.getNbDocuments();
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
           // System.out.println("Mot: " + Vocabulary.getMot(i) + " Id:" + i + " nbDoc " + nbDoc + " / " + tab[i] + " = " + nbDoc / tab[i]);
            tab[i] = Math.log(nbDoc / tab[i]);
        }
        idf = tab;
        return this;
    }

    /*  public void processQuery(String query, Document max ) {
          double [] features = processCorpus(String) ;

      }
  */
    public double[] features(String query) {
        double[] features = new double[idf.length];
        String[] mots = query.split(" ");
        for (String motStr : mots) {
            Mot mot = new Mot(motStr);
            if (Vocabulary.vocabContains(mot)) {
                int id = Vocabulary.getId(mot);
                features[id]++;
            }
        }
        return features;
    }

    public HashMap<Document,Double> evaluate ( double[] queryFeatures) {
//produits scalaire

        HashMap<Document,Double>  eval = new HashMap<>();
        tf.forEach((document , tab)->{
            double prodScal = 0.0 ;
            double normeA = 0.0 ;
            double normeB = 0.0 ;
            double resultat ;
            System.out.println("µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ");
            //System.out.println(document.getTitre());
            for (int i = 0 ; i< idf.length ; i++){

                prodScal +=  queryFeatures[i] * tab[i] * idf[i] ;// A scalaire B
              //  System.out.println( " mot " + Vocabulary.getMot(i) + " A : " + queryFeatures[i]+ " b: "  + "tf "+ tab[i] + " * " +  "  idf :  " + idf[i] +" egal = " + tab[i] * idf[i] + " egal " + prodScal );
                normeA += queryFeatures[i]*queryFeatures[i] ;
                normeB += (tab[i] * idf[i])*(tab[i] * idf[i]);
            }
            normeA=sqrt(normeA);
            normeB=sqrt(normeB);
            resultat = prodScal / (normeA * normeB) ;

            eval.put(document ,resultat);
        });


        return eval ;
    }

    public  void processQuery(String query , int max){
        double[] queryFeatures = features(query);
        HashMap<Document,Double>  eval = evaluate(queryFeatures);

        List<Map.Entry<Document,Double>> tabfin = new ArrayList<>(eval.entrySet());
    tabfin.sort((e1,e2) -> Double.compare(e2.getValue() , e1.getValue()));
        System.out.println(query);
    tabfin.stream()
            .limit(max)
            .forEach(entry -> {
                System.out.println("Document: " + entry.getKey().getTitre() + ", Score: " + entry.getValue());

            });





     /* for (Map.Entry evalua : eval.entrySet()) {

            double maxi = max(evalua.getValue()) ;
            Document doc = (Document) evalua.getKey();
            System.out.println("clé: "+ doc.getTitre()
                    + " | valeur: " + evalua.getValue());


        }

*/







                 /*
                   int taille = tab.length;

          for (int i = 1; i < taille; i++)
          {
               int index = tab[i];
               int j = i-1;

               while(j >= 0 && tab[j] > index)
               {
                    tab[j+1] = tab[j];
                    j--;
               }
               tab[j+1] = index;
        }
        }
*/



    }

}



