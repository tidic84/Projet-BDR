package bdr;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Vocabulary {

    HashMap<Mot, Integer> recherche = new HashMap<Mot, Integer>();

    //HashSet<Mot> stopl = new HashSet<Mot>();

    public Vocabulary(String path) {

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int id = 1;
            int cpt=0;


            while (myReader.hasNextLine() && cpt < 100) {

                String data = myReader.nextLine();
                Mot vocab = new Mot(data.split(" ")[0]);
                recherche.put(vocab, id);
                id++;
                cpt++; // ENLEVER
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Vocabulary{" +
                "recherche=" + recherche + '}';
    }
}