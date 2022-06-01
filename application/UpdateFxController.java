package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpdateFxController implements Initializable{
	
	public static boolean updated = false;
	
	@FXML
	public AnchorPane scenePane;
	
	Stage stage1;
	
	public void confirmed(ActionEvent event) {
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	
	
		//table column
		
		@FXML 
		 private TextField nom;
		
		@FXML 
		 private TextField email;
		
		@FXML 
		 private TextField date;
		
		@FXML 
		 private TextField sup;
		
		@FXML 
		 private TextField category;
		
		//button
		@FXML
		public ToggleButton confirm;
		
		@FXML 
		 public Button confirmBtn;
		
		@FXML 
		 private Button cancelBtn;
		
		updateUtility importedEmploye ;
		public static int mat = 34 ;
		
		@Override
		public void initialize(URL location, ResourceBundle resources) { 
			
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
			        if (newValue.length()>6) {
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
			
			category.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("^[a-zA-Z]*$")) {
			            category.setText(newValue.replace(newValue, oldValue));
			        }else if(newValue.length()==0 ){
			        	confirmBtn.setText("Check Category");
		        		confirmBtn.setDisable(true);
			        }else if(newValue.matches("Employe") || newValue.matches("Vendeur")) {
			        	confirmBtn.setText("Confirmer");
		        		confirmBtn.setDisable(false);
			        }
			    }
			});
			
			confirmBtn.setOnAction((event) -> {
				updateMethod();
				confirmed(event);
			});
			
			this.importedEmploye = new updateUtility(mat); 
			 try {
				importEmploye();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@FXML
		 public void importEmploye() throws Throwable{
			category.setText(importedEmploye.getClassName());
			String c = importedEmploye.getClassName();
			String em = "Employe";
			if(c.equals(em)) {
				nom.setText(importedEmploye.getImportEmp(mat).getNom());
				email.setText(importedEmploye.getImportEmp(mat).getEmail());
				date.setText(String.valueOf(importedEmploye.getImportEmp(mat).getRecru()));
				sup.setText(String.valueOf(importedEmploye.getImportEmp(mat).getSup()));
			}else {
				nom.setText(importedEmploye.getImportVen(mat).getNom());
				email.setText(importedEmploye.getImportVen(mat).getEmail());
				date.setText(String.valueOf(importedEmploye.getImportVen(mat).getRecru()));
				sup.setText(String.valueOf(importedEmploye.getImportVen(mat).getVente()));
			}
				
				
			}
		
		private Stage stage;
		private Scene scene;
		
		public void switchToVueScene(ActionEvent event) throws IOException {
			confirmed(event);
		}

		public static double setSalaireFinal (double dateE, double pour) {
			if (dateE>=2005) {
				return  (400 + pour);
			}else {
				return  280 + pour;
			}
		}
		
		public static double setP(double su) {
			return su*0.2;
		}
		
		@FXML
		public void getEntredData() {
			
		}
		
		@FXML
		public void updateMethod() {
			try{  
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
						"jdbc:mysql://localhost:3307/miniprojet","root","");  
				
				PreparedStatement ps=con.prepareStatement("update entreprise set nom=?, email=?, salaire=?, dateEmbauche=?, sup=?, supDT=?, category=? where matriculeE=?");  			
				
				int matricule=mat;
				 
				String newNom=nom.getText();  
				
				String newEmail=email.getText();  
				
				  
				double NewDateE=Double.parseDouble(date.getText());
				  
				double NewSup=Double.parseDouble(sup.getText());
				
				double supDT=setP(Double.parseDouble(sup.getText()));
				double salaryFinal=setSalaireFinal(NewDateE, supDT);
				
				  
				String newCategory=category.getText(); 
				
				
				ps.setInt(8, matricule);
				ps.setString(1,newNom);  
				ps.setString(2,newEmail);
				ps.setDouble(3, salaryFinal);
				ps.setDouble(4, NewDateE);
				ps.setDouble(5, NewSup);
				ps.setDouble(6, supDT);
				ps.setString(7,newCategory);
				
				ps.executeUpdate();  
				  
				con.close();
				updated = true;
				
			}catch(Exception e){ System.out.println(e);} 
		}
		


		
		
}
