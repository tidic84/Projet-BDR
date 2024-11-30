package bdr.corpus;

import bdr.exceptions.CorpusException;

import java.util.Arrays;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Permet de stocker un ensemble de documents.
public class Corpus extends Vector<Document> {
    private String titre;


    /**
     * Constructeur a 2 arguments.
     * <p>Le fonctionnement est le suivant:
     * <p> -> On lit le fichier, on regarde ligne par ligne, on considère que une ligne est un document. On procède différemment en fonction de dataSets
     *      <p>- Si dataSets est WIKIPEDIA: on prend le premier mot séparé par ||| , on le met dans l'attribut titre de l'objet document puis on sépare le reste mot par mot (en considérant que tous les mots sont séparé par des espaces) puis on les ajoute a l'objet document.
     *      <p>- Si dataSets est Corpus: on prend le premier mot qui est un nombre avec un regex puis on le met dans l'attribut titre et le reste est le meme que pour wikipedia.
     *      <p>On a arbitrairement utilisé un limiteur qui est présent pour éviter de trop long temps d'attente.
     * @param path – correspond au chemin du fichier qui contient la liste de documents.
     * @param dataSets – un enum appelé dataSets ou on stock les types de corpus que notre programme prend en compte, qui est pour l'instant de 2. WIKIPDEIA et CORPUS
     */
    public Corpus(String path, DataSets dataSets ) { // ajouter instance class tfidf ou bm25
        try {
            if(path == null ) { throw new CorpusException("Fichier contenant les docs est null");}
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int cpt = 0; //  <---- Compteur/Limiteur

            while (myReader.hasNextLine() && cpt < 10000) { // On a limité pour des raisons de performances
                String data = myReader.nextLine();
                String titre = "";
                Document document = null;

                switch (dataSets) {
                    case WIKIPEDIA:
                        titre = data.split("\\|\\|\\|")[0];
                        String mots = data.split("\\|\\|\\|")[1];
                        if (titre.equals(" ")) continue ;
                        document = new Document(new Mot(titre));
                        Arrays.stream(mots.split(" ")).forEach(document::putMot); //  On utilise Arrays.stream pour pouvoir utiliser le foreach qui est largement plus rapide qu'un for.
                        this.add(document);
                        cpt++; //  <---- Compteur/Limiteur
                        break;
                    case OUVRAGES:
                        titre = data.split("\\s")[0];
                        document = new Document(new Mot(titre));

                        for (String mot : data.split("\\s")) {
                            document.putMot(mot);
                        }
                        this.add(document);
                        cpt++; //  <---- Compteur/Limiteur
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        } catch (CorpusException e) {
            throw new RuntimeException(e);

        }
        titre = path;

    }

    /**
     * Cette méthode permet d'ajouter un document à la collection de documents.
     * @param document – Le document qui va être ajouté
     */
    public void addDocument(Document document) {
        if(document == null) return;
        this.add(document);
        return;
    }

    /**
     * On peut l’appeler avec pour paramètre un objet correspondant à l’un ou l’autre de ces deux algorithmes de calcul de la taille (TailleDocument ou TailleMot).
     * @param taille – TailleDocument ou TailleMot
     * @return – – retourne un entier, résultat de l’appel de la méthode de calcul, invoquée sur l’objet passé en paramètre.
     */
    public int taille(Object taille) {
        if(taille instanceof TailleMot) {
            return ((TailleMot) taille).calculer(this);
        } else if(taille instanceof TailleDocument) {
            return ((TailleDocument) taille).calculer(this);
        }
        return 0;
    }

    /**
     * Appelle la méthode processCorpus de cette instance
     * @param methode instance de TfIdf ou Bm25
     * @return une instance de TfIdf ou de Bm25
     */
    public Object getFeatures(Object methode) {
        if(methode instanceof Bm25) {
            Bm25 bm25 = (Bm25) methode;
            bm25.processCorpus(this);
            return bm25;
        } else if(methode instanceof TfIdf) {
            TfIdf tfIdf = (TfIdf) methode;
            tfIdf.processCorpus(this);
            return tfIdf;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Corpus [titre=" + titre + ", collDocuments=" + super.toString() + "]";
    }
}