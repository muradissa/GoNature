package GUI;



import Client.GoNatureClient;
import controllers.WorkerCC;
import entities.Park;
import entities.ParkManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CheckParkVacancyGuiC {
	ParkManager ParkManager = (ParkManager) GoNatureClient.currentWorker;
	
	   @FXML
	    private Button CheckVacancyBtn;

	    @FXML
	    private Label NumberOfCurrentVisitorLabel;

	    @FXML
	    private Label NumberOfAvailablePlaces;

	    
	    /**
	     * this function show number of current visitors and number of availability places in the park 
	     * it Edit the labels when the user click the chickParkVacancey Button 
	     * @param event
	     */
	    @FXML
	    void CheckVacancy(MouseEvent event) {
	    	Park park = WorkerCC.checkParkVacancy(ParkManager.getPark().getName());
	    	NumberOfCurrentVisitorLabel.setText(String.valueOf(park.getCurrentNumOfVisitors()));
	    	NumberOfAvailablePlaces.setText(String.valueOf(park.getMaxNumOfVisitor()-park.getCurrentNumOfVisitors()));
	    }


}
