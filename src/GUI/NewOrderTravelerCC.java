package GUI;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.mysql.cj.result.LocalDateValueFactory;

import javafx.scene.control.DateCell;

import Client.GoNatureClient;
import controllers.OrderCC;
import controllers.PaymentController;
import controllers.SubscriberCC;
import controllers.WaitingListCC;
import controllers.inputValidator;
import entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * this class works when the visitor wants to order
 * 
 * @author Group2
 *
 */
public class NewOrderTravelerCC implements Initializable {

	private double price;
	public static Order newOrder = null;
	boolean checksForTexts = false;
	private ArrayList<Park> arrParks;
	@FXML
	private TextField telephone_order_text;
	@FXML
	private TextField email_order_text;
	@FXML
	private ComboBox<Integer> numOfVisitors_order_comboBox;
	@FXML
	private DatePicker date_order_datePicker;
	@FXML
	private ComboBox<String> park_order_comBox;
	@FXML
	private Label price_order_label;
	@FXML
	private Label orderType_order_label;
	@FXML
	private Button order_btn;
	@FXML
	private AnchorPane anPane_anchore;
	@FXML
	private ComboBox<String> minutes_order_comBox;
	@FXML
	private ComboBox<String> hours_order_comBox;
	@FXML
	private AnchorPane mainOrderPage_anchore;
	@FXML
	private Button enterWaitingList_btn;
	@FXML
	private Button getAnotherDate_btn;
	@FXML
	private Label successful_label;
	@FXML
	private Button okGreat_btn;
	@FXML
	private Label error_existOrder_label;
	@FXML
	private Label error_message_label;
	@FXML
	private CheckBox payNow_checkBox;

	@FXML
	private Label ccn_pay_label;

	@FXML
	private TextField ccn_pay_text;

	@FXML
	private Label ed_pay_label;

	@FXML
	private Label cvv_Label;

	@FXML
	private TextField ed_pay_text;

	@FXML
	private TextField cvv_pay_text;

	@FXML
	private ImageView visaImage;

	@FXML
	private ImageView cvv_pay_star;

	@FXML
	private ImageView ed_pay_star;

	@FXML
	private ImageView ccn_pay_star;
	@FXML
	private Label date_label;
	@FXML
	private ImageView date_img;

	@FXML
	private Label error_msg_date_label;

	@FXML
	private Button priceShow;

	static VisitorHomePageCC cc;

