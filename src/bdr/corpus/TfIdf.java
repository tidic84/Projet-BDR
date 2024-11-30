package bdr.corpus;

import bdr.exceptions.Bm25Exception;
import bdr.exceptions.TfIdfException;

import java.util.*;

import static java.lang.Math.sqrt;

public class TfIdf {
    HashMap<Document, double[]> tf = new HashMap<Document, double[]>();
    double[] idf;
    public TfIdf() {
    }

    /**
     * Sert a remplir la seule instance de vocabulaire avec les mots contenu dans le corpus
     * @param corpus le corpus
     */
    public void vocabulaire(Corpus corpus) {
        if(corpus == null) return;
        Integer integer = 0;
        for (Document document : corpus) {
            for (Mot mot : document) {
                if (Vocabulary.vocabContains(mot)) continue;
                if (Vocabulary.stopWordContains(mot)) continue;
                Vocabulary.add(mot, integer);
                integer++;
            }
        }
    }

    // On a dupliqué la méthode vocabulaire et on a enlevé la condition qui vérifie si le mot est dans la stopword (mais on a pas compris pourquoi il le faut le faire)
    public void vocabulaireMax(Corpus corpus) {
        if(corpus == null) return;
        Integer integer = 0;
        for (Document document : corpus) {
            for (Mot mot : document) {
                if (Vocabulary.vocabContains(mot)) continue;
                Vocabulary.add(mot, integer);
                integer++;
            }
        }
    }

    /**
     * La méthode permet de collecter le corpus à l'aide de la méthode vocabulaire
     * puis de traiter le Corpus pour remplir la HashMap de TF et le vecteur de double IDF
     * @param corpus
     * @return une instance de TFIDF
     */
    public TfIdf processCorpus(Corpus corpus) {
        if(corpus == null|| corpus.isEmpty()){
            throw new TfIdfException("Le corpus est nul ou ne contient aucun document.");
        }
        vocabulaire(corpus);
//        System.out.println(Vocabulary.getVocab()); // <--------------  Vocab
        setTf(corpus);
        setIdf(corpus);
        return this;
    }

    public TfIdf processCorpusMax(Corpus corpus) {
        if(corpus == null) return null;
        vocabulaireMax(corpus);
        setTf(corpus);
        setIdf(corpus);
        return this;
    }

    /**
     * Cette méthode permet de remplir la hashmap tf avec comme clé le document, et en valeur la liste des tf par mot de ce document.
     * Donc on calcule le tf grace a cette formule: Occurence du mot / Nb de mots dans le document
     * @param corpus le corpus
     */
    public void setTf(Corpus corpus) {
        if(corpus == null) return;
        int totalMots = Vocabulary.getVocab().size();

        corpus.forEach(document -> {
            double[] tab = new double[totalMots];
            int nbMots = document.size();
            document.forEach(mot -> {
                if (mot == null) return;
                if (!Vocabulary.vocabContains(mot)) return;
                int id = Vocabulary.getId(mot);
                tab[id]++;
            });
            for (int i = 1; i < tab.length; i++) {
                if (tab[i] == 0) continue;
                tab[i] /= nbMots;
            }
            tf.put(document, tab);
        });
    }

    /**
     * Cette méthode permet de remplir le tableau idf qui correspond au logarthme( du total de document / par le nombre de document dans le quel le terme est présent)
     * @param corpus une instance de corpus contenant tous les fichiers
     */
    public void setIdf(Corpus corpus) {
        if(corpus == null) return;
        int totalMots = Vocabulary.getVocab().size();
        int nbDoc = corpus.size();
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
//            System.out.println("Mot: " + Vocabulary.getMot(i) + " Id:" + i + " nbDoc " + nbDoc + " / " + tab[i] + " = " + nbDoc / tab[i]);
            tab[i] = Math.log(nbDoc / tab[i]);
        }
        idf = tab;
    }

    /**
     * Extrait depuis la requête, la fréquence de chaque mot composant la requête sous la forme d'un vecteur de double
     * @param query la requete
     * @return
     */
    public double[] features(String query) {
        double[] features = new double[idf.length];
        String[] mots = query.split(" ");

        //Permet de paralleliser les iterations pour que ce soit plus rapide
        Arrays.stream(mots).parallel().forEach(motStr -> {
            Mot mot = new Mot(motStr);
            //On vérifie si le mot est dans le vocabulaire, et si il n'est pas dans la stopword
            if(Vocabulary.stopWordContains(mot)) return; // Dans un for each continue ne fonctionne pas, donc on met un return
            if (Vocabulary.vocabContains(mot)) {
                int id = Vocabulary.getId(mot);
                features[id]++;
            }
        });
        return features;
    }

    public double[] featuresMax(String query) {
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

    /**
     * Permet de calculer le rang/ la pertinance de chaque document
     * pour cela on doit pour chaque document:
     *      <p>- calculer la somme de tous les produits scalaire des vecteurs queryfeatures et tfidf
     *      <p>- toutes les normes de querry features
     *      <p>- toutes les normes de tfidf
     *      ensuite faire la formule sommePScalaire / (sommeNormeQF * sommeNormeTfidf)
     * et on insert le résultat dans la hash map eval qu'on renvoie quand on a parcouru tous les documents
     * @param queryFeatures les poid des mots dans la requete
     * @return
     */
    public HashMap<Document,Double> evaluate ( double[] queryFeatures) {
        HashMap<Document,Double>  eval = new HashMap<>();
        tf.forEach((document , tab)->{
            double prodScal = 0.0;
            double normeA = 0.0;
            double normeB = 0.0;
            double resultat;
            for (int i = 0 ; i< idf.length ; i++){
                prodScal +=  queryFeatures[i] * tab[i] * idf[i] ;// A scalaire B
                //  System.out.println( " mot " + Vocabulary.getMot(i) + " A : " + queryFeatures[i]+ " b: "  + "tf "+ tab[i] + " * " +  "  idf :  " + idf[i] +" egal = " + tab[i] * idf[i] + " egal " + prodScal );
                normeA += queryFeatures[i]*queryFeatures[i] ;
                normeB += (tab[i] * idf[i])*(tab[i] * idf[i]);
            }
            normeA=sqrt(normeA);
            normeB=sqrt(normeB);
            resultat = prodScal / (normeA * normeB) ;
            if(Double.isNaN(resultat)) resultat = 0;
            eval.put(document ,resultat);
        });
        return eval ;
    }

    /**
     * Permet de calculer le poid des documents puis d'afficher les max premiers résultats
     * On prend le résultat avec le plus grand rang, on l'affiche et on l'enlève,
     * on répète cette opération max fois
     * @param query la requete
     * @param max la valeur maximale de document a renvoyer
     * @return eval: la hashmap qui contient le poid de chaque document.
     */
    public  void processQuery(String query , int max){
        if ( query == " ") {
            throw new Bm25Exception("Le champ est vide");
        }
        double[] queryFeatures = features(query);
        HashMap<Document,Double>  eval = evaluate(queryFeatures);
        if(max > eval.size()) max = eval.size();

        System.out.println("Résultat de la recherche: " + System.lineSeparator());
        for(int i = 0; i < max; i++) {
            Document doc = Collections.max(eval.entrySet(), Map.Entry.comparingByValue()).getKey();
            double score = Collections.max(eval.entrySet(), Map.Entry.comparingByValue()).getValue();
            System.out.println("Document: " + doc.getTitre() + "; Score: " + score);
            eval.remove(doc);
        }

    }

}
