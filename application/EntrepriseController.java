package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EntrepriseController implements Initializable{
	private ListerBetweenSalaire DataBetween;
	private ListerParAnc DataByAnc;
	private DataBaseGetEmploye DataGetEmploye;
	private DataBaseGetVendeur DataGetVendeur;
	private GetPlusFortVendeur DataPlusFortVendeur;
	private SalarierPlusFaible DataPlusFaible;
	
	private List<Salarier> salarie;
	private List<Category> categorie;
	
	private Alert a = new Alert(AlertType.NONE);
	private Alert confirmation = new Alert(AlertType.NONE);
	
	DataBaseUtility data = new DataBaseUtility();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		matCol.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		 nomCol.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		 emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		 salaireCol.setCellValueFactory(new PropertyValueFactory<>("Salaire"));
		 categoryCol.setCellValueFactory(new PropertyValueFactory<>("Cat"));
		 table2.setFixedCellSize(31.0);table.setFixedCellSize(31.0);
		 
		 seeDetailButtonCol.setCellFactory(ActionButtonTableCell.<Salarier>forTableColumn("Detail", (Salarier e) -> {
			 loadDetail(e);
			    return e;
			}));    
		 
		 modifyButtonCol.setCellFactory(ActionButtonTableCell.<Salarier>forTableColumn("Modify", (Salarier e) -> {
			 loadDetailForUpdate(e);
			    return e;
			}));    
		 
		 deleteButtonCol.setCellFactory(ActionButtonTableCell.<Salarier>forTableColumn("Delete", (Salarier e) -> {
			 a.setAlertType(AlertType.CONFIRMATION);
             a.setContentText("are yu sure to delete "+ e.getNom()+" "+"de matricule "+e.getMatricule());
             Optional<ButtonType> option = a.showAndWait();
             
             confirmation.setAlertType(AlertType.WARNING);
             if (option.get() == ButtonType.OK) {
            	 try {
					clearList();
					deleteS(e);importerList();
				} catch (Throwable e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
              } else if (option.get() == ButtonType.CANCEL) {
            	  confirmation.close();
              } else {
            	  confirmation.close();
              }
			    return e;
			})); 
		 
		 min.textProperty().addListener(new ChangeListener<String>() {
 
				@Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {					
			        if (!newValue.matches("\\d*")) {
			        	min.setText(newValue.replaceAll("[^\\d]", ""));
			        }else if(newValue.length()>0 ){
			        	double entredMin = Double.parseDouble(newValue);
			        	ObservableList<Salarier> obs = FXCollections.observableArrayList(data.getImportlist());
				    	ObservableList<Salarier> obs1 = FXCollections.observableArrayList();
				    	ObservableList<Category> obsCat = FXCollections.observableArrayList(data.getClassName());
				    	ObservableList<Category> obsCat1 = FXCollections.observableArrayList();
				    	for (Salarier s : obs) {
				    		if(s.getSalaire()>entredMin) {
				        		obs1.add(s);obsCat1.add(obsCat.get(obs.indexOf(s)));
				       		}else if(s.getSalaire()<entredMin){
				       			obs1.remove(s);obsCat1.remove(obsCat.get(obs.indexOf(s)));
				       		}
				        }
				    	 table.setItems(obs1);
				    		table2.setItems(obsCat1);
				    		
				    		if(newValue.compareTo("")==0){
					    		try {
									clearList();
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    		importerList();
					    	}
			        }else if(newValue.length()==0 ) {
			        	listerBtn.setDisable(true);
			        }
			        if(newValue.length()>0){
			        	listerBtn.setDisable(false);
			        }
			    }
			});
		 
		 max.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	max.setText(newValue.replaceAll("[^\\d]", ""));
			        }else if(newValue.length()>0){
			        	double entredMax = Double.parseDouble(newValue);
			        	ObservableList<Salarier> obs = FXCollections.observableArrayList(data.getImportlist());
				    	ObservableList<Salarier> obs1 = FXCollections.observableArrayList();
				    	ObservableList<Category> obsCat = FXCollections.observableArrayList(data.getClassName());
				    	ObservableList<Category> obsCat1 = FXCollections.observableArrayList();
				    	for (Salarier s : obs) {
				        	if(s.getSalaire()<entredMax) {
				        		obs1.add(s);obsCat1.add(obsCat.get(obs.indexOf(s)));
				       		}else if(s.getSalaire()>entredMax){
				       			obs1.remove(s);obsCat1.remove(obsCat.get(obs.indexOf(s)));
				       		}
				        }
				    	 table.setItems(obs1);
				    		table2.setItems(obsCat1);
				    		
				    		if(newValue.compareTo("")==0){
					    		try {
									clearList();
								} catch (Throwable e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    		importerList();
					    	}
			        	
			        }else if(newValue.length()==0 ) {
			        	listerBtn.setDisable(true);
			        }
			        if(newValue.length()>0){
			        	listerBtn.setDisable(false);
			        }
			    }
			});
		 
		 empOrVen.selectedProperty().addListener((Observable, oldValue, newValue ) ->
		 {
			if(newValue) {
				empOrVen.setText("Emplyoe");
				try {
					for ( int i = 0; i<table.getItems().size(); i++) {
					    table.getItems().clear();
					    table2.getItems().clear();
					}
					importEmp();
					
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				try {
					for ( int i = 0; i<table.getItems().size(); i++) {
					    table.getItems().clear();
					    table2.getItems().clear();
					}
					empOrVen.setText("Vendeur");
					importVen();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		 });
		 
		 search.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			    	String lowerCaseFiltre = newValue.toLowerCase();
			    	ObservableList<Salarier> obs = FXCollections.observableArrayList(data.getImportlist());
			    	ObservableList<Salarier> obs1 = FXCollections.observableArrayList();
			    	ObservableList<Category> obsCat = FXCollections.observableArrayList(data.getClassName());
			    	ObservableList<Category> obsCat1 = FXCollections.observableArrayList();
			        for (Salarier s : obs) {
			        	if(lowerCaseFiltre.length()>0) {
			        		if(s.getNom().toLowerCase().contains(lowerCaseFiltre) || s.getEmail().toLowerCase().contains(lowerCaseFiltre)) {
			        			obs1.add(s);obsCat1.add(obsCat.get(obs.indexOf(s)));
			        		}else if(!s.getNom().toLowerCase().contains(lowerCaseFiltre) || !s.getEmail().toLowerCase().contains(lowerCaseFiltre)){
			        			obs1.remove(s);obsCat1.remove(obsCat.get(obs.indexOf(s)));
			        		}
			        	}
			        }
			        table.setItems(obs1);
		    		table2.setItems(obsCat1);
		    		
		    		if(newValue.compareTo("")==0){
			    		try {
							clearList();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    		importerList();
			    	}
			    }
			});
		 
		 
		 min.setText("0");
		 max.setText("9999");
		 
		 this.DataPlusFaible = new SalarierPlusFaible();
		 this.DataPlusFortVendeur = new GetPlusFortVendeur();
		 this.DataGetVendeur =  new DataBaseGetVendeur() ;
		 this.DataGetEmploye = new DataBaseGetEmploye();
		 this.DataByAnc = new ListerParAnc();
		 this.DataBetween = new ListerBetweenSalaire(Double.parseDouble(min.getText()), Double.parseDouble(max.getText()));
		 this.data = new DataBaseUtility();
		 
		 try {
			importList();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List<Salarier> sal;
	private List<Category> ct;
	public void importerList() {
		try {
			clearList();
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		table.getItems().addAll(sal);
		table2.getItems().addAll(ct);
	}
	
	public void closing(Stage stage){		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("You're about to cancel!");
		alert.setContentText("Do you want to save before exiting?");
		
		if (alert.showAndWait().get() == ButtonType.OK){
			stage.close();
			try {
				clearList();
				importerList();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
	public void loadDetailForUpdate(Salarier e) {
		try {
			//Load clicked employe matricule into the static variable mat
	 		FXMLLoader fxmlLoader = new FXMLLoader();
		 	fxmlLoader.setLocation(getClass().getResource("UpdateFx.fxml"));
			fxmlLoader.load();
			UpdateFxController detailControl = (UpdateFxController) fxmlLoader.getController();
	 		detailControl.mat= e.getMatricule();
	 		
	 		//Start of the second vue
	 		FXMLLoader Due = new FXMLLoader();
		 	Due.setLocation(getClass().getResource("UpdateFx.fxml"));
			Parent hama = Due.load();
	 		Scene scene = new Scene(hama);
	 		
	        Stage stage = new Stage();
	        
	        stage.setTitle("Update salarié de matricule " + e.getMatricule());
	        stage.setScene(scene);
	        stage.setOnCloseRequest(event -> {
				event.consume();
				closing(stage);	
				try {
					clearList();
					importerList();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
	        stage.showAndWait();
	        try {
				clearList();importerList();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        
	        
	        
	      
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
	
	public void loadDetail(Salarier e) {
	 	try {
	 		FXMLLoader fxmlLoader = new FXMLLoader();
		 	fxmlLoader.setLocation(getClass().getResource("VueDetailEmploye.fxml"));
			fxmlLoader.load();
			vueDetailController detailControl = (vueDetailController) fxmlLoader.getController();
	 		detailControl.num= e.getMatricule();
	 		FXMLLoader Due = new FXMLLoader();
		 	Due.setLocation(getClass().getResource("VueDetailEmploye.fxml"));
			Parent hama = Due.load();
	 		Scene scene = new Scene(hama);
	        Stage stage = new Stage();
	        stage.setTitle(e.getNom());
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void plusFaible() throws Throwable {
		clearList();
		table.getItems().addAll(DataPlusFaible.getImportlist());
		table2.getItems().addAll(DataPlusFaible.getClassName());
	}
	
	@FXML
	public void plusFortVendeur() throws Throwable {
		clearList();
		table.getItems().addAll(DataPlusFortVendeur.getImportlist());
		table2.getItems().addAll(DataPlusFortVendeur.getClassName());
	}
	
	
	 EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {  

			@Override
			public void handle(ActionEvent event) {
				try {
					if((min.getText()==null)||(max.getText()==null)) {
						listerBtn.setText("Put a Value");listerBtn.setDisable(true);
					}
					else{
						listerBtn.setText("Lister entre deux salaire");listerBtn.setDisable(false);
						importListBetween();
					}
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  
	          
	    };  
	
	public void importVen() {
		table.getItems().addAll(DataGetVendeur.getImportEmployeList());
		table2.getItems().addAll(DataGetVendeur.getClassName());
	}
	
	public void importEmp() {
		table.getItems().addAll(DataGetEmploye.getImportEmployeList());
		table2.getItems().addAll(DataGetEmploye.getClassName());
	}
	
	@FXML
	public void importListParAnc() throws Throwable {
		clearList();
		table.getItems().addAll(DataByAnc.getImportlist());
		table2.getItems().addAll(DataByAnc.getClassName());
	}
		
	@FXML 
	public void importListBetween() throws Throwable{
		for ( int i = 0; i<table.getItems().size(); i++) {
		    table.getItems().clear();
		    table2.getItems().clear();
		}
		table.getItems().addAll(DataBetween.getImportlistBetween(Double.parseDouble(min.getText()),Double.parseDouble(max.getText())));
		table2.getItems().addAll(DataBetween.getImportlistBetweenCat(Double.parseDouble(min.getText()),Double.parseDouble(max.getText())));
	}
	
	@FXML
	public void addSalarier() {
		Parent vueDetailler;
		try {
			vueDetailler = FXMLLoader.load(getClass().getResource("Ajout.fxml"));
			Scene scene = new Scene(vueDetailler);
	        Stage stage = new Stage();
	        stage.setTitle("Ajouter un nouveau Salarié");
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearList() throws Throwable{
		for ( int i = 0; i<table.getItems().size(); i++) {
		    table.getItems().clear();
		    table2.getItems().clear();
		}
	}
	
	private Stage stage;
	private Scene scene;
	
	public void switchToVueScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddSalarier.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Ajouter un salarié");
		stage.show();
		stage.setOnCloseRequest(null);
	}
	@FXML
	public void deleteS(Salarier selected) throws Throwable{
		try{
			PreparedStatement ps=Connexion.getCn().prepareStatement("delete from entreprise where matriculeE=?");  
			ps.setInt(1, selected.getMatricule());
			int i=ps.executeUpdate();  
		}catch(Exception e){ System.out.println(e);} 
	}
	
	@FXML
	public void importList() throws Throwable{
		empOrVen.setText("Employe ou Vendeur");
		empOrVen.selectedProperty().set(false);
		clearList();
		table.getItems().addAll(data.getImportlist());
		table2.getItems().addAll(data.getClassName());
	}
	
	@FXML
	public void exportList() throws Throwable{
		salarie = new ArrayList<Salarier>();
		categorie = new ArrayList<Category>();
		int col = 15;
		File exFile = new File("C:\\Users\\Moham\\OneDrive\\Documents\\Java Files\\list.txt");
		FileWriter exFr = new FileWriter(exFile);
		BufferedWriter bfr = new BufferedWriter(exFr);
		String newLine = "";
		try{  		
			Statement stmt=Connexion.getCn().createStatement();  
			ResultSet rs=stmt.executeQuery("select * from entreprise");
	
			while(rs.next())  
				if(rs.getString(8)=="Employe") {
					salarie.add(new Employe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6)));
					categorie.add(new Category(rs.getString(8)));
				}
				else {
					salarie.add(new Vendeur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(5), rs.getDouble(6)));
					categorie.add(new Category(rs.getString(8)));
				} 
		}catch(Exception e){ System.out.println(e);} 
		
		Iterator<Salarier> itS = salarie.iterator();
		Iterator<Category> itC = categorie.iterator();
		for(int i = 0; i<col*8; i++) {
			bfr.write("-");
		}
		bfr.newLine();
		bfr.write("Matricule       |Nom             |Email           |Salaire         |Recrutement     |Category        |");
		
		
		while(itS.hasNext()) {
			bfr.newLine();
			for(int i = 0; i<col*8; i++) {
				bfr.write("-");
			}
			bfr.newLine();
			newLine = itS.next().toString()+"|"+itC.next().toString();
			bfr.write(newLine);
		}
		bfr.close();
		
	}
	
	@FXML
	private TextField min;
	
	@FXML
	private TextField max;
	
	@FXML
	private TextField search;
	
	@FXML
	private ToggleButton empOrVen;
	
	//table
		@FXML
		private TableView<Salarier> table;
		
		//table column
		@FXML 
		 private TableColumn<Salarier, Integer> matCol;
		
		@FXML 
		 private TableColumn<Salarier, String> nomCol;
		
		@FXML 
		 private TableColumn<Salarier, String> emailCol;
		
		@FXML 
		 private TableColumn<Salarier, Double> salaireCol;
		
		@FXML 
		 private TableColumn<Salarier, Double> dateCol;
		
		@FXML 
		 private TableColumn<Salarier, Double> supCol;
		
		@FXML
		private TableView<Category> table2;
		
		@FXML 
		private TableColumn<Category, String> categoryCol;
		
		@FXML
		private TableColumn seeDetailButtonCol;
		
		@FXML
		private TableColumn modifyButtonCol;
		
		@FXML
		private TableColumn deleteButtonCol;
		
		//button
		@FXML 
		 private Button ajoutBtn;
		
		@FXML 
		 private Button AfficherTout;
		
		@FXML 
		 private Button deleteBtn;
		
		@FXML 
		 private Button detailBtn; 
		
		@FXML 
		 private Button modifyBtn;
		
		@FXML 
		 private Button listerBtn;
		
		//switcher between scene
		@FXML
		private Button listerParAncienette;
			
		@FXML
		private Button listerParSalaire;
		
		@FXML
		private Button export;


}
