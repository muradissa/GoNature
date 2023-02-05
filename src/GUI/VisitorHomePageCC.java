package GUI;

import java.awt.Window;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import Client.ClientUI;
import controllers.OrderCC;
import controllers.SubscriberCC;
import controllers.WaitingListCC;
import entities.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import GUI.VisitorHomePageCC.*;

/**
 * this class is for VisitorHomePageGui
 * 
 * @author MICHEL
 *
 */

public class VisitorHomePageCC implements Initializable {

	private Order selectedOrder;
	public static String idNumber_static;
	public static String subscriptionNumber_static;
	private ArrayList<Order> arrOrders = new ArrayList<Order>();
	public static Subscriber subscriber_private;
	@FXML
	private ImageView ICON1;
	@FXML
	private ImageView ICON4;
	@FXML
	private Label title_homepage_label;
	@FXML
	private Label hello_label;
	@FXML
	private Button logout_btn;
	@FXML
	private AnchorPane anPane;
	@FXML
	private AnchorPane WelcomTo;
	@FXML
	private Label yellow_box_label;
	@FXML
	private Label green_box_label;
	@FXML
	private Button profile_confirm_btn;
	@FXML
	private Button profile_cancel_btn;
	@FXML
	private Label profile_id_label;
	@FXML
	private Label profile_fullname_label;
	@FXML
	private Label profile_email_label;
	@FXML
	private TableView<Order> visitor_table_view;
	@FXML
	private TableColumn<Order, String> parkname_tableColumn;
	@FXML
	private TableColumn<Order, Date> dateOrder_tableColumn;
	@FXML
	private TableColumn<Order, Time> time_tableColumn;
	@FXML
	private TableColumn<Order, Integer> numOfVisitors_tableColumn;
	@FXML
	private TableColumn<Order, Double> priceOrder_tableColumn;
	@FXML
	private TableColumn<Order, String> statusOrder_tableColumn;
	@FXML
	private Label label_for_tableview;

	private ImageView selected;

	public void selectedImage(ImageView newImage) {
		Pane p1;
		if (selected != null) {
			p1 = (Pane) selected.getParent();
			p1.setStyle(null);
		}
		Pane p = (Pane) newImage.getParent();

		p.setStyle("-fx-background-color: #e7d8c0");

		selected = newImage;
	}

