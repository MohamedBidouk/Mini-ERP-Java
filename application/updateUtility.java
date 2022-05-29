package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class updateUtility {
	private Salarier sal;
	private Employe emp;
	private Vendeur ven;
	
	public updateUtility(int matricule) {
		
		try{  
			
			PreparedStatement ps=Connexion.getCn().prepareStatement("select * from entreprise where matriculeE=?");
			ps.setInt(1, matricule);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {  
				if(rs.getString(8).equals("Employe")) {
					sal = new Employe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6));
				}else {
					sal = new Vendeur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6));
				}
				
			}
					
		}catch(Exception e){ System.out.println(e);} 
	}
	public String getClassName() {return sal.getClass().getName().substring(12);}
	
	public Employe getImportEmp(int matricule) {
		emp = new Employe(matricule, null, null, matricule, matricule); 
		try{  
			
			PreparedStatement ps=Connexion.getCn().prepareStatement("select * from entreprise where matriculeE=?");
			ps.setInt(1, matricule);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {  
					emp = new Employe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6));
			}
					
		}catch(Exception e){ System.out.println(e);} 
		return emp;
		}
	
	public Vendeur getImportVen(int matricule) {
		ven = new Vendeur(matricule, null, null, matricule, matricule); 
		try{  
			
			PreparedStatement ps=Connexion.getCn().prepareStatement("select * from entreprise where matriculeE=?");
			ps.setInt(1, matricule);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {  
					ven = new Vendeur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6));
			}
					
		}catch(Exception e){ System.out.println(e);}
		
		return ven;
		}
	
}
