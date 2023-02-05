package GUI;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.GoNatureClient;
import controllers.DepartmentMangerCC;
import controllers.ReportsController;
import entities.ManagerRequest;
import entities.ParameterChangeRequest;
import entities.ParkManager;
import entities.Sale;
import entities.TableViewHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ParkMangerHomeGuiC implements Initializable{
	
	ParkManager ParkManager = (ParkManager) GoNatureClient.currentWorker;
	 

	    @FXML
	    private Label WelcomeLabel;

	    @FXML
	    private Label SalesInTreatmentCard;

	    @FXML
	    private Label AllSalesCard;

	    @FXML
	    private Label ParameterInTreatment;

	    @FXML
	    private Label AllParametersRequest;

	    @FXML
	    private TableColumn<TableViewHelper, Integer> RequestNumber;
	
	    @FXML
	    private TableColumn<TableViewHelper, String> status; 
	
	    @FXML
	    private TableColumn<TableViewHelper, String> requestType;
	
	    @FXML
	    private TableColumn<TableViewHelper, Date> IssueDate;
	    
	    @FXML
	    private TableView<TableViewHelper> AllRequestTable;
	    
	    
	   static int SaleRequestInTreatment=0,AllSale=0,ParameterRequestInTreatment=0,AllParameterRequest=0;
	    
	  
	/**
	 * this function prepare the GUI before Load it 
	 * it Prepare the Request table view by get the data from the DB
	 * in addition , it update the status Card of this GUi 
	 * status card : parkManger name , status of his all sale request ,status of his all parameter request 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		    SaleRequestInTreatment=0;
		    AllSale=0;
		    ParameterRequestInTreatment=0;
		    AllParameterRequest=0;
	        WelcomeLabel.setText("Welcome " +ParkManager.getFirstName());
		    RequestNumber.setCellValueFactory(new PropertyValueFactory<TableViewHelper, Integer>("id"));
		    status.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("status"));
			requestType.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("name"));
			IssueDate.setCellValueFactory(new PropertyValueFactory<TableViewHelper, Date>("date"));
			AllRequestTable.setItems(getRequests());	
		
			SalesInTreatmentCard.setText("You Have\r\n" + SaleRequestInTreatment +
					"\r\n" + 
					"Sale requests in treatment\r\n" + 
					"");
			AllSalesCard.setText("You Have Sent\r\n" + AllSale +
					"\r\n" + 
					"Sale requests");
			
			
			ParameterInTreatment.setText("You Have\r\n" + ParameterRequestInTreatment +
					"\r\n" + 
					"Parameter change\r\n" + 
					"requests in treatment");
			
			AllParametersRequest.setText("You Have Sent\r\n" + AllParameterRequest +
					"\r\n" + 
					"Parameter change\r\n" + 
					"requests");
	}
	
	
	
	
	
	
	/**
	 * this function get all this worker request (parameter's and sale's)
	 * @return ObservableList of TableViewerHelper that hold all Request (Sale and parameter's) and their status and their sendDate 
	 */
	ObservableList<TableViewHelper> getRequests() {
					int i=1;
		ObservableList<TableViewHelper> requests=FXCollections.observableArrayList();
					
		ArrayList<ManagerRequest> result = DepartmentMangerCC.GetAllRequests(ParkManager.getPark().getName());
			if(result!=null) {
						
			for(ManagerRequest temp : result)
			    if(temp instanceof ParameterChangeRequest) {
			    	AllParameterRequest++;
			    requests.add(new TableViewHelper(i,
		     	((ParameterChangeRequest)temp).getType().toString(),((ParameterChangeRequest)temp).getStatus(),
			    ((ParameterChangeRequest)temp).getSendDate()));
			    if(((ParameterChangeRequest)temp).getStatus().equals("waiting"))
			    	ParameterRequestInTreatment++;
			    i++;
			 }
		else {
			    AllSale++;
				requests.add(new TableViewHelper(
				i,((Sale)temp).getSaleName(),((Sale)temp).getStatus(),((Sale)temp).getSendDate()));
				
				if(((Sale)temp).getStatus().equals("waiting"))
					SaleRequestInTreatment++;
				i++;
				
				 }	
			}
			return requests;
	}
			
}
	

	