	/**
	 * this method checks if the check box is selected,if yes all the relevant
	 * labels will be visible
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void actionComboBox(ActionEvent event) {
		if (payNow_checkBox.isSelected()) {

			ccn_pay_label.setVisible(true);
			ccn_pay_text.setVisible(true);
			ccn_pay_star.setVisible(true);

			ed_pay_label.setVisible(true);
			ed_pay_text.setVisible(true);
			ed_pay_star.setVisible(true);

			cvv_Label.setVisible(true);
			cvv_pay_text.setVisible(true);
			cvv_pay_star.setVisible(true);

			visaImage.setVisible(true);
			date_label.setVisible(false);
			date_img.setVisible(false);

		} else {

			ccn_pay_label.setVisible(false);
			ccn_pay_text.setVisible(false);
			ccn_pay_star.setVisible(false);

			ed_pay_label.setVisible(false);
			ed_pay_text.setVisible(false);
			ed_pay_star.setVisible(false);

			cvv_Label.setVisible(false);
			cvv_pay_text.setVisible(false);
			cvv_pay_star.setVisible(false);

			visaImage.setVisible(false);
			date_label.setVisible(true);
			date_img.setVisible(true);

		}

	}

	/**
	 * this method is launched when the user presses GetAnotherDate button this
	 * method opens AvailibilityTable page while also closing the current page
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void actionGetAnotherDateBtn(ActionEvent event) {
		try {
			choosingAnotherOrderTimeCC.cc = cc;
			anPane_anchore.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("AvailibilityTableGUI.fxml")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error ::: actionGetAnotherDateBtn");
		}
	}

	/**
	 * this method is launched when the user presses EnterWaitingList button this
	 * method put a pop up on the screen and updates the waiting list
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void actionEnterWaitingList(ActionEvent event) {
		try {
			if (WaitingListCC.enterWaitingList(newOrder)) {
				VisitorHomePageCC a = new VisitorHomePageCC();
				a.start(new Stage());
				((Node) event.getSource()).getScene().getWindow().hide();
				popUpCC aa = new popUpCC();
				aa.start(new Stage(), true, "Yor Are Now In Waiting List", "We Will Keep In Touch", "Ok , Great!");
			} else {
				popUpCC aa = new popUpCC();
				aa.start(new Stage(), false, "Oops The Waiting List Is Full", "You Can Choose Another Date", "Ok !");
			}
		} catch (Exception e) {
			System.out.println("WaitingListCC.enterWaitingList(newOrder)");
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * this method is launched when the user pressesOrder button this method checks
	 * if the order is good to go,if yes the method updates the orders table
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void actionOrderBtn(ActionEvent event) {
		if (checksForTexts == true && newOrder != null) {
			ArrayList<Order> ordersForTraveler = new ArrayList<Order>();
			ordersForTraveler = OrderCC.getAllOrderForTraveler(newOrder.getOrderer());
			int lenOrders = ordersForTraveler.size();
			int lenOrdersThatNeedConfirmn = 0;
			boolean existOrder = false;
			for (int i = 0; i < ordersForTraveler.size(); i++) {
				if (ordersForTraveler.get(i).getVisitTime().compareTo(newOrder.getVisitTime()) == 0
						& !ordersForTraveler.get(i).getOrderStatus().equals("unconfirmed")) {
					if (ordersForTraveler.get(i).getVisitDate().compareTo(newOrder.getVisitDate()) == 0) {
						if (ordersForTraveler.get(i).getPark().getName().equals(newOrder.getPark().getName())) {
							existOrder = true;
						}
					}
				}
			}
			if (!existOrder) {
				Date visitDate = newOrder.getVisitDate();
				Time visitTime = newOrder.getVisitTime();
				if (Math.abs(ChronoUnit.DAYS.between(LocalDate.now(),
						LocalDate.of(visitDate.getYear() + 1900, visitDate.getMonth() + 1, visitDate.getDate()))) == 1
						&& visitTime.compareTo(Time.valueOf(LocalTime.now())) <= 0)
					newOrder.setOrderStatus("confirmed");
				if (Math.abs(ChronoUnit.DAYS.between(LocalDate.now(),
						LocalDate.of(visitDate.getYear() + 1900, visitDate.getMonth() + 1, visitDate.getDate()))) == 0
						&& visitTime.compareTo(Time.valueOf(LocalTime.now())) >= 0)
					newOrder.setOrderStatus("confirmed");
				if (Math.abs(ChronoUnit.DAYS.between(LocalDate.now(),
						LocalDate.of(visitDate.getYear() + 1900, visitDate.getMonth() + 1, visitDate.getDate()))) == 0
						&& visitTime.compareTo(Time.valueOf(LocalTime.now())) <= 0) {
					newOrder = null;
					error_msg_date_label.setVisible(true);
					return;
				}
				error_msg_date_label.setVisible(false);
				if (OrderCC.addOrder(newOrder)) {
					// should to return for home page
					error_existOrder_label.setVisible(false);
					error_message_label.setVisible(false);
					popUpCC aa = new popUpCC();
					aa.start(new Stage(), true, "We are Waitting For You", "Your Order has been placed successfully!",
							"Ok , Great!");
					cc.openVisitorHomePage(null);
				} else {
					try {
						error_message_label.setVisible(false);
						mainOrderPage_anchore.getChildren().clear();
						mainOrderPage_anchore.getChildren()
								.setAll((Node) FXMLLoader.load(getClass().getResource("unconfirmedGUI.fxml")));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} else {
				error_existOrder_label.setVisible(true);
			}
		} else {
			error_message_label.setVisible(true);
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@FXML
	void billShow(MouseEvent event) {
		calculateThePrice(null);
		if (newOrder != null && checksForTexts) {
			new billController().start(new Stage(), Date.valueOf(LocalDate.now()).toString(), newOrder.getOrderer(),
					PaymentController.getbill(newOrder));
			error_message_label.setText("");

		}

	}

	@FXML
	void MouseExit(MouseEvent event) {
		if (event.getSource() instanceof Button)
			((Button) event.getSource()).setEffect(null);

	}

	/**
	 * this method runs at the beginning this method initialize the labels on the
	 * screen
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// TODO Auto-generated method stub
		try {
			date_order_datePicker.setDayCellFactory(picker -> new DateCell() {

				public void updateItem(LocalDate date, boolean empty) {
					super.updateItem(date, empty);
					LocalDate today = LocalDate.now();
					setDisable(empty || date.compareTo(today) < 0);
				}
			});
			order_btn.setCursor(Cursor.HAND);
			priceShow.setCursor(Cursor.HAND);
			arrParks = new ArrayList<Park>();
			arrParks = OrderCC.getAllParksforClient();
			for (Park e1 : arrParks) {
				park_order_comBox.getItems().addAll((String) e1.getName());
			}
		} catch (Exception e) {// e.printStackTrace();
		}
		try {
			if (VisitorHomePageCC.subscriber_private != null) {

				if (VisitorHomePageCC.subscriber_private.getNumberOfFamilyMembers() > 1) {
					orderType_order_label.setText("Family visitor");
				} else {
					orderType_order_label.setText("Subscriber visitor");
				}
				for (int i = 1; i <= VisitorHomePageCC.subscriber_private.getNumberOfFamilyMembers(); i++) {
					numOfVisitors_order_comboBox.getItems().addAll((Integer) i);
				}
				telephone_order_text.setText(VisitorHomePageCC.subscriber_private.getPhoneNumber());
				email_order_text.setText(VisitorHomePageCC.subscriber_private.getEmail());
			} else {
				if (GoNatureClient.currentTraveler == null) {
					numOfVisitors_order_comboBox.getItems().addAll((Integer) 1);
				} else if (GoNatureClient.currentTraveler.isGuide() == true) {
					orderType_order_label.setText("Guide visitor");
					for (int i = 1; i <= 15; i++) {
						numOfVisitors_order_comboBox.getItems().addAll((Integer) i);
					}
				} else {
					orderType_order_label.setText("Ordinary visitor");

					numOfVisitors_order_comboBox.getItems().addAll((Integer) 1);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			for (Integer i = 8; i <= 18; i++) {
				hours_order_comBox.getItems().addAll(i.toString());
			}
			for (Integer i = 0; i <= 59; i++) {
				minutes_order_comBox.getItems().addAll(i.toString());
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

	}

	/**
	 * this method is launched when the user finishes putting all his details in the
	 * order
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void mouseEventCalcPrice(MouseEvent event) {
		calculateThePrice(null);
		if (event.getSource() instanceof Button)
			((Button) event.getSource()).setEffect(new DropShadow());
	}

	/**
	 * this method is calculates the price of the order this method updates the
	 * price label depinding on the order details
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void calculateThePrice(ActionEvent e) {
		try {
			try {
				Park parkOrder = null;
				checksForTexts = false;
				for (Park e1 : arrParks) {
					if (e1.getName().equals(park_order_comBox.getValue())) {
						parkOrder = e1;
					}
				}
				OrderType orderType;
				if (orderType_order_label.getText().equals("Ordinary visitor")) {
					orderType = OrderType.PLANNEDTRAVELER;
				} else if (orderType_order_label.getText().equals("Guide visitor")) {
					orderType = OrderType.PLANNEDGUIDE;
				} else {
					orderType = OrderType.PLANNEDSUBSCRIBER;
				}

				String id = VisitorHomePageCC.idNumber_static;
				String telephoneNumber = telephone_order_text.getText();
				String email = email_order_text.getText();

				if (numOfVisitors_order_comboBox.getValue() != null) {
					int numberOfVisitors = numOfVisitors_order_comboBox.getValue();
					if (date_order_datePicker.getValue() == null) {
						error_message_label.setText("No date selected !");
						error_message_label.setVisible(true);
						return;
					}
					@SuppressWarnings("deprecation")
					Date date = new Date(date_order_datePicker.getValue().getYear() - 1900,
							date_order_datePicker.getValue().getMonthValue() - 1,
							date_order_datePicker.getValue().getDayOfMonth());
					if (date.compareTo(Date.valueOf(LocalDate.now())) >= 0) {
						if (park_order_comBox.getValue() != null) {
							if (hours_order_comBox.getValue() != null) {
								int hours = Integer.parseInt(hours_order_comBox.getValue());
								if (checktelephone(telephoneNumber)) {
									if (minutes_order_comBox.getValue() != null) {
										int minutes = Integer.parseInt(minutes_order_comBox.getValue());
										if (isValid(email)) {

											Time time = new Time(hours, minutes, 0);
											newOrder = new Order(parkOrder, date, time, numberOfVisitors, id, null,
													orderType, 0.0, null, false);
											price = PaymentController.calculateOrderPrice(newOrder);

											price_order_label.setText(String.valueOf(price) + " nis");
											if (payNow_checkBox.isSelected() && checkVisaTexts()) {
												newOrder = new Order(parkOrder, date, time, numberOfVisitors, email, id,
														orderType, price, "waiting", true, false, telephoneNumber);

												price = PaymentController.calculateOrderPrice(newOrder);
												newOrder = new Order(parkOrder, date, time, numberOfVisitors, email, id,
														orderType, price, "waiting", true, false, telephoneNumber);
												checksForTexts = true;
											} else {
												if (payNow_checkBox.isSelected()) {
													error_message_label.setText("enter valid card details");
													error_message_label.setVisible(true);
													newOrder = new Order(parkOrder, date, time, numberOfVisitors, email,
															id, orderType, price, "waiting", false, false,
															telephoneNumber);
													checksForTexts = false;

												} else {
													newOrder = new Order(parkOrder, date, time, numberOfVisitors, email,
															id, orderType, price, "waiting", false, false,
															telephoneNumber);
													checksForTexts = true;
												}

											}

										} else {

											error_message_label.setText(
													checkInputs() ? "Wrong email format!" : "All Fields required");
											error_message_label.setVisible(true);
										}
									} else {
										error_message_label.setText(
												checkInputs() ? "You have to select minutes!" : "All Fields required");
										error_message_label.setVisible(true);
									}
								} else {
									error_message_label.setText(
											checkInputs() ? "Phone number is incorrect!" : "All Fields required");
									error_message_label.setVisible(true);
								}
							} else {
								error_message_label
										.setText(checkInputs() ? "You have to select hour!" : "All Fields required");
								error_message_label.setVisible(true);
							}
						} else {
							error_message_label.setText(checkInputs() ? "No Park selected!" : "All Fields required");
							error_message_label.setVisible(true);
						}
					} else {
						error_message_label.setText(checkInputs() ? "Incorrect date" : "All Fields required");
						error_message_label.setVisible(true);
					}
				} else {
					error_message_label
							.setText(checkInputs() ? "Incorrect number of visitors!" : "All Fields required");
					error_message_label.setVisible(true);
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (IllegalArgumentException e4) {
			e4.printStackTrace();
		}
	}
	/*
	 * Ordinary visitor , Family visitor , Guide visitor , Subscriber visitor
	 * PLANNEDTRAVELER , PLANNEDSUBSCRIBER , PLANNEDGUIDE ,
	 */

