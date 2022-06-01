package application;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddSalarierController implements Initializable{
	
	List<String> listCat;
	String v;
	ObservableList<String> names;
	List<Integer> listMat;
	String entredM = "";;
	private Stage stage;
	private Scene scene;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listCat = new ArrayList<String>();
		listMat = new ArrayList<Integer>(); 
		try{  		
			Statement stmt=Connexion.getCn().createStatement();  
			ResultSet rs=stmt.executeQuery("select * from categorylist");
			
			Statement stmtMat=Connexion.getCn().createStatement();  
			ResultSet rsMat=stmtMat.executeQuery("select matriculeE from entreprise");
	
			while(rs.next()) {
			 listCat.add(rs.getString(1));
			 v = rs.getString(1);
			 choix.getItems().addAll(v);
			}
			
			while(rsMat.next()) {
				listMat.add(rsMat.getInt(1));
			}
			names = FXCollections.observableArrayList(listCat);
			
		}catch(Exception e){ System.out.println(e);}
		
		matric.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            matric.setText(newValue.replaceAll("[^\\d]", ""));
		        }else if(newValue.length()>0){
		        	entredM = newValue;
		        }
		        if(listMat.contains(Integer.parseInt(entredM))) {
	        		confirmBtn.setText("Matricule Dublicated");
	        		confirmBtn.setDisable(true);
	        	}else {
	        		confirmBtn.setText("Confirmer");
	        		confirmBtn.setDisable(false);
	        	}
		    }
		});
		
		email.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("^(.+)@(.+)$*")) {
		        	confirmBtn.setText("Check Email");
	        		confirmBtn.setDisable(true);
		        }else {
		        	confirmBtn.setText("Confirmer");
	        		confirmBtn.setDisable(false);
		        }
		        
		    }
		});
		
		nom.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("^[a-zA-Z]*$")) {
		            nom.setText(newValue.replaceAll("[1-9]*$", ""));
		        }else if(newValue.length()==0){
		        	confirmBtn.setText("Check Nom");
	        		confirmBtn.setDisable(true);
		        }else {
		        	confirmBtn.setText("Confirmer");
	        		confirmBtn.setDisable(false);
		        }
		    }
		});
		
		
		date.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.length()>4) {
		            date.setText(newValue.replace(newValue, oldValue));
		        }else if(newValue.length()==0){
		        	confirmBtn.setText("Check Nom");
	        		confirmBtn.setDisable(true);
		        }else {
		        	confirmBtn.setText("Confirmer");
	        		confirmBtn.setDisable(false);
		        }
		    }
		});
		
	}
	
	@FXML
	ChoiceBox<String> choix;
	
	
	@FXML 
	 private TextField matric;
	
	@FXML 
	 private TextField nom;
	
	@FXML 
	 private TextField email;
	
	@FXML 
	 private TextField date;
	
	@FXML 
	 private TextField sup;
	
	//button
	@FXML 
	 private Button confirmBtn;
	
	@FXML 
	 private Button cancelBtn;
	
	boolean Existed = false;
	
	public void addMethod(ActionEvent event) throws IOException{
		try{
			PreparedStatement ps=Connexion.getCn().prepareStatement("insert into entreprise values(?,?,?,?,?,?,?,?)");  			
			
			int matricule=Integer.parseInt(matric.getText());
			String newNom=nom.getText();  
			String newEmail=email.getText();  
			double NewDateE=Double.parseDouble(date.getText());
			double NewSup=Double.parseDouble(sup.getText());
			String newCategory=choix.getValue();
			
			if(newCategory=="Employe") {
				Vendeur s = new Vendeur(matricule,newNom,newEmail,NewDateE,NewSup);
				ps.setInt(1, s.getMatricule());
				ps.setString(2,s.getNom());  
				ps.setString(3,s.getEmail());
				ps.setDouble(4, s.getSalaire());
				ps.setDouble(5, s.getRecru());
				ps.setDouble(6, s.getVente());
				ps.setDouble(7, s.getPourcentage());
				ps.setString(8, newCategory);
				}
			else {
				Employe s = new Employe(matricule,newNom,newEmail,NewDateE,NewSup);
				ps.setInt(1, s.getMatricule());
				ps.setString(2,s.getNom());  
				ps.setString(3,s.getEmail());
				ps.setDouble(4, s.getSalaire());
				ps.setDouble(5, s.getRecru());
				ps.setDouble(6, s.getSup());
				ps.setDouble(7, s.getPrixSuperieur());
				ps.setString(8, newCategory);
			}
			
			ps.executeUpdate();  
			
		}catch(Exception e){ System.out.println(e);}
		
		Parent root = FXMLLoader.load(getClass().getResource("EntrepriseData.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}



	
	public void switchToVueScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("EntrepriseData.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Liste des salarié");
		stage.show();
	}
}
