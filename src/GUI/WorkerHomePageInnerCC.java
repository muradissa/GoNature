package GUI;

import java.awt.CardLayout;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;

import controllers.*;
import entities.Order;
import entities.Park;
import entities.Subscriber;

import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.javafx.webkit.ThemeClientImpl;

import Client.GoNatureClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * controller class for inner workerHomePage GUI
 * @author ayman
 *
 */
public class WorkerHomePageInnerCC implements Initializable {


    @FXML
    private Button CheckVacancyBtn;

    @FXML
    private Label numOfVisitorsInParkLabel;

    @FXML
    private Label numOfAvailablePlacesLabel;

    @FXML
    private Button CheckOrderBtn;

    @FXML
    private TextField CheckOrderIDTextField;

    @FXML
    private Label SelectedOrderNameLabel;

    @FXML
    private Label SelectedOrderDateLabel;

    @FXML
    private Label SelectedOrderTimeLabel;

    @FXML
    private Label SelectedOrderNumOfVisitorsLabel;

    @FXML
    private Label SelectedOrderTypeLabel;

    @FXML
    private Label SelectedOrderPriceLabel;

    @FXML
    private Label SelectedOrderPayStatusLabel;

    @FXML
    private Circle checkOrderCircle;

    @FXML
    private Button ConfirmOrderBtn;

    @FXML
    private Button ReadIdBtn;
    
    @FXML
    private Label orderIdStatusLabel;
    
    @FXML
    private Label cardReaderMessageLabel;
    
    @FXML
    private Label numOfAvailableUnplannedLabel;
    
    @FXML
    private Button EntranceBtn;

    @FXML
    private Button ExitBtn;
    
    private Order currOrder=null;
    
    /**
     * this method is launched when the user clicks on check order button
     * the method reads the input of order id,then searches for the order in the database
     * if the order exists,the method fills in the order info in relevant fields in the gui
     * if the order doesn't exist,the method displays error msg in the gui as label
     * 
     * @param event-javaFx ActionEvent,the event that started this method
     */
    @FXML
    void checkOrder(ActionEvent event) {
    	String id=CheckOrderIDTextField.getText();
    	if(inputValidator.checkId(id)||inputValidator.checkSubscription(id))
    	{
    	Order order=ParkEntranceAndExitCC.identify(id, GoNatureClient.currentWorker.getPark().getName());
    	
    	if(order!=null) {
    		currOrder=order;
    		fillOrderDetails(order);
			checkOrderCircle.setFill(Paint.valueOf("Green"));
    		orderIdStatusLabel.setVisible(false);
    		ConfirmOrderBtn.setVisible(true);
    		if(order.isArrived())
    		{
    			if(order.getExit()!=null)
    			{
    				orderIdStatusLabel.setText("order id doesn't exist");
        			checkOrderCircle.setFill(Paint.valueOf("RED"));
            		orderIdStatusLabel.setVisible(true);
            		currOrder=null;
            		ConfirmOrderBtn.setVisible(false);
            		clearOrderDetails();
            		return;
    			}
    			ConfirmOrderBtn.setText("Confirm Exit");
    		}
    		else {
    			
    			ConfirmOrderBtn.setText("Confirm Entrance");
			}
    		
		}
    		else {
    			orderIdStatusLabel.setText("order id doesn't exist");
    			checkOrderCircle.setFill(Paint.valueOf("RED"));
        		orderIdStatusLabel.setVisible(true);
        		currOrder=null;
        		ConfirmOrderBtn.setVisible(false);
        		clearOrderDetails();
    		}
    	}
    	else
    	{
    		checkOrderCircle.setFill(Paint.valueOf("RED"));
    		orderIdStatusLabel.setVisible(true);
    		orderIdStatusLabel.setText("id format is incorrect!");
    		currOrder=null;
    		ConfirmOrderBtn.setVisible(false);
    		clearOrderDetails();
    	}
    }

    /**
     * this method is launched when the user clicks on check Vacancy button
     * the method updates the fields related the current park capacity information
     * @param event-javaFx ActionEvent,the event that started this method
     */
    @FXML
    void checkVacancy(ActionEvent event) {
    	Park park=WorkerCC.checkParkVacancy(GoNatureClient.currentWorker.getPark().getName());
    	numOfVisitorsInParkLabel.setText(String.valueOf(park.getCurrentNumOfVisitors()));
    	numOfAvailablePlacesLabel.setText(String.valueOf(park.getMaxNumOfVisitor()-park.getCurrentNumOfVisitors()));
    	numOfAvailableUnplannedLabel.setText(String.valueOf(park.getMaxNumOfVisitor()-park.getMaxNumOfOrders()-park.getCurrentNumOfUnplannedVisitors()));
    }

