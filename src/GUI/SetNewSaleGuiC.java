package GUI;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import controllers.ParkMangerCC;
import controllers.inputValidator;
import entities.ParkManager;
import entities.Sale;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class SetNewSaleGuiC implements Initializable{
	ParkManager parkManager = (ParkManager) GoNatureClient.currentWorker;
    @FXML
    private DatePicker StartDate;

    @FXML
    private DatePicker EndDate;

    @FXML
    private TextField SalePrecent;

    @FXML
    private TextField SaleName;

    @FXML
    private Button SendBtn;
    
    @FXML
    private Label message;
    
    
    /**
     * this function take the sale details from the label's and insert a new sale request to the Database 
     * @param event
     */
    @SuppressWarnings("deprecation")
	@FXML
    void SendSaleRequest(MouseEvent event) {
    	 
    	if(StartDate.getValue()==null || EndDate.getValue()==null || !inputValidator.CheckSaleInput(SalePrecent.getText()) || SaleName.getText().equals("")) {
    		message.setText("please Enter Correct Values");
    		message.setVisible(true);
    		
    	}else {
    		
    	message.setVisible(false);
    	Date startDay= new Date(StartDate.getValue().getYear()-1900,StartDate.getValue().getMonthValue()-1,StartDate.getValue().getDayOfMonth());
    	Date endDate = new Date(EndDate.getValue().getYear()-1900,EndDate.getValue().getMonthValue()-1,EndDate.getValue().getDayOfMonth());
    	SalePrecent.getText();
    	SaleName.getText();
    	long millis=System.currentTimeMillis();  
        java.sql.Date Currentdate=new java.sql.Date(millis);  
   
        if(endDate.getYear()<startDay.getYear() || (startDay.getYear()==endDate.getYear() && endDate.getDay()<startDay.getDay())){
        	message.setText("End date can't be before start Date !");
    		message.setVisible(true);
    		
        } else {
        	
        	message.setVisible(false); 
    	Sale sale = new Sale(startDay, endDate, Integer.valueOf(SalePrecent.getText()), SaleName.getText(), parkManager.getPark().getName(), "waiting" , Currentdate);
    	if(!ParkMangerCC.setSale(sale, parkManager.getPark().getName())) {
    		message.setText("There is already confirmed Sale in this date!");
    		message.setVisible(true);    		
    		
    	}else {
     	String title = "Congratulations sir";
        String message = "You've successfully created your Sale";        
        popUpCC success=new popUpCC();
		success.start(new Stage(),true, "Awesome.", "Your Request has been sent", "ok!");
    	}
       }
    }
  }

    
    
	/**
	 * this function prepare the date's datePicker 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// close the posability to chose old date 
		StartDate.setDayCellFactory(picker -> new DateCell() {
		        public void updateItem(LocalDate date, boolean empty) {
		            super.updateItem(date, empty);
		            LocalDate today = LocalDate.now();

		            setDisable(empty || date.compareTo(today) < 0 );
		        }
		    });
		//close the old possability to chose old date 
		EndDate.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();

	            setDisable(empty || date.compareTo(today) < 0 );
	        }
	    });
	}
}
