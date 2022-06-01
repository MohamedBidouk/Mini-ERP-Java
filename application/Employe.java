package application;

public class Employe extends Salarier{
	private double Sup;
	private double PrixSuperieur;
	public double getSup() {
		return Sup;
	}
	public void setSup(double sup) {
		Sup = sup;
	}
	public double getPrixSuperieur() {
		return PrixSuperieur;
	}
	public void setPrixSuperieur(double prixSuperieur) {
		PrixSuperieur = prixSuperieur;
	}

	public Employe(int matricule, String nom, String email, double recru,double sup) {
		super(matricule, nom, email, recru);
		Sup = sup;
		PrixSuperieur = sup * 33;
		setSalaire((PrixSuperieur+Salarier.salaireFix(recru)));
	}
	@Override
	public String toString() {
		return  "|"+getMatricule() + "|"  + getNom() + "|" + getEmail()  + "|" + getSalaire() 
				+ "|" + getRecru()  + "|" + getSup()  + "|" + getPrixSuperieur();
	}
	
	
}
