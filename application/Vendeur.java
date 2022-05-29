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
	@Override
	public String toString() {
		return getMatricule() + ","  + getNom() + "," + getEmail()  + "," + getSalaire() 
		+ "," + getRecru()  + "," + getVente()  + "," + getPourcentage();
	}
	
	
}
