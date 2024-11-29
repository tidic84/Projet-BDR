package bdr;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Vocabulary {

    public static HashMap<Mot, Integer> recherche = new HashMap<Mot, Integer>();

    public static HashSet<Mot> stopl = new HashSet<Mot>();

    public Vocabulary(String path) throws FileNotFoundException {
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {

            String data = myReader.nextLine();
            stopl.add(new Mot(data)) ;

        }
    }

    public int getId(Mot mot) {
        final int[] res = new int[1];
        recherche.forEach((key, value) -> {
            if(key.toString() == mot.toString()) {
                res[0] = value.intValue();
            };
        });
        return res[0];
    }

    public static HashMap<Mot, Integer> getVocab() {
        return recherche;
    }

    public static void add (Mot mot, Integer integer){
        recherche.put(mot, integer) ;
    }

    public static  HashSet<Mot> getStopWord(){
        return stopl ;
    }



    @Override
    public String toString() {
        return "Vocabulary{" +
                "recherche=" + recherche + '}';
    }
}