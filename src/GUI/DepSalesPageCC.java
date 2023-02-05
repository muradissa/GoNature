package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.GoNatureClient;
import controllers.DepartmentMangerCC;
import controllers.ReportsController;
import entities.DepartmentManager;
import entities.ManagerRequest;
import entities.Park;
import entities.Sale;
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

public class DepSalesPageCC implements Initializable {

	@FXML
	private Button confirmBtn;

	@FXML
	private Button RejectBtn;

	@FXML
	private TableView<TableViewHelper> SalesTable;

	@FXML
	private TableColumn<TableViewHelper, String> ParkCol;

	@FXML
	private TableColumn<TableViewHelper, String> NameCol;

	@FXML
	private TableColumn<TableViewHelper, String> StartDateCol;

	@FXML
	private TableColumn<TableViewHelper, String> EndDateCol;

	@FXML
	private TableColumn<TableViewHelper, String> RequestDateCol;

	@FXML
	private TableColumn<TableViewHelper, Integer> SalePercentCol;

	private TableViewHelper selectedSale = null;

	private DepartmentManager dep;

	@FXML
	void ConfirmBtnClicked(ActionEvent event) {
		if (selectedSale != null) {
			Sale sale = new Sale(selectedSale.getStartDate(), selectedSale.getEndDate(),
					(Integer) selectedSale.getNewValue(), selectedSale.getName(), selectedSale.getParkName(),
					selectedSale.getStatus(), selectedSale.getDate());
			DepartmentMangerCC.confirmSale(sale, sale.getParkName());
			SalesTable.getItems().removeAll(selectedSale);
		}
	}

	@FXML
	void RejectBtnClicked(ActionEvent event) {
		if (selectedSale != null) {
			Sale sale = new Sale(selectedSale.getStartDate(), selectedSale.getEndDate(),
					(Integer) selectedSale.getNewValue(), selectedSale.getName(), selectedSale.getParkName(),
					selectedSale.getStatus(), selectedSale.getDate());
			DepartmentMangerCC.UnconfirmSale(sale, sale.getParkName());
			SalesTable.getItems().removeAll(selectedSale);

		}
	}

	/**
	 * this function initialize the selected sale as value of the selected row
	 * 
	 * @param event
	 */
	@FXML
	void selectSale(MouseEvent event) {
		if (SalesTable.getSelectionModel().getSelectedItem() != null) {
			selectedSale = SalesTable.getSelectionModel().getSelectedItem();
		}
	}

	/**
	 * this function prepare the table of all request sale in the park
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dep = (DepartmentManager) GoNatureClient.currentWorker;
		ParkCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("parkName"));
		RequestDateCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("date"));
		NameCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("name"));
		StartDateCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("startDate"));
		EndDateCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, String>("endDate"));
		SalePercentCol.setCellValueFactory(new PropertyValueFactory<TableViewHelper, Integer>("newValue"));
		SalesTable.setItems(getRequests());

	}

	/**
	 * this function get all sale request in the selected park
	 * 
	 * @return ObservableList TableViewHelper hold all the sales request
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
					if (temp instanceof Sale) {
						if (((Sale) temp).getStatus().equals("waiting")) {
							TableViewHelper t = new TableViewHelper(i, ((Sale) temp).getSaleName(),
									((Sale) temp).getStatus(), ((Sale) temp).getSendDate(),
									((Sale) temp).getParkName());
							t.setStartDate(((Sale) temp).getStartDate());
							t.setEndDate(((Sale) temp).getEndDate());
							t.setNewValue(Integer.valueOf(((Sale) temp).getSalePercent()));
							requests.add(t);

							i++;
						}
					}
			}
		}
		return requests;
	}

}