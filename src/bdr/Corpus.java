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


		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			int cpt = 0;// ENLEVER


			String data = myReader.nextLine();

			while (myReader.hasNextLine() && cpt < 1000) // ENLEVER le cpt < 1000
			{
				String titre = "";
				Document document = null;
				
				switch (dataSets) {
				case DataSets.WIKIPEDIA:
					titre = data.split("\\|\\|\\|")[0];
					document = new Document(new Mot(titre));
					for(String mot : data.split("\\|\\|\\|")[1].split(" ")) {
						document.putMot(mot);
					}
					collDocuments.add(document);
					cpt++; // ENLEVER
					break;
				case DataSets.OUVRAGES:
					titre = data.split("\\{")[0];
					document = new Document(new Mot(titre));
					for(String mot : data.split("\\{")[1].split(" ")) {
						document.putMot(mot);
					}
					collDocuments.add(document);
					cpt++; // ENLEVER
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
	
	public Vector<Document> getCollDocuments() {
		return collDocuments;
	}
	



	@Override
	public String toString() {
		return "Corpus [titre=" + titre + ", collDocuments=" + collDocuments + "]";
	}
}