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

    public static int getId(Mot mot) {
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

    public static boolean stopWordContains (Mot mot ){

        for (Mot mot2 : stopl){
               if(mot.equals(mot2)) return true ;

        }
        return false;
    }

    public static boolean vocabContains (Mot mot  ){

        for (Mot mot2 : recherche.keySet()){
            if(mot.equals(mot2)) return true ;

        }
        return false;
    }



    @Override
    public String toString() {
        return "Vocabulary{" +
                "recherche=" + recherche + '}';
    }
}