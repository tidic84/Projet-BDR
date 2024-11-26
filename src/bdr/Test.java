package bdr;

public class Test {

	public static void main(String[] args) {
		DataSets dataSets = null;
		Corpus corpus = new Corpus("./ressources/stemmed.txt", dataSets);
		System.out.println(corpus);

	}

}
