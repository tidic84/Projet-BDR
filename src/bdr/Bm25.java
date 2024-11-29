package bdr;

import java.util.Arrays;
import java.util.HashMap;

public class Bm25 extends TfIdf {

    private double k1;
    private double b ;
    private double avgDI ;


    public Bm25(double k1 , double b  ){
        this.k1 = k1;
        this.b = b ;




    }

    @Override
    public TfIdf processCorpus(Corpus corpus) {
        super.processCorpus(corpus);
        double resultat = 0.0 ;

        for(Document document : corpus){

            resultat += document.getNbMots();

        }
        avgDI = resultat / corpus.getNbDocuments();
        return this ;


    }


    @Override
    public HashMap<Document, Double> evaluate(double[] queryFeatures) {

        HashMap<Document,Double>  eval = new HashMap<>();
        tf.forEach((document , tab)-> {
            double score = 0.0 ;
            for (int i = 0; i < idf.length; i++) {
                if(idf[i]==0 || tab[i]==0) continue;

                score += idf[i] * (tab[i] * (k1 + 1)) /(tab[i] + (k1 * (1 - b + b * (document.getNbMots() / avgDI))));
                //System.out.println(score);
            }
            // System.out.println(document.getTitre());
            eval.put(document,score);
            //System.out.println(eval);
        });
        return eval ;
    }
    }








