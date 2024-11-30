package bdr.corpus;

import java.util.Objects;

public class Mot {

    @Override
    public String toString() {
        return mot;
    }

    private String mot = new String();

    /**
     * Constructeur a un argument qui enlève les caractères superflu comme les parenthèses, les virgules ect.. et qui met le mot en minuscule pour faciliter les comparaisons plus tard
     * @param mot un string qui sera le mot
     */
    public Mot(String mot) {
        if (mot == null) {
            this.mot = "";
            return;
        }
        this.mot = mot.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    /**
     * Retourne le string mot
     * @return mot
     */
    public String getMot() {
        return mot;
    }

    /**
     * permet de changer la veleur de mot tout en gardant les memes caractères que le reste des mots.
     * @param str un string qui remplacera le contenu du mot
     */
    public void setMot(String str) {mot = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Mot)) return false;
        Mot mot1 = (Mot) o;
        return Objects.equals(mot, mot1.mot);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mot);
    }
}