	/**
	 * this method checks if the telephone is correct,if yes the method returns
	 * true,otherwise returns false
	 * 
	 * @param id
	 * @return boolean
	 */
	private boolean checktelephone(String id) {
		if (id.length() > 8 && id.length() < 12) {// checking length of id number,should be 9
			for (int i = 0; i < id.length(); i++)
				if (!Character.isDigit(id.charAt(i)))// if one character is not a digit then id isn't valid return false
					return false;
			return true;
		}
		return false;
	}

	/**
	 * this method checks if the email is correct,if yes the method returns
	 * true,otherwise returns false
	 * 
	 * @param email
	 * @return boolean
	 */
	private boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	/**
	 * this method checks if the Visa is correct,if yes the method returns
	 * true,otherwise returns false
	 * 
	 * @return boolean
	 */
	private boolean checkVisaTexts() {

		String ccn = ccn_pay_text.getText();
		String ed = ed_pay_text.getText();
		String cvv = cvv_pay_text.getText();

		if (cvv.length() == 3) {// checking length of id number,should be 3
			for (int i = 0; i < cvv.length(); i++)
				if (!Character.isDigit(cvv.charAt(i)))// if one character is not a digit then id isn't valid return
														// false
				{

					return false;
				}
		} else {
			return false;
		}

		if (ed.length() > 3 && ed.length() < 7) {// checking length of id number,should be 5 mixmum
			for (int i = 0; i < ed.length(); i++)
				if (!Character.isDigit(ed.charAt(i)) && ed.charAt(i) != '/')// if one character is not a digit then id
																			// isn't valid return false
				{
					return false;
				}
		} else {
			return false;
		}

		if (ccn.length() > 8 && ccn.length() < 20) {// checking length of id number,should be 9
			for (int i = 0; i < ccn.length(); i++)
				if (!Character.isDigit(ccn.charAt(i)))// if one character is not a digit then id isn't valid return
														// false
				{
					return false;
				}
		} else {
			return false;
		}

		return true;

	}

	public boolean checkInputs() {

		if (park_order_comBox.getValue() != null && date_order_datePicker.getValue() != null
				&& hours_order_comBox.getValue() != null && minutes_order_comBox.getValue() != null
				&& numOfVisitors_order_comboBox.getValue() != null && !telephone_order_text.getText().equals("")
				&& !email_order_text.getText().equals("")) {

			return true;

		}
		return false;
	}

}
