package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import controllers.WorkerCC;
import entities.DepartmentManager;
import entities.Park;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class DepManagerVacancyGuiCC implements Initializable {

	@FXML
	private ComboBox<String> parkCombo;

	@FXML
	private Button chackVacancyBtn;

	@FXML
	private Label NumVisitorLabel;

	@FXML
	private Label AvailablePlacesLabel;

	@FXML
	private Label AvailableUnplannedPlacesLabel;

	/**
	 * this function present in the label the number of the Visitor and number of Available Places and number of  Available Unplanned Places in the selected park 
	 * @param event
	 */
	@FXML
	void CheckClicked(ActionEvent event) {
		Park p = WorkerCC.checkParkVacancy(parkCombo.getValue());
		NumVisitorLabel.setText(String.valueOf(p.getCurrentNumOfVisitors()));
		AvailablePlacesLabel.setText(String.valueOf(p.getMaxNumOfVisitor() - p.getCurrentNumOfVisitors()));
		AvailableUnplannedPlacesLabel.setText(String.valueOf((p.getMaxNumOfVisitor() - p.getMaxNumOfOrders()) - p.getCurrentNumOfUnplannedVisitors()));

	}

	/**
	 * this function initialize the ComboBox that present all park name  
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DepartmentManager depManager = (DepartmentManager) GoNatureClient.currentWorker;
		ArrayList<String> parkNames = new ArrayList<String>();
		for (Park p : depManager.getParks())
			parkNames.add(p.getName());
		ObservableList<String> arr = FXCollections.observableArrayList(parkNames);
		parkCombo.setItems(arr);

	}

}
