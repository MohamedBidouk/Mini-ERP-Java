package application;

public class Vendeur extends Salarier{
	private double Vente;
	private double Pourcentage;
	public double getVente() {
		return Vente;
	}
	public void setVente(double vente) {
		Vente = vente;
	}
	public double getPourcentage() {
		return Pourcentage;
	}
	public void setPourcentage(double pourcentage) {
		Pourcentage = pourcentage;
	}
	public Vendeur(int matricule, String nom, String email, double recru, double vente) {
		super(matricule, nom, email, recru);
		Vente = vente;
		Pourcentage = Vente * 4;
		setSalaire((Pourcentage+Salarier.salaireFix(recru)));
	}
	
	public String createEs(String s) {
		String espace = "";
		for(int i = 0; i<15-s.length(); i++) {
			espace += " ";
		}
		return espace;
	}
	@Override
	public String toString() {
		
		return "|"+getMatricule() +createEs(Integer.toString(getMatricule()))+ "|"  + getNom() +createEs(getNom())+"|" + getEmail()  
		+createEs(getEmail())+ "|" + getSalaire()+createEs(Double.toString(getSalaire())) 
		+ "|" + getRecru()  +createEs(Double.toString(getRecru())) + "|" + getVente()  +createEs(Double.toString(getVente())) 
		+ "|" + getPourcentage()+createEs(Double.toString(getPourcentage())) ;
	}
	
	
}
