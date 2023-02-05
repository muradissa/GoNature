package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.GoNatureClient;
import controllers.DepartmentMangerCC;
import controllers.ReportsController;
import controllers.WorkerCC;
import entities.DepartmentManager;
import entities.ManagerRequest;
import entities.ParameterChangeRequest;
import entities.ParameterType;
import entities.Park;
import entities.TableViewHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ParameterConfirmGUICC implements Initializable {

	@FXML
	private Button confirmReqBtn;

	@FXML
	private TableColumn<TableViewHelper, String> ParkCol;

	@FXML
	private TableColumn<TableViewHelper, String> TypeCol;

	@FXML
	private TableColumn<TableViewHelper, String> SendDateCol;

	@FXML
	private TableColumn<TableViewHelper, String> OldValueCol;

	@FXML
	private TableColumn<TableViewHelper, String> NewValCol;

	@FXML
	private Button rejectReqBtn;

	@FXML
	private TableView<TableViewHelper> ParametersReqTable;

	private TableViewHelper selectedRed = null;

	private DepartmentManager dep;

	/**
	 * this function take the selected parameter and update it status in the DB to
	 * be confirmed
	 * 
	 * @param event
	 */
	@FXML
	void ConfirmReqClicked(ActionEvent event) {
		if (selectedRed != null) {
			Park park = null;
			for (Park p1 : dep.getParks())
				if (selectedRed.getParkName().equals(p1.getName())) {
					park = p1;
					break;
				}
			ParameterChangeRequest p = new ParameterChangeRequest(ParameterType.valueOf(selectedRed.getName()),
					selectedRed.getNewValue(), park, selectedRed.getDate(), selectedRed.getStatus());
			DepartmentMangerCC.confirmParameter(p, selectedRed.getDate(), selectedRed.getNewValue());
			ParametersReqTable.getItems().removeAll(ParametersReqTable.getSelectionModel().getSelectedItems());

		}

	}

	/**
	 * this function take the selected request and update it status to unconfirmed
	 * 
	 * @param event
	 */
	@FXML
	void rejectBtnClicked(ActionEvent event) {
		if (selectedRed != null) {
			Park park = null;
			for (Park p1 : dep.getParks())
				if (selectedRed.getParkName().equals(p1.getName())) {
					park = p1;
					break;
				}
			ParameterChangeRequest p = new ParameterChangeRequest(ParameterType.valueOf(selectedRed.getName()),
					selectedRed.getNewValue(), park, selectedRed.getDate(), selectedRed.getStatus());
			DepartmentMangerCC.UnconfirmParameter(p, selectedRed.getDate(), selectedRed.getNewValue());
		}
	}

	/**
	 * this function take the selected row and put it into selectedRed
	 * 
	 * @param event
	 */
	@FXML
	void selectedReq(MouseEvent event) {
		if (ParametersReqTable.getSelectionModel().getSelectedItem() != null) {
			selectedRed = ParametersReqTable.getSelectionModel().getSelectedItem();
			String n = selectedRed.getName();
			selectedRed.setName(n.replaceAll("\\s+", "").toUpperCase());

		}
	}

	/**
	 * this function prepare the tableView before loading the GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dep = (DepartmentManager) GoNatureClient.currentWorker;
		ParkCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("parkName"));
		TypeCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("name"));
		SendDateCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("date"));
		OldValueCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("oldValue"));
		NewValCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("newValue"));
		ParametersReqTable.setItems(getRequests());
	}

	/**
	 * this function gets all the requests from the DB
	 * 
	 * @return observableList of tableviewHelper that hold all park request
	 */
	private ObservableList<TableViewHelper> getRequests() {
		int i = 1;
		ObservableList<TableViewHelper> requests = FXCollections.observableArrayList();
		DepartmentManager dp = (DepartmentManager) GoNatureClient.currentWorker;
		ArrayList<ManagerRequest> result = null;
		for (Park p : dp.getParks()) {
			result = DepartmentMangerCC.GetAllRequests(p.getName());
			if (result != null) {
				for (ManagerRequest temp : result)
					if (temp instanceof ParameterChangeRequest)
						if (((ParameterChangeRequest) temp).getStatus().equals("waiting")) {
							TableViewHelper t = new TableViewHelper(i,
									ParameterType.getType(((ParameterChangeRequest) temp).getType()),
									((ParameterChangeRequest) temp).getStatus(),
									((ParameterChangeRequest) temp).getSendDate(),
									((ParameterChangeRequest) temp).getPark().getName());
							t.setClassType("Parameter");
							t.setOldValue(getOldParameter(t.getParkName(),
									((ParameterChangeRequest) temp).getType().toString()));
							t.setNewValue(((ParameterChangeRequest) temp).getNewValue());
							requests.add(t);
							i++;
						}
			}
		}
		return requests;
	}

	/**
	 * this function receive the name of the park and the type of the parameter
	 * 
	 * @param String name
	 * @param String type
	 * @return return the last updated parameter
	 */
	private Object getOldParameter(String name, String type) {
		Park p = WorkerCC.checkParkVacancy(name);
		System.out.println(type);
		switch (type) {
		case "MAXNUMBEROFORDER":
			return p.getMaxNumOfOrders();
		case "MAXNUMBEROFVISITORS":
			return p.getMaxNumOfVisitor();
		case "STAYTIME":
			return p.getStayTime();
		default:
			break;
		}
		return null;
	}
}
