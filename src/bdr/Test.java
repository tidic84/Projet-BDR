package bdr;

import java.lang.invoke.VolatileCallSite;
import java.util.HashMap;

public class Test {

	public static void main(String[] args) {

//		Corpus corpus = new Corpus("./ressources/booksummaries.txt", DataSets.OUVRAGES);
		Corpus corpus = new Corpus("./ressources/stemmed.txt", DataSets.WIKIPEDIA);
		TailleDocument taillecol = new TailleDocument();
		TailleMot taillemot = new TailleMot();
		
//		System.out.println(corpus);
//		System.out.println(taillecol.calculer(corpus));
		//System.out.println(taillemot.calculer(corpus));
		
//		corpus.taille(taillecol);
//		System.out.println(corpus.taille(taillecol));
//		corpus.test();

		Vocabulary reche = new Vocabulary("./ressources/stemmed.vocab");
		System.out.println(reche);
	};


}
