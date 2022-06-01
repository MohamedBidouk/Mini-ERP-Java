package application;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalarierPlusFaible {
	private List<Salarier> sal;
	private List<Category> ct;
	public SalarierPlusFaible() {
		sal = new ArrayList<Salarier>();
		ct = new ArrayList<Category>();
		try{  		
			Statement stmt=Connexion.getCn().createStatement();  
			ResultSet rs=stmt.executeQuery("select * from entreprise");
	
			while(rs.next())  
				if(rs.getString(8)=="Employe") {
					Employe a = new Employe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6));
					a.setSalaire(rs.getDouble(4));
					sal.add(a);
					ct.add(new Category(rs.getString(8)));
				}
				else {
					Vendeur v = new Vendeur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6));
					v.setSalaire(rs.getDouble(4));
					sal.add(v);
					ct.add(new Category(rs.getString(8)));
				} 
		}catch(Exception e){ System.out.println(e);} 
	}
	
	public List<Salarier> getImportlist() { return sal;}
	public List<Category> getClassName() { return ct;	}
}