	/**
	 * this method lunches VisitorHomePageGUI
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/VisitorHomePageBase.fxml"));

			Parent root = loader.load();
			NewOrderTravelerCC.cc = loader.getController();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Traveler Home Page");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user press logout button this method opens
	 * home page of GoNature while also closing the current page
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void logoutBtn(ActionEvent event) {
		subscriber_private = null;
		subscriptionNumber_static = null;
		idNumber_static = null;
		ClientUI clientUI = new ClientUI();
		((Node) event.getSource()).getScene().getWindow().hide();
		clientUI.chat.quit();
		clientUI.start(new Stage());
	}

	/**
	 * this method is launched when the user press HomePage button this method opens
	 * VisitorHomePage while also closing the current page
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void openVisitorHomePage(MouseEvent event) {
		try {
			anPane.getChildren().clear();
			anPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("VisitorHomePage.fxml")));
			title_homepage_label.setText("Traveler Home Page");
			selectedImage(ICON1);
		} catch (Exception e) {
//			
			e.printStackTrace();

		}
	}

	/**
	 * this method is launched when the user press order button this method opens
	 * OrderGUI while also closing the current page
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void eventOrerIcon(MouseEvent event) {
		try {
			anPane.getChildren().clear();
			anPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("OrderGUI.fxml")));
			title_homepage_label.setText("Order Page");
			selectedImage(ICON4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method runs at the beginning this method initialize the labels on the
	 * screen and the tableView of the orders
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			if (subscriptionNumber_static != null) {
				subscriber_private = SubscriberCC.getSubsscriber(subscriptionNumber_static);
				idNumber_static = subscriber_private.getIdNumber();
			}
		} catch (Exception e) {
		}
		try {

			if (idNumber_static != null) {
				subscriber_private = SubscriberCC.getSubsscriber(idNumber_static);
			}
		} catch (Exception e) {
		}
		try {
			if (subscriber_private != null) {
				profile_email_label.setText(subscriber_private.getEmail());
				profile_id_label.setText(subscriber_private.getIdNumber());
				profile_fullname_label
						.setText(subscriber_private.getFirstName() + " " + subscriber_private.getLastName());
				hello_label.setText("Hello, " + subscriber_private.getFirstName());
			} else {
				profile_id_label.setText(VisitorHomePageCC.idNumber_static);
				hello_label.setText("Hello");
			}
		} catch (Exception e) {
		}
		try {
			ArrayList<Order> ordersForTravelerInWaitingList = new ArrayList<Order>();
			ordersForTravelerInWaitingList = WaitingListCC.getAllOrdersInWaitingListForTraveler(idNumber_static);
			for (int i = 0; i < ordersForTravelerInWaitingList.size(); i++) {
				ordersForTravelerInWaitingList.get(i).setOrderStatus("waiting-list");
			}

		} catch (Exception e) {

		}

		int lenOrders;
		int lenOrdersThatNeedConfirmn = 0;
		ArrayList<Order> ordersForTravelerInWaitingList = new ArrayList<Order>();
		ArrayList<Order> ordersForTraveler = new ArrayList<Order>();
		String youHaveNumOrders;

		try {
			ordersForTraveler = OrderCC.getAllOrderForTraveler(idNumber_static);
			lenOrders = 0;
			for (Order o : ordersForTraveler)
				if (!o.getOrderStatus().equals("unconfirmed"))
					lenOrders++;
			lenOrdersThatNeedConfirmn = 0;
			youHaveNumOrders = "You Have\n" + lenOrders + "\nOrders";
			yellow_box_label.setText(youHaveNumOrders);

			for (int i = 0; i < ordersForTraveler.size(); i++) {

				if (ordersForTraveler.get(i).getVisitDate().compareTo(Date.valueOf(LocalDate.now())) == 0
						&& ordersForTraveler.get(i).getVisitTime().compareTo(Time.valueOf(LocalTime.now())) < 0) {
				} else {
					arrOrders.add(ordersForTraveler.get(i));
					if (ordersForTraveler.get(i).getOrderStatus().equals("'unconfirmed'")) {
						// lenOrders--;
					}
					ordersForTraveler.get(i).setPhoneNumber(ordersForTraveler.get(i).getPark().getName());
				}
			}

			ordersForTravelerInWaitingList = WaitingListCC.getAllOrdersInWaitingListForTraveler(idNumber_static);
			if (ordersForTravelerInWaitingList.size() != 0)
				for (int i = 0; i < ordersForTravelerInWaitingList.size(); i++) {
					lenOrders++;
					arrOrders.add(ordersForTravelerInWaitingList.get(i));
					System.out.println(ordersForTravelerInWaitingList.get(i).getOrderer() + "  "
							+ ordersForTravelerInWaitingList.get(i).getPark().getName());
					ordersForTravelerInWaitingList.get(i).setOrderStatus("waiting-list");
					ordersForTravelerInWaitingList.get(i)
							.setPhoneNumber(ordersForTravelerInWaitingList.get(i).getPark().getName());
				}

			ArrayList<Order> ordersToConfirmOrCancel = getTheOrdersToConfirmOrCancel(ordersForTraveler,
					ordersForTravelerInWaitingList);

			lenOrdersThatNeedConfirmn = ordersToConfirmOrCancel.size();
			green_box_label.setText("You Have\n" + ordersToConfirmOrCancel.size() + "  Orders\n To Confirm it");
			profile_cancel_btn.setLayoutX(510);
			try {
				profile_confirm_btn.setVisible(false);
				if (ordersToConfirmOrCancel.size() > 0) {
					profile_confirm_btn.setVisible(true);
					label_for_tableview.setText("Please Confirm or Cancel Your Orders:");
					profile_cancel_btn.setLayoutX(510);

					dateOrder_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, Date>("visitDate"));
					time_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, Time>("visitTime"));
					parkname_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("phoneNumber"));
					numOfVisitors_tableColumn
							.setCellValueFactory(new PropertyValueFactory<Order, Integer>("numOfVisitors"));
					priceOrder_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
					statusOrder_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderStatus"));
					visitor_table_view.setItems(FXCollections.observableArrayList(ordersToConfirmOrCancel));
					visitor_table_view.refresh();
				} else if (lenOrders != 0) {
					/*
					 * change the green box change the label make confirm button is UNvisiable
					 */
					profile_confirm_btn.setVisible(false);
					label_for_tableview.setText("Your upcoming orders:");
					profile_cancel_btn.setLayoutX(410);
					dateOrder_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, Date>("visitDate"));
					time_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, Time>("visitTime"));
					parkname_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("phoneNumber"));
					numOfVisitors_tableColumn
							.setCellValueFactory(new PropertyValueFactory<Order, Integer>("numOfVisitors"));
					priceOrder_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
					statusOrder_tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderStatus"));
					visitor_table_view.setItems(FXCollections.observableArrayList(arrOrders));
					visitor_table_view.refresh();
				}
				selectedImage(ICON1);
			} catch (Exception exce) {
			}
		} catch (Exception exe) {
			exe.printStackTrace();
		}

	}

	/**
	 * this method is launched when the user press confirm button this method
	 * confirms the order
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void actionConfirmBtn(ActionEvent event) {
		if (visitor_table_view.getSelectionModel().getSelectedItem() != null) {
			selectedOrder = visitor_table_view.getSelectionModel().getSelectedItem();
			if (selectedOrder.getOrderStatus().equals("waiting")) {
				OrderCC.confirmOrder(getOrderFromarrOrders(selectedOrder));
			} else if (selectedOrder.getOrderStatus().equals("waiting-list")) {
				selectedOrder = getOrderFromarrOrders(selectedOrder);
				System.out.println(selectedOrder + " " + selectedOrder.getPhoneNumber());
				selectedOrder.setOrderStatus("waiting");
				WaitingListCC.confirmOrderFromWaitingList(selectedOrder);
			} else
				System.out.println("Wrong Wrong");

		}
		WelcomTo.getChildren().clear();
		try {
			WelcomTo.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("VisitorHomePage.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * this method is launched when the user press cancel button this method cancels
	 * the order
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void actionCancelBtn(ActionEvent event) {

		if (visitor_table_view.getSelectionModel().getSelectedItem() != null) {
			selectedOrder = visitor_table_view.getSelectionModel().getSelectedItem();
			if (selectedOrder.getOrderStatus().equals("waiting")) {
				OrderCC.cancelOrder(getOrderFromarrOrders(selectedOrder));
			} else if (selectedOrder.getOrderStatus().equals("waiting-list")) {
				WaitingListCC.cancelOrderFromWaitingList(getOrderFromarrOrders(selectedOrder));
			} else
				System.out.println("Wrong Wrong");

			/* should to go to the waitinglist and see if another traveler waiting */
		}

		try {
			WelcomTo.getChildren().clear();
			WelcomTo.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("VisitorHomePage.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * this method organize the tableView of the orders and the status of the order
	 * 
	 * @param ordersForTraveler
	 * @param ordersForTravelerInWaitingList
	 * @return ArrayList of Order
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<Order> getTheOrdersToConfirmOrCancel(ArrayList<Order> ordersForTraveler,
			ArrayList<Order> ordersForTravelerInWaitingList) {

		ArrayList<Order> ordersToConfirmOrCancel = new ArrayList<Order>();

		Date dateNow = Date.valueOf(LocalDate.now());
		Time timeNow = Time.valueOf(LocalTime.now());

		for (int i = 0; i < ordersForTraveler.size(); i++) {

			Date dateOrder = ordersForTraveler.get(i).getVisitDate();
			Time timeOrder = ordersForTraveler.get(i).getVisitTime();
			dateOrder.setDate(dateOrder.getDate() - 1);
			if (dateOrder.equals(dateNow) && timeOrder.compareTo(timeNow) <= 0
					&& ordersForTraveler.get(i).getOrderStatus().equals("waiting")) {
				timeOrder.setHours(timeOrder.getHours() + 2);
				if (timeOrder.compareTo(timeNow) >= 0) {
					dateOrder.setDate(dateOrder.getDate() + 1);
					timeOrder.setHours(timeOrder.getHours() - 2);
					ordersToConfirmOrCancel.add(ordersForTraveler.get(i));
				} else {
					timeOrder.setHours(timeOrder.getHours() - 2);
				}
			} else {
				dateOrder.setDate(dateOrder.getDate() + 1);
			}
		}
		if (ordersForTravelerInWaitingList.size() != 0)
			for (int j = 0; j < ordersForTravelerInWaitingList.size(); j++) {
				for (int i = 0; i < ordersForTraveler.size(); i++) {
					if (ordersForTravelerInWaitingList.get(j).getOrderer()
							.equals(ordersForTraveler.get(i).getOrderer())) {
						if (ordersForTravelerInWaitingList.get(j).getVisitTime()
								.equals(ordersForTraveler.get(i).getVisitTime())) {
							if (ordersForTravelerInWaitingList.get(j).getVisitDate()
									.equals(ordersForTraveler.get(i).getVisitDate())) {
								if (ordersForTravelerInWaitingList.get(j).getPark().getName()
										.equals(ordersForTraveler.get(i).getPark().getName())) {
									if (ordersForTravelerInWaitingList.get(j).getOrderStatus().equals("waiting-list")) {
										if (ordersForTraveler.get(i).getOrderStatus().equals("waiting")) {
											System.out.println(ordersForTravelerInWaitingList.get(j).getPark());
											System.out.println("okokokokokok");
											ordersToConfirmOrCancel.add(ordersForTravelerInWaitingList.get(j));
										}
									}

								}
							}
						}
					}

				}

//				if (OrderCC.checkOrderAvailibilty(ordersForTravelerInWaitingList.get(i).getVisitTime(),ordersForTravelerInWaitingList.get(i).getVisitDate(), ordersForTravelerInWaitingList.get(i).getNumOfVisitors(), ordersForTravelerInWaitingList.get(i).getPark().getName())) {
//					
//				}
			}
		return ordersToConfirmOrCancel;
	}

	@FXML
	void mouseEntered(MouseEvent event) {
		logout_btn.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void mouseExit(MouseEvent event) {
		logout_btn.getScene().setCursor(Cursor.DEFAULT);
	}

	/**
	 * this method checks if there is a relevant order and returns it
	 * 
	 * @param order
	 * @return Order
	 */
	public Order getOrderFromarrOrders(Order order) {

		for (int i = 0; i < arrOrders.size(); i++) {
			if (arrOrders.get(i).getOrderer().equals(order.getOrderer())) {
				if (arrOrders.get(i).getVisitTime().equals(order.getVisitTime())) {
					if (arrOrders.get(i).getVisitDate().equals(order.getVisitDate())) {
						if (arrOrders.get(i).getPark().getName().equals(order.getPark().getName())) {
							return arrOrders.get(i);
						}
					}
				}
			}
		}
		return null;
	}

}
