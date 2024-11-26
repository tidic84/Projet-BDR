package bdr;

import java.util.Vector;

public class TailleDocument  {


	
	
	
	 public int calculerDoc(Corpus corpus) {
	int n=0;
	Vector<Document> colDocuments = corpus.getCollDocuments();
	
	for (Document document : colDocuments ) {
		n++;
	}
	
	return n ;
	
		
	}
	
	
	

}
