package application;

public class Salarier {
	private int Matricule;
	private String Nom;
	private String Email;
	private double Recru;
	private double Salaire;
	public int getMatricule() {
		return Matricule;
	}
	public void setMatricule(int matricule) {
		Matricule = matricule;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public double getSalaire() {
		return Salaire;
	}
	public void setSalaire(double salaire) {
		Salaire = salaire;
	}
	
	
	public double getRecru() {
		return Recru;
	}
	public void setRecru(double recru) {
		Recru = recru;
	}
	public Salarier(int matricule, String nom, String email, double recru) {
		super();
		Matricule = matricule;
		Nom = nom;
		Email = email;
		Recru = recru;
	}
	@Override
	public String toString() {
		return "Salarier [Matricule=" + Matricule + ", Nom=" + Nom + ", Email=" + Email + ", Recru=" + Recru
				+ ", Salaire=" + Salaire + "]";
	}
	
	public static double salaireFix(double recrutement) {
		if(recrutement>2005) return 400;
		else return 280;
	}
	
}
