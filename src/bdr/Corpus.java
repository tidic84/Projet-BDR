package bdr;

import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Corpus extends Vector<Document> {
    private String titre;

    public Corpus(String path, DataSets dataSets ) { // ajouter instance class tfidf ou bm25
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int cpt = 0;// ENLEVER

            while (myReader.hasNextLine() && cpt < 500) {

                String data = myReader.nextLine();
                String titre = "";
                Document document = null;

                switch (dataSets) {
                    case WIKIPEDIA:
                        titre = data.split("\\|\\|\\|")[0];
                        if (titre.equals(" ")) continue ;
                        document = new Document(new Mot(titre));

                        for (String mot : data.split("\\|\\|\\|")[1].split(" ")) {
                            document.putMot(mot);
                        }


                        this.add(document);
                        cpt++; // ENLEVER
                        break;
                    case OUVRAGES:
                        titre = data.split("\\{")[0];
                        document = new Document(new Mot(titre));

                        for (String mot : data.split("\\{")[1].split(" ")) { // modifier
                            document.putMot(mot);
                        }
                        this.add(document);
                        cpt++; // ENLEVER

                }


            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        titre = path;

    }

    public void test() {
        Document doc = new Document(new Mot("test"));
        this.add(doc);
        System.out.println(this);
//		System.out.println(collDocuments);
    }




    public void addDocument(String path, DataSets dataSets) {
        try {
            File doc = new File(path);
            Scanner scandoc = new Scanner(doc);

            while (scandoc.hasNextLine()) ;
            {
                String[] data = scandoc.nextLine().split("\\|\\|\\|");
                this.add(new Document(new Mot(data[0])));
            }
            scandoc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public Vector<Document> getCollDocuments() {
        return this;
    }

    public int getNbDocuments() {
        int cpt = 0;
        for(Document document: this) {
            cpt++;
        }
        return cpt;
    }

    public int taille(Calculer taille) {
        return taille.calculer(this);
    }

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