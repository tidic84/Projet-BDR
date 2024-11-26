package bdr;
import java.util.Vector;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Corpus extends Vector<Document> {
    private String titre;
    private Vector<Document>collDocuments ;

    public Corpus(String path, DataSets dataSets) {

        collDocuments=new Vector<Document>();
        int cpt = 0;

         try {
              File myObj = new File(path);
              Scanner myReader = new Scanner(myObj);

              while (myReader.hasNextLine() && cpt < 10000)
              {
            	  switch (dataSets) {
            	  case DataSets.WIKIPEDIA:
                      String[] data = myReader.nextLine().split("\\|\\|\\|");
                    Document document = new Document(new Mot (data[0]));
                    collDocuments.add(document);
              	  cpt++;
                    break;
            	  case DataSets.OUVRAGES:
                      String[] datao = myReader.nextLine().split("{");
                    Document ouvrages = new Document(new Mot (datao[0]));
                    collDocuments.add(ouvrages);
              	  cpt++;
                    	
            	  }



              }
              myReader.close();
            } catch (FileNotFoundException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
         
         titre = path ;

    }
    
    
    public void addDocument(String path , DataSets dataSets) {
    	try {
    	 File doc = new File(path);
         Scanner scandoc = new Scanner (doc);
    			
    	while ( scandoc.hasNextLine());
    			{
    	String[] data = scandoc.nextLine().split("\\|\\|\\|");    	
    	collDocuments.add(new Document(new Mot (data[0])));
    	}
    	scandoc.close();	
    	}catch (FileNotFoundException e) {
    	              System.out.println("An error occurred.");
    	              e.printStackTrace();
    	            }

    }


	@Override
	public String toString() {
		return "Corpus [titre=" + titre + ", collDocuments=" + collDocuments + "]";
	}
}