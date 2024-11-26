package bdr;

public class Test {

	public static void main(String[] args) {

		//	Corpus corpus = new Corpus("./ressources/stemmed.txt", DataSets.WIKIPEDIA);
		//System.out.println(corpus);
		Corpus ouvrages = new Corpus("./ressources/booksummaries.txt", DataSets.OUVRAGES);
		System.out.println(ouvrages);

	}

}
