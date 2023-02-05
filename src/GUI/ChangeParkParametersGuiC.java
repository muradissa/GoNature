package GUI;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.GoNatureClient;
import controllers.ParkMangerCC;
import controllers.inputValidator;
import entities.ParameterChangeRequest;
import entities.ParameterType;
import entities.Park;
import entities.ParkManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChangeParkParametersGuiC implements Initializable{
	ParkManager parkManager = (ParkManager) GoNatureClient.currentWorker;
    @FXML
    private Button ChangeStayTimeBtn;

    @FXML
    private Button ChangeMaxNumVisitorBtn;

    @FXML
    private Button ChangeNumUnplannedVBtn;

    @FXML
    private TextField StayTimeOldValue;

    @FXML
    private TextField MaxNumberVisitorsOldV;

    @FXML
    private TextField MaxNumUnplannedOldV;

    @FXML
    private TextField StayTimeNewValue;

    @FXML
    private TextField MaxNumOfVisitorNewV;

    @FXML
    private TextField MaxNumUnplannedNewV;
    
    @FXML
    private Label message;

    
    
    /** 
     * this function insert "change max number of unplanned Visitor " request and insert to DB
     * @param event
     */
    @FXML
    void ChangeMaxNumOfUnplannedV(MouseEvent event) {

    	long millis=System.currentTimeMillis();
    	ArrayList<Object> parameter  = ParkMangerCC.getParameter(parkManager.getPark().getName());    
    	if(!inputValidator.checkInputIfNumber(MaxNumUnplannedNewV.getText()) || (Long.valueOf(MaxNumUnplannedNewV.getText()) > Long.valueOf(MaxNumberVisitorsOldV.getText()))) {
    		message.setVisible(true);
    		MaxNumUnplannedNewV.setFocusTraversable(true);
    		message.setText("Please Enter Correct number");
    		
    	}else {
    		message.setVisible(false);
         	ParameterChangeRequest request = new ParameterChangeRequest(ParameterType.MAXNUMBEROFORDER,String.valueOf((Integer)parameter.get(1)-Integer.valueOf(MaxNumUnplannedNewV.getText()))
    			, new Park(parkManager.getPark().getName(), null, null, 0, 0)
    			, null, null);
    	    ParkMangerCC.setParameter(request, new java.sql.Date(millis));
    	
    	    popUpCC success=new popUpCC();
		    success.start(new Stage(),true, "Awesome.", "Your Request has been sent", "ok!");
    	}
    }

    
    
    /** 
     * this function insert "change max number of Visitor " request and insert to DB
     * @param event
     */
    @FXML
    void ChangeNumberOfVisitortParameter(MouseEvent event) {
    	long millis=System.currentTimeMillis();
    	
    	if(!inputValidator.checkInputIfNumber(MaxNumOfVisitorNewV.getText()) ) {
    		message.setVisible(true);
    		message.setText("Please Enter Correct number");
    		
    	}else {
    		message.setVisible(false);
    	ParameterChangeRequest request = new ParameterChangeRequest(ParameterType.MAXNUMBEROFVISITORS, MaxNumOfVisitorNewV.getText()
    			, new Park(parkManager.getPark().getName(), null, null, 0, 0)
    			, null, null);
    	ParkMangerCC.setParameter(request, new java.sql.Date(millis));
    	
    	popUpCC success=new popUpCC();
		success.start(new Stage(),true, "Awesome.", "Your Request has been sent", "ok!");
    	}
    }
    
    

    /**
     * this function insert "Stay time" request and insert to DB
     * @param event
     */
    @FXML
    void ChangeStayTimeParameter(MouseEvent event) {
    	long millis=System.currentTimeMillis();
    	if(StayTimeNewValue.getText().equals("") || !inputValidator.checkTime(StayTimeNewValue.getText())) {
    		message.setText("Please Enter Correct Time");
    		message.setVisible(true);
    	} else {
    		
    		message.setVisible(false);
    	ParameterChangeRequest request = new ParameterChangeRequest(ParameterType.STAYTIME, StayTimeNewValue.getText()
    			, new Park(parkManager.getPark().getName(), null, null, 0, 0)
    			, null, null);
    	ParkMangerCC.setParameter(request, new java.sql.Date(millis));

    	popUpCC success=new popUpCC();
		success.start(new Stage(),true, "Awesome.", "Your Request has been sent", "ok!");
    	}
    	
    }
    
    

	/**
	 * this function prepare the old value's label before loading the GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ArrayList<Object> parameter  = ParkMangerCC.getParameter(parkManager.getPark().getName());
		StayTimeOldValue.setText(((Time)parameter.get(0)).toString());
		MaxNumberVisitorsOldV.setText(String.valueOf((Integer)parameter.get(1)));
		MaxNumUnplannedOldV.setText(String.valueOf((Integer)parameter.get(1)-(Integer)parameter.get(2)));
		
	}
}
