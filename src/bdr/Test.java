package bdr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

//		Corpus corpus = new Corpus("./ressources/booksummaries.txt", DataSets.OUVRAGES);
		Corpus corpus = new Corpus("./ressources/stemmed.txt", DataSets.WIKIPEDIA);
//		TailleDocument taillecol = new TailleDocument();
//		TailleMot taillemot = new TailleMot();


//		System.out.println(taillecol.calculer(corpus));
		//System.out.println(taillemot.calculer(corpus));

//		corpus.taille(taillecol);
//		System.out.println(corpus.taille(taillecol));
//		corpus.test();

//		Vocabulary reche = new Vocabulary("./ressources/stemmed.vocab");
//		System.out.println(reche);

//		TfIdf tfIdf = new TfIdf();
//		System.out.println(Arrays.toString(tfIdf.processCorpus(corpus).idf));

//		tfIdf.evaluate(tfIdf.features("often anarch public "));

	//	Bm25 bm25 = new Bm25();
	//	bm25.processCorpus(corpus);
		Scanner sc = new Scanner(System.in);
		System.out.println("tape la recherche : ");
		String str = sc.nextLine();
		TfIdf tf = new TfIdf();
		tf.processCorpus(corpus);
		tf.processQuery(str,5 ) ;


//		System.out.println("Query Features: " + Arrays.toString(queryFeatures));

	//	bm25.processQuery(str , 5 );








//		});


	}
}
