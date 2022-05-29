package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ListerBetweenSalaire {
	
	private List<Salarier> sal;
	private List<Category> ct;
	public ListerBetweenSalaire(double first, double last) {
		sal = new ArrayList<Salarier>();
		ct = new ArrayList<Category>();
		try{ 
			PreparedStatement stmt=Connexion.getCn().prepareStatement("select * from entreprise where salaire between ? and ?");  
			stmt.setDouble(1, first);
			stmt.setDouble(2, last);
			ResultSet rs=stmt.executeQuery();
	
			while(rs.next())  
				if(rs.getString(8)=="Employe") {
					sal.add(new Employe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6)));
					ct.add(new Category(rs.getString(8)));
				}
				else {
					sal.add(new Vendeur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6)));
					ct.add(new Category(rs.getString(8)));
				} 
		}catch(Exception e){ System.out.println(e);} 
	}
	
	public List<Salarier> getImportlistBetween(double first, double last) { 
		sal = new ArrayList<Salarier>();
		ct = new ArrayList<Category>();
		try{ 
			PreparedStatement stmt=Connexion.getCn().prepareStatement("select * from entreprise where salaire between ? and ?");  
			stmt.setDouble(1, first);
			stmt.setDouble(2, last);
			ResultSet rs=stmt.executeQuery();
	
			while(rs.next())  
				if(rs.getString(8)=="Employe") {
					sal.add(new Employe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5)));
					ct.add(new Category(rs.getString(8)));
				}
				else {
					sal.add(new Vendeur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5)));
					ct.add(new Category(rs.getString(8)));
				} 
		}catch(Exception e){ System.out.println(e);} 
		return sal;
		}
	
	public List<Category> getImportlistBetweenCat(double first, double last) { 
		ct = new ArrayList<Category>();
		try{ 
			PreparedStatement stmt=Connexion.getCn().prepareStatement("select * from entreprise where salaire between ? and ?");  
			stmt.setDouble(1, first);
			stmt.setDouble(2, last);
			ResultSet rs=stmt.executeQuery();
	
			while(rs.next())  
				if(rs.getString(8)=="Employe") {
					ct.add(new Category(rs.getString(8)));
				}
				else {
					ct.add(new Category(rs.getString(8)));
				} 
		}catch(Exception e){ System.out.println(e);} 
		return ct;
		}
}
