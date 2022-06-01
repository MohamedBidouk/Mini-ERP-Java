package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class vueDetailController extends EntrepriseController implements Initializable{
	public static int num ;
	String nam;

	private ShowDetailUtility data;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 this.data = new ShowDetailUtility(num); 
		 try {
			 System.out.println(num);
			importList();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 nam = data.getClassName();
		 System.out.println(data.getClassName());
		 System.out.println(data.isss(data.getClassName()));
		 if(!data.isss(data.getClassName())) {
			 supCol.setText("Taux de vente");
			 matCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
			 nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
			 emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
			 salaireCol.setCellValueFactory(new PropertyValueFactory<>("Salaire"));
			 dateCol.setCellValueFactory(new PropertyValueFactory<>("recru"));
			 supCol.setCellValueFactory(new PropertyValueFactory<>("vente"));
			 
		 }else {
			 supCol.setText("Nombre d'heure sup");
			 matCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
			 nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
			 emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
			 salaireCol.setCellValueFactory(new PropertyValueFactory<>("Salaire"));
			 dateCol.setCellValueFactory(new PropertyValueFactory<>("recru"));
			 supCol.setCellValueFactory(new PropertyValueFactory<>("sup"));
		 }
		
		
		
	}
	
	public void importEmp() {
		
	}
	
	@FXML
	 public void importList() throws Throwable{
			table.getItems().addAll(data.getImportlist());
		}
	
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
	private Button btn;
	
	

}