    /**
     * this method is launched when the user clicks on confirm Entrance/confirm exit button
     * the method updates in the database , the entrance time/exit time of the selected order
     * according to the status of the order(entered/not entered) the park
     * the method also displays the bill in the case of an order that hasn't payed yet
     * @param event-javaFx ActionEvent,the event that started this method
     */
    @FXML
    void confirmOrder(ActionEvent event) {
    	
    	String toDo=ConfirmOrderBtn.getText();
    	if(toDo.equals("Confirm Entrance")&&currOrder!=null)
    	{
    		if(currOrder.getPayStatus()==false)
    		{
    		billController bc=new billController();
        	String bill=PaymentController.getbill(currOrder);
        	if(bill!=null)
        		bc.start(new Stage(), Date.valueOf(LocalDate.now()).toString(), currOrder.getOrderer(),bill);
    		}
    		ParkEntranceAndExitCC.identifyAtEntrance(currOrder.getOrderer(),currOrder.getPark().getName());
    		checkVacancy(null);
    		ConfirmOrderBtn.setText("Confirm Exit");
    	}
    	else if (toDo.equals("Confirm Exit")&&currOrder!=null) {
    		ParkEntranceAndExitCC.identifyAtExit(currOrder.getOrderer(),currOrder.getPark().getName());
    		clearOrderDetails();
    		checkVacancy(null);
    		currOrder=null;
    		ConfirmOrderBtn.setVisible(false);
    		CheckOrderIDTextField.setText("");
    		checkOrderCircle.setFill(Paint.valueOf("RED"));
		}
    	
    }
    
    /**
     * this method is launched when the user clicks on read id from scanner.
     * the method simulates reading an id number from the card reader
     * if the entrance button in the gui is selected,the method will read random order that hasn't arrived
     * but is scheduled to arrive in the near time,
     * if the exit button in the gui is selected,the method will read random order that has arrived
     *  but hasn't exited the park yet,
     *  if there are no orders matching the criteria,the method will display error msg as label in gui
     * @param event-javaFx ActionEvent,the event that started this method
     */
    @FXML
    void readIdFromCardScanner(ActionEvent event) {
    	boolean entrance=EntranceBtn.getStyle().equals("-fx-background-color:  #11ff00;");
    	Order orderByScanner=WorkerCC.cardReaderSimulator(GoNatureClient.currentWorker.getPark().getName(),entrance);
    	if(orderByScanner!=null&&orderByScanner.getExit()==null)
    	{
    		CheckOrderIDTextField.setText(orderByScanner.getOrderer());
    		cardReaderMessageLabel.setVisible(false);
    	}
    	else {
    		cardReaderMessageLabel.setVisible(true);
		}
    }

    /**
	 * initialize method for the gui to fill all the gui elements
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkOrderCircle.setFill(Paint.valueOf("RED"));
		orderIdStatusLabel.setVisible(false);
		cardReaderMessageLabel.setVisible(false);
		ConfirmOrderBtn.setVisible(false);
		entranceBtnClick(null);
	}
	
	/**
	 * this method is launched when the user clicks on entrance button
	 * the method highlights the entrance button with green color,and dehighlights the exit button
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
    void entranceBtnClick(ActionEvent event) {
		ExitBtn.setStyle("-fx-background-color:  #dadada;");
		EntranceBtn.setStyle("-fx-background-color:  #11ff00;");
    }

	/**
	 * this method is launched when the user clicks on exit button
	 * the method highlights the exit button with green color,and dehighlights the entrance button
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
    @FXML
    void exitBtnClick(ActionEvent event) {
    	ExitBtn.setStyle("-fx-background-color:  #11ff00;");
		EntranceBtn.setStyle("-fx-background-color:  #dadada;");
    }
	
    /**
     * this method receives and order and fills the gui fields with the received order information
     * @param order-Order to use to fill fields in gui
     */
	public void fillOrderDetails(Order order)
	{
		SelectedOrderNameLabel.setText(order.getOrderer());
		SelectedOrderDateLabel.setText(order.getVisitDate().toString());
		SelectedOrderNumOfVisitorsLabel.setText(String.valueOf(order.getNumOfVisitors()));
		SelectedOrderPayStatusLabel.setText(order.getPayStatus()?"Payed":"Not Payed");
		SelectedOrderPriceLabel.setText(String.valueOf(order.getPrice()));
		SelectedOrderTimeLabel.setText(order.getVisitTime().toString());
		SelectedOrderTypeLabel.setText(order.getOrderType().toString());
	}
	
	/**
	 * this method clears all the fields in the gui related to order information.
	 */
	public void clearOrderDetails()
	{
		SelectedOrderNameLabel.setText("");
		SelectedOrderDateLabel.setText("");
		SelectedOrderNumOfVisitorsLabel.setText("");
		SelectedOrderPayStatusLabel.setText("");
		SelectedOrderPriceLabel.setText("");
		SelectedOrderTimeLabel.setText("");
		SelectedOrderTypeLabel.setText("");
	}
	
	
}
