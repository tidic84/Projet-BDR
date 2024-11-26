package bdr;

public class Test {

	public static void main(String[] args) {

		Corpus corpus = new Corpus("./ressources/booksummaries.txt", DataSets.OUVRAGES);
		TailleDocument taillecol = new TailleDocument();
		TailleMot taillemot = new TailleMot();
		
		System.out.println(corpus);
		System.out.println(taillecol.calculer(corpus));
		System.out.println(taillemot.calculer(corpus));
	}

}
