package bdr;

import java.util.Vector;

public class TailleDocument implements Calculer {


	
	
	
	 public int calculer(Corpus corpus) {
	int n=0;
	Vector<Document> colDocuments = corpus.getCollDocuments();
	
	for (Document document : colDocuments ) {
		n++;
	}
	
	return n ;
	
		
	}
	
	
	

}
