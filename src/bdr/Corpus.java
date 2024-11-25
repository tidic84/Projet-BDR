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

              while (myReader.hasNextLine())
              {
                String data = myReader.nextLine();
                String[] splitted = data.split("|||");
                System.out.println(splitted[0]);
              //  collDocuments.add(new Document(new Mot (data)));

              }
              myReader.close();
            } catch (FileNotFoundException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }

    }
}