package bdr;
import java.util.Vector;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Corpus extends Vector<Document> {
    public String titre;
    private Vector<Document>collDocuments ;

    public Corpus(String path, DataSets dataSets) {

        collDocuments=new Vector<Document>();

         try {
              File myObj = new File(path);
              Scanner myReader = new Scanner(myObj);

              while (myReader.hasNextLine())
              {
                String[] data = myReader.nextLine().split("\\|\\|\\|");
//                String[] mots = data[1].split(" ");
                Document document = new Document(new Mot (data[0]));
//                for(String mot : mots ) {
//                	document.putMot(mot);
//                }
                collDocuments.add(document);

              }
              myReader.close();
            } catch (FileNotFoundException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }

    }
    
    
    public void addDocument(String path) {
    	try {
    	 File doc = new File(path);
         Scanner scandoc = new Scanner (doc);
    			
    	while ( scandoc.hasNextLine());
    			{
    	String data = scandoc.nextLine();
    	collDocuments.add(new Document(new Mot (data)));
    	}
    	scandoc.close();	
    	}catch (FileNotFoundException e) {
    	              System.out.println("An error occurred.");
    	              e.printStackTrace();
    	            }

    }
}}