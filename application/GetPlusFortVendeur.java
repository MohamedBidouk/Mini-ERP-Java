package application;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetPlusFortVendeur {
	private List<Salarier> sal;
	private List<Category> ct;
	public GetPlusFortVendeur() {
		sal = new ArrayList<Salarier>();
		ct = new ArrayList<Category>();
		try{  		
			Statement stmt=Connexion.getCn().createStatement();  
			ResultSet rs=stmt.executeQuery("select * from entreprise where category='Vendeur' order by sup asc");
			double max = 0;
			while(rs.next()) {  
				Vendeur v = new Vendeur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6));
				v.setSalaire(rs.getDouble(4));
				if(rs.getDouble(4)>=max) {
					sal.add(v);
					ct.add(new Category(rs.getString(8)));
					max = rs.getDouble(4);
				}else {
					sal.remove(sal.size()-1);ct.remove(ct.size()-1);
				}
				} 
		}catch(Exception e){ System.out.println(e);} 
	}
	
	public List<Salarier> getImportlist() { return sal;}
	public List<Category> getClassName() { return ct;	}
}