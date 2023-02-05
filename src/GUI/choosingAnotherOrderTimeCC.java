package GUI;

import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.OrderCC;
import controllers.WaitingListCC;
import entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * this class works when there isn't any left space in the park and the visitor
 * wants to choose another order time
 * 
 * @author Group2
 *
 */

public class choosingAnotherOrderTimeCC implements Initializable {

	static NewOrderTravelerCC controler;
	@FXML
	private AnchorPane anPane;
	@FXML
	private TableView<Order> choosingAnotherDATETIME_TableView;

	@FXML
	private TableColumn<Order, Date> choosingAnotherDATET_TableView;

	@FXML
	private TableColumn<Order, Time> choosingAnotherTIME_TableView;

	@FXML
	private Button choosingAnotherOrder_Btn;

	static VisitorHomePageCC cc;

	Order order;

	/**
	 * this method is launched when the user selects an order in the table view
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void choosingRableViewDateandTime(MouseEvent e) {
		if (choosingAnotherDATETIME_TableView.getSelectionModel().getSelectedItem() != null) {
			order = choosingAnotherDATETIME_TableView.getSelectionModel().getSelectedItem();
		}
	}

	/**
	 * this methods is launched when the user chooses another order time this
	 * methods opens a pop up on screen
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */

	@FXML
	void actionchoosingAnotherOrder(ActionEvent event) {

		Order changeOrder = NewOrderTravelerCC.newOrder;
		if (choosingAnotherDATETIME_TableView.getSelectionModel().getSelectedItem() != null) {
			changeOrder.setVisitDate(order.getVisitDate());
			changeOrder.setVisitTime(order.getVisitTime());
			System.out.println(order.getVisitTime() + "  " + order.getVisitDate());
			if (OrderCC.addOrder(changeOrder)) {
				popUpCC aa = new popUpCC();
				aa.start(new Stage(), true, "We are Waitting For You", "Your Order has been placed successfully!",
						"Ok , Great!");
				cc.openVisitorHomePage(null);
			} else
				System.out.println("You have Problem in database");
		} else {
			System.out.println("Error : take the time and date");
		}

	}

	/**
	 * this method runs at the beginning this method initialize the tableView of the
	 * relevant orders time and date
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Timestamp> array = new ArrayList<Timestamp>();
		array = OrderCC.fetchAvailibleOrderDates(NewOrderTravelerCC.newOrder.getVisitTime(),
				NewOrderTravelerCC.newOrder.getVisitDate(), NewOrderTravelerCC.newOrder.getNumOfVisitors(),
				NewOrderTravelerCC.newOrder.getPark().getName());
		ArrayList<Order> array2 = new ArrayList<Order>();
		for (int i = 0; i < array.size(); i++) {
			Date zz1 = new Date(array.get(i).getYear(), array.get(i).getMonth(), array.get(i).getDate());
			Time zz2 = new Time(array.get(i).getHours(), array.get(i).getMinutes(), 0);
			array2.add(new Order(null, zz1, zz2, i, null, null, null, 0, null, false));
		}
		choosingAnotherDATET_TableView.setCellValueFactory(new PropertyValueFactory<Order, Date>("visitDate"));
		choosingAnotherTIME_TableView.setCellValueFactory(new PropertyValueFactory<Order, Time>("visitTime"));
		choosingAnotherDATETIME_TableView.setItems(FXCollections.observableArrayList(array2));
		choosingAnotherDATETIME_TableView.refresh();
	}
}
