public class Mot{
	
	@Override
	public String toString() {
		return mot;
	}


	private String mot = new String();
	
	
	public Mot(String mot) {
		if(mot == null) {
			this.mot = "";
			return;
		}
		this.mot = mot;
	}
	
	public String getMot() {
		return mot ; 
	}


}
