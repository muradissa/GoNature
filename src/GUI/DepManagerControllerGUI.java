package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import controllers.DepartmentMangerCC;
import controllers.ReportsController;
import entities.DepartmentManager;
import entities.ManagerRequest;
import entities.ParameterChangeRequest;
import entities.ParameterType;
import entities.Park;
import entities.Sale;
import entities.TableViewHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DepManagerControllerGUI implements Initializable {

	@FXML
	private Label welcomeLabel;

	@FXML
	private TableColumn<TableViewHelper, Integer> RequestNumber;

	@FXML
	private TableColumn<TableViewHelper, String> ParkCol;

	@FXML
	private TableColumn<TableViewHelper, String> TypeCol;

	@FXML
	private TableColumn<TableViewHelper, String> StatusCol;

	@FXML
	private TableColumn<TableViewHelper, String> DateCol;

	@FXML
	private TableView<TableViewHelper> RequestsTableView;

	@FXML
	private AnchorPane AnPane;

	@FXML
	private Label SaleRequestsLabel;

	@FXML
	private Label ParamatersRequestLabel;

	@FXML
	private Button MoreInfoButton;

	private int ParameterRequestInTreatment, SaleRequestInTreatment;

	private TableViewHelper selectedReq = null;

	public static DepManagerBaseGUIController controller;

	/**
	 * this function loading the match GUI after clicking on View More Button if the
	 * selected row is sale , the function load department sale GUI if the selected
	 * row is parameter , the function load department parameters GUI
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void MoreInfoClicked(ActionEvent event) throws Exception {
		if (selectedReq != null) {
			switch (selectedReq.getClassType()) {
			case "Sale":
				try {

					controller.setTxt("Sales Confirm Page");
					AnPane.getChildren().clear();
					AnPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ConfirmSalesGui.fxml")));
					controller.selectedImage(controller.getSalesImage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Parameter":
				try {
					controller.setTxt("Parameters Confirm Page");
					AnPane.getChildren().clear();
					AnPane.getChildren().setAll(
							(Node) FXMLLoader.load(getClass().getResource("DepartmentManagerParametersGUI.fxml")));
					controller.selectedImage(controller.getParametersImage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			default:
				break;
			}
		}
	}

	/**
	 * this function initialize the table view row as the selected item in the table
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void RequestClicked(MouseEvent event) throws Exception {
		if (RequestsTableView.getSelectionModel().getSelectedItem() != null) {
			selectedReq = RequestsTableView.getSelectionModel().getSelectedItem();
		}
	}

	/**
	 * this function prepare the tableview that hold all the request in all the
	 * parks
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
		// MoreInfoButton.setStyle(HOVERED_BUTTON_STYLE);
		MoreInfoButton.hoverProperty().addListener(l -> {
			MoreInfoButton.setStyle("fx-background-color: red");

		});
		welcomeLabel.setText("Welcome, " + GoNatureClient.currentWorker.getFirstName() + " "
				+ GoNatureClient.currentWorker.getLastName());
		SaleRequestInTreatment = 0;
		ParameterRequestInTreatment = 0;
		RequestNumber.setCellValueFactory(new PropertyValueFactory<TableViewHelper, Integer>("id"));
		StatusCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("status"));
		TypeCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("name"));
		DateCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("date"));
		ParkCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("parkName"));
		RequestsTableView.setItems(getRequests());

		SaleRequestsLabel
				.setText("You Have\r\n" + SaleRequestInTreatment + "\r\n" + "Sale requests \r\n in treatment\r\n" + "");

		ParamatersRequestLabel.setText("You Have\r\n" + ParameterRequestInTreatment + "\r\n" + "Parameter change\r\n"
				+ "requests in treatment");

	}

	/**
	 * this function takes all the parameters and sale's request of specific park
	 * from the DB via the ReportsController it takes the park name from the
	 * combobox
	 * 
	 * @return ObservableList of TableViewHelper that hold all the requests
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
					if (temp instanceof ParameterChangeRequest) {
						if (((ParameterChangeRequest) temp).getStatus().equals("waiting")) {
							ParameterRequestInTreatment++;
							TableViewHelper t = new TableViewHelper(i,
									ParameterType.getType(((ParameterChangeRequest) temp).getType()),
									((ParameterChangeRequest) temp).getStatus(),
									((ParameterChangeRequest) temp).getSendDate(),
									((ParameterChangeRequest) temp).getPark().getName());
							t.setClassType("Parameter");
							requests.add(t);

							i++;
						}
					} else {
						if (((Sale) temp).getStatus().equals("waiting")) {
							SaleRequestInTreatment++;
							TableViewHelper t = new TableViewHelper(i, ((Sale) temp).getSaleName(),
									((Sale) temp).getStatus(), ((Sale) temp).getStartDate(),
									((Sale) temp).getParkName());
							t.setClassType("Sale");
							requests.add(t);

							i++;
						}
					}
			}
		}
		return requests;
	}

	/**
	 * this function remove the effect of more info button when the cursor is not on
	 * the button
	 * 
	 * @param event
	 */
	@FXML
	void MouseExited(MouseEvent event) {
		MoreInfoButton.setEffect(null);
	}

	/**
	 * this function set a effect in more info button when the cursor is on the
	 * button
	 * 
	 * @param event
	 */
	@FXML
	void mouseEntered(MouseEvent event) {
		MoreInfoButton.setEffect(new DropShadow());
	}

}
