package GUI;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import controllers.IdentificationCC;
import controllers.OrderCC;
import controllers.PaymentController;
import controllers.WorkerCC;
import controllers.inputValidator;
import entities.Order;
import entities.OrderType;
import entities.Park;
import entities.Subscriber;
import entities.Traveler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * controller for unplanned visit worker GUI
 * 
 * @author ayman
 *
 */
public class UnplannedVisitCC implements Initializable {

	@FXML
	private ComboBox<OrderType> visitTypeComboBox;

	@FXML
	private Button showBillBtn;

	@FXML
	private TextField numOfVisitorsTextField;

	@FXML
	private TextField subscriptionNumberTextField;

	@FXML
	private Label PriceLabel;

	@FXML
	private Button CalculatePriceBtn;

	@FXML
	private Button AddOrderBtn;

	@FXML
	private ComboBox<Integer> numOfVisitorsComboBox;

	@FXML
	private Label idNumberLabel;

	@FXML
	private Label errorMsgLabel;

	private Order currOrder;

	/**
	 * this method is launched when the user clicks on addOrder button the method
	 * checks if the selected unplanned order could be places, according to
	 * available unplanned places in the park if the order could be added, the
	 * method adds order to database and shows success popUp if the order couldn't
	 * be added,the method displays error popUp
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void addOrder(ActionEvent event) {
		calculatePrice(null);
		if (currOrder != null) {
			Park p = WorkerCC.checkParkVacancy(GoNatureClient.currentWorker.getPark().getName());
			int maxNumOfUnplannedVisitors = p.getMaxNumOfVisitor() - p.getMaxNumOfOrders();
			int availableUnplannedVisitors = maxNumOfUnplannedVisitors - p.getCurrentNumOfUnplannedVisitors();
			if (currOrder.getNumOfVisitors() <= availableUnplannedVisitors) {
				OrderCC.addOrder(currOrder);
				popUpCC success = new popUpCC();
				success.start(new Stage(), true, "Awesome.", "order added succesfully", "Great!");
			} else {
				popUpCC failure = new popUpCC();
				failure.start(new Stage(), false, "oops.", "not enough space in the park", "Ok");
			}
		}

	}

	/**
	 * this method is launched when the user clicks on calculate price button the
	 * method checks if user input is correct, if input is correct the method will
	 * calculate the price for the selected order, otherwise the method will display
	 * error msg as label
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void calculatePrice(ActionEvent event) {
		OrderType orderType = visitTypeComboBox.getSelectionModel().getSelectedItem();
		int numOfVisitors = numOfVisitorsComboBox.getSelectionModel().getSelectedItem();
		String id = subscriptionNumberTextField.getText();
		Order order;
		if (orderType == OrderType.UNPLANNEDGUIDE || orderType == OrderType.UNPLANNEDTRAVELER) {
			if (!inputValidator.checkId(id))// check guide and traveler id format
			{
				displayError("incorrect id format");
				showBillBtn.setVisible(false);
				currOrder = null;
				return;
			}
			if (orderType == OrderType.UNPLANNEDGUIDE) {
				if (!IdentificationCC.checkGuide(id))// incorrect guide id
				{
					displayError("no such guide");
					showBillBtn.setVisible(false);
					currOrder = null;
					return;
				}
			}
		} else {
			if (!inputValidator.checkSubscription(id)) {
				displayError("incorrect subscription number format");
				showBillBtn.setVisible(false);
				currOrder = null;
				return;
			}
			Subscriber subscriber = IdentificationCC.checkSubscriptionNumber(id);
			if (subscriber == null) {
				displayError("no such subscriber");
				showBillBtn.setVisible(false);
				currOrder = null;
				return;
			}
			id = subscriber.getIdNumber();
			if (numOfVisitors > subscriber.getNumberOfFamilyMembers()) {
				errorMsgLabel.setVisible(true);
				errorMsgLabel.setText(
						"your subscription allows for up to " + subscriber.getNumberOfFamilyMembers() + " visitors");
				return;
			}

		}
		order = new Order(GoNatureClient.currentWorker.getPark(), Date.valueOf(LocalDate.now()),
				Time.valueOf(LocalTime.now()), numOfVisitors, null, id, orderType, 0, "confirmed", false, false);
		order.setPrice(PaymentController.calculateOrderPrice(order));
		showBillBtn.setVisible(true);
		currOrder = order;
		PriceLabel.setText(String.valueOf(order.getPrice()) + " ₪");
		errorMsgLabel.setVisible(false);

	}

	/**
	 * this method is launched when the user clicks on the open bill button the
	 * method loads the bill gui and displays it on new stage, showing the order
	 * bill.
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void openBill(ActionEvent event) {
		calculatePrice(null);
		billController bc = new billController();
		String bill;
		if (currOrder == null)
			return;
		bill = PaymentController.getbill(currOrder);
		if (bill != null)
			bc.start(new Stage(), Date.valueOf(LocalDate.now()).toString(), currOrder.getOrderer(), bill);
	}

	/**
	 * this method is launched when the user changes the visit type combo box the
	 * method changes idNumberLabel according to what was chosen in the visit type
	 * combo box
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void visitTypeChanged(ActionEvent event) {
		OrderType visitType = visitTypeComboBox.getSelectionModel().getSelectedItem();
		switch (visitType) {
		case UNPLANNEDGUIDE:
			idNumberLabel.setText("Guide Id : ");
			break;
		case UNPLANNEDSUBSCRIBER:
			idNumberLabel.setText("Subscription Number : ");
			break;
		case UNPLANNEDTRAVELER:
			idNumberLabel.setText("Traveler Id : ");
			break;
		default:
			break;
		}

	}

	
	
	
	/**
	 * initialize method for the gui to fill all the gui elements
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		AddOrderBtn.setCursor(Cursor.HAND);
		CalculatePriceBtn.setCursor(Cursor.HAND);
		showBillBtn.setCursor(Cursor.HAND);
		currOrder = null;
		PriceLabel.setText("");
		subscriptionNumberTextField.setText("");
		subscriptionNumberTextField.setPromptText("optional");
		idNumberLabel.setText("Guide Id : ");
		errorMsgLabel.setVisible(false);
		for (int i = 1; i <= 15; i++)
			numOfVisitorsComboBox.getItems().add(i);
		numOfVisitorsComboBox.getSelectionModel().select(0);
		visitTypeComboBox.getItems().add(OrderType.UNPLANNEDGUIDE);
		showBillBtn.setVisible(false);
		visitTypeComboBox.getItems().add(OrderType.UNPLANNEDSUBSCRIBER);
		visitTypeComboBox.getItems().add(OrderType.UNPLANNEDTRAVELER);
		visitTypeComboBox.getSelectionModel().select(0);
	}

	/**
	 * this method receives a string and displays it in the error msg label
	 * 
	 * @param msg-String to be displayed as error msg
	 */
	public void displayError(String msg) {
		errorMsgLabel.setText(msg);
		errorMsgLabel.setVisible(true);
		PriceLabel.setText("");
	}

}
