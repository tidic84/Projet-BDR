package bdr;

public class Test {

	public static void main(String[] args) {

		Corpus corpus = new Corpus("./ressources/booksummaries.txt", DataSets.OUVRAGES);
		TailleDocument taillecol = new TailleDocument();
		System.out.println(corpus);
		System.out.println(taillecol.calculer(corpus));

	}

}
