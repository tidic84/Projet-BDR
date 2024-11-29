package bdr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

	//	TfIdf tfIdf = new TfIdf();
	//	System.out.println(Arrays.toString(tfIdf.processCorpus(corpus).idf));

	//	tfIdf.evaluate(tfIdf.features("often anarch public "));
		Bm25 bm25 = new Bm25(1.5 , 0.75);
		bm25.processCorpus(corpus);
		double[] queryFeatures = bm25.features("anarch polit");
		System.out.println("Query Features: " + Arrays.toString(queryFeatures));


		HashMap<Document , Double> caca = bm25.evaluate(queryFeatures);
		caca.forEach((document , db) -> {
			System.out.println("nom doc "+  document.getTitre()  + " score : " + db );
		});


	};


}
