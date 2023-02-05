package GUI;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import controllers.*;
import entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * controller class for the traveler/subscriber identification GUI
 * 
 * @author murad
 *
 */
public class IdentificationGUICC implements Initializable {

	private Button bt1;
	private Button bt2;

	@FXML
	private AnchorPane AnPane;

	@FXML
	private ImageView backImage;

	@FXML
	private Button id_btn;
	@FXML
	private Button subscription_btn;
	@FXML
	private Label id_sub_label;
	@FXML
	private Label error_label;
	@FXML
	private Button identify_btn;
	@FXML
	private TextField id_sub_text;
	@FXML
	private Label star_1_label;

	/**
	 * this method is launched when the user presses a key if the key pressed is
	 * enter,the method calls the sign in button method
	 * 
	 * @param event-javaFx KeyEvent,the event that started this method
	 */
	@FXML
	void EnterPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER))
			identificationBtnAction(new ActionEvent());
	}

	/**
	 * this method loads the fxml file for this gui and displays it on primaryStage
	 * Stage parameter
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/IdentificationPageGui.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("identification");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void mouseEntered(MouseEvent event) {
		id_btn.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void mouseExit(MouseEvent event) {
		id_btn.getScene().setCursor(Cursor.DEFAULT);
	}

	/**
	 * this method is launched when the user clicks on id button this method
	 * highlights the id button with green color and dehighlights the subscription
	 * button
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void idBtnAction(ActionEvent event) {
		id_btn.setStyle("-fx-background-color:  #11ff00;");
		subscription_btn.setStyle("-fx-background-color:  #dadada;");
		try {
			id_sub_label.setText("id :");
			;
			id_sub_text.setPromptText("Enter Your Id Number");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user clicks on subscription button this
	 * method highlights the subscription button with green color and dehighlights
	 * the id button
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void subscriptionBtnAction(ActionEvent event) {
		try {
			id_btn.setStyle("-fx-background-color:  #dadada;");
			subscription_btn.setStyle("-fx-background-color:  #11ff00;");
			id_sub_label.setText("subscription:");
			id_sub_text.setPromptText("Enter Your Subscription");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user clicks on back button this method
	 * closes the traveler identification window and opens home page window
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void backClicked(MouseEvent event) {
		Stage stage = (Stage) AnPane.getScene().getWindow();
		HomePageCC homePage = new HomePageCC();
		homePage.start(stage);

	}

	/**
	 * this method is launched when the user clicks on identify button this method
	 * checks the user input(id/subscription number) is correct, if its correct the
	 * method closes current window and moves to the relevant window(id/subscription
	 * hompages) if the user input is incorrect the method shows an error msg to the
	 * user on the gui
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void identificationBtnAction(ActionEvent event) {

		String typeLogin = id_sub_label.getText();
		String txtNumber = id_sub_text.getText();
		ArrayList<Object> params = new ArrayList<Object>();
		if (typeLogin.equals("id :")) {
			if (inputValidator.checkId(txtNumber)) {
				GoNatureClient.currentTraveler = IdentificationCC.checkId(txtNumber);
				VisitorHomePageCC.idNumber_static = txtNumber;
				try {
					params.add(new Connection(InetAddress.getLocalHost().getHostAddress(),
							InetAddress.getLocalHost().getHostName(), GoNatureClient.currentTraveler == null ? "Traveler"
									: GoNatureClient.currentTraveler.isGuide() ? "Guide" : "Traveler",
							txtNumber));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ClientMessage msg = new ClientMessage("", params, params.size());
				ClientUI.chat.accept(msg);
				ServerMessage sm = GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
				if ((boolean) sm.getData()) {
					VisitorHomePageCC vSHPCC = new VisitorHomePageCC();
					vSHPCC.start(new Stage());
					id_btn.getScene().getWindow().hide();
				} else {
					error_label.setText("You are already logged in");
				}
			} else {
				error_label.setText("Error: id number should be 9 digits");
			}
		}
		if (typeLogin.equals("subscription:")) {
			if (SubscriberCC.subscriptionNumberValidator(txtNumber)) {
				GoNatureClient.currentTraveler = IdentificationCC.checkSubscriptionNumber(txtNumber);
				VisitorHomePageCC.subscriptionNumber_static = txtNumber;
				try {
					params.add(new Connection(InetAddress.getLocalHost().getHostAddress(),
							InetAddress.getLocalHost().getHostName(), "Subscriber",
							GoNatureClient.currentTraveler.getIdNumber()));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ClientMessage msg = new ClientMessage("", params, params.size());
				ClientUI.chat.accept(msg);

				ServerMessage sm = GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
				if ((boolean) sm.getData()) {
					VisitorHomePageCC vSHPCC = new VisitorHomePageCC();
					vSHPCC.start(new Stage());
					id_btn.getScene().getWindow().hide();
				} else {
					error_label.setText("You are already logged in");
				}
			} else {
				error_label.setText("Error: subscription number not found");
			}
		}
	}

	/**
	 * this method initializes the elements of this gui
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bt1 = new Button();
		bt2 = new Button();
		bt1.setBackground(id_btn.getBackground());
		bt2.setBackground(subscription_btn.getBackground());
	}
}
