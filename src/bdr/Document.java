package bdr;
import java.util.ArrayList;
import java.util.Vector;

public class Document extends ArrayList<Mot> {

	@Override
	public String toString() {
		return "Document [titre=" + titre + ", listeDoc=" + listeDoc + "]";
	}

	private Mot titre;
	private ArrayList<Mot> listeDoc = new ArrayList<Mot>();

	public Document(Mot titre) {
		// Faire une verif
		super();

		this.titre=titre;



	}

	public void putMot(String leMot) {
		// Faire verif

		listeDoc.add(new Mot (leMot));
	}

	
	public ArrayList<Mot> getListMot() {
		return listeDoc;
	}
}


