package bdr;

import bdr.corpus.*;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		TfIdf tf = new TfIdf();
		Bm25 bm25 = new Bm25();

//		Corpus corpus = new Corpus("./ressources/booksummaries.txt", DataSets.OUVRAGES);
		Corpus corpus = new Corpus("./ressources/stemmed.txt", DataSets.WIKIPEDIA);
		System.out.println("Il y a " + corpus.taille(new TailleMot()) + " mots contenus dans " + corpus.size() + " documents dans le corpus.");

		Scanner sc = new Scanner(System.in);
		System.out.println("–––––––––––––––––––––––––");
		System.out.println("Tapez votre recherche:");
		String str = sc.nextLine();
		System.out.println("–––––––––––––––––––––––––");

		long startTime = System.currentTimeMillis();
		TfIdf features = (TfIdf) corpus.getFeatures(tf);
		features.processQuery(str,5 ) ;

		System.out.println("–––––––––––––––––––––––––");
		System.out.println("La recherche a durée: " + (float)(System.currentTimeMillis()-startTime)/1000 + " s");

//		bm25.processCorpus(corpus);
//		bm25.processQuery(str , 5 );



	}
}
