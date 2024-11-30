package bdr.corpus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Vocabulary {

    public static HashMap<Mot, Integer> vocab = new HashMap<Mot, Integer>();

    public static HashSet<Mot> stopl = new HashSet<Mot>();

    /**
     * Constructeur de Vocabulary.
     * Le constructeur sert a remplir la stopword hashset
     *
     * @param path chemin de la stop word
     */
    public Vocabulary(String path) throws FileNotFoundException {
        if (!stopl.isEmpty()) return;
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            stopl.add(new Mot(data));
        }
    }

    /**
     * Permet de récupérer l'id d'un mot a partir d'un argument Mot
     *
     * @param mot est le mot dont on veut l'id
     * @return retourne l'id
     */
    public static int getId(Mot mot) {
        int res = -1;
        res = vocab.get(mot);
        return res;
    }

    /**
     * Permet de récupérer le mot en fonction de l'id
     *
     * @param integer est l'id du mot que l'on veut.
     * @return retourne le mot demandé
     */
    public static Mot getMot(int integer) {
        /**
         * On fait comme ça puisque for each ne peut pas changer changer des valeurs non final.
         * Donc on fait un final blank
         * On utilise un for each et pas une boucle for pour sa rapidité.
         */
        final Mot[] mot = new Mot[1];
        vocab.forEach((key, value) -> {
            if (value == integer) {
                mot[0] = key;
            }
            ;
        });
        return mot[0];
    }

    /**
     * Renvoie la hashmap vocabulaire
     *
     * @return le vocab
     */
    public static HashMap<Mot, Integer> getVocab() {
        return vocab;
    }

    /**
     * Permet d'ajouter un nouveau mot au vocabulaire
     *
     * @param mot     le mot a ajouter
     * @param integer son id
     */
    public static void add(Mot mot, Integer integer) {
        if (integer < 0) return;
        vocab.put(mot, integer);
    }

    /**
     * Permet de récupérer la hashset qui correspond a la stopword
     *
     * @return stopword
     */
    public static HashSet<Mot> getStopWord() {
        return stopl;
    }

    /**
     * Permet de vérifier si un mot est contenu dans la stopword
     *
     * @param mot le mot à vérifier
     * @return vrai/faux
     */
    public static boolean stopWordContains(Mot mot) {
        return stopl.contains(mot);
    }

    /**
     * Permet de vérifier si le mot est contenu dans le vocab
     *
     * @param mot le mot à vérifier
     * @return vrai/faux
     */
    public static boolean vocabContains(Mot mot) {
        return vocab.containsKey(mot);
    }

    @Override
    public String toString() {
        return "Vocabulary{" +
                "vocab=" + vocab + '}';
    }
}