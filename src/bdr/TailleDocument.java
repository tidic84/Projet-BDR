package bdr;

import java.util.Vector;

public class TailleDocument  {


	
	
	
	 public int calculer(Corpus corpus) {
	int n=0;
	Vector<Document> colDocuments = corpus.getCollDocuments();
	
	for (Document documents : colDocuments ) {
		n++;
	}
	
	return n ;
	
		
	}
	
	
	

}
