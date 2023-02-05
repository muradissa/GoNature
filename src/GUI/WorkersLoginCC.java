package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.geom.Area;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.sun.javafx.webkit.ThemeClientImpl;

import Client.GoNatureClient;
import Client.ClientController;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import controllers.*;
import entities.*;

/**
 * controller for the workerlogin GUI
 * 
 * @author ayman
 *
 */
public class WorkersLoginCC {

	@FXML
	private AnchorPane AnPane;

	@FXML
	private Label incorrectLogin;

	@FXML
	private TextField userName;

	@FXML
	private PasswordField password;

	@FXML
	private ImageView backButton;

	@FXML
	private Button signInBtn; 

	/**
	 * this method is launched when the user presses a key if the key pressed is
	 * enter,the method calls the sign in button method
	 * 
	 * @param event-javaFx KeyEvent,the event that started this method
	 */
	@FXML
	void EnterPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER))
			actionSignIbBtn(new ActionEvent());
	}

	/**
	 * this method is launched when the user clicks back button this method closes
	 * the current window and opens the homepage gui
	 * 
	 * @param event-javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void backClicked(MouseEvent event) {
		Stage stage = (Stage) AnPane.getScene().getWindow();
		HomePageCC homePage = new HomePageCC();
		homePage.start(stage);

	}

	@FXML
	void mouseEntered(MouseEvent event) {
		signInBtn.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void mouseExit(MouseEvent event) {
		signInBtn.getScene().setCursor(Cursor.DEFAULT);
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
			loader.setLocation(getClass().getResource("/GUI/WorkersLoginGUI.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign In");
			primaryStage.show();

		} catch (Exception e) {
			System.out.println("Error At WorkersLoginCC.start");
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user clicks on sign in button if the login
	 * credentials the user inputted are valid and he hasn't logged in already,the
	 * method will open the relevant homepage (according to type of
	 * worker),otherwise method will show an error msg.
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void actionSignIbBtn(ActionEvent event) {

		String textUserName;
		String textPassword;
		textUserName = userName.getText();
		textPassword = password.getText();
		ArrayList<Object> params = new ArrayList<Object>();
		Worker worker;

		worker = WorkerLoginCC.checkWorker(textUserName, textPassword);
		GoNatureClient.currentWorker = worker;

		if (worker instanceof ServiceRepesentative) {
			try {
				params.add(new Connection(InetAddress.getLocalHost().getHostAddress(),
						InetAddress.getLocalHost().getHostName(), "Service Representative", worker.getWorkerId()));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ClientMessage msg = new ClientMessage("", params, params.size());
			ClientUI.chat.accept(msg);
			ServerMessage sm = GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
			if ((boolean) sm.getData()) {
				serviceRepresentiveHomePageCC vSHPCC = new serviceRepresentiveHomePageCC();
				vSHPCC.start(new Stage());
				AnPane.getScene().getWindow().hide();
			} else {
				incorrectLogin.setText("You are already logged in!");
				incorrectLogin.setVisible(true);
			}
		} else if (worker instanceof ParkManager) {
			try {
				params.add(new Connection(InetAddress.getLocalHost().getHostAddress(),
						InetAddress.getLocalHost().getHostName(), "Park Manager", worker.getWorkerId()));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ClientMessage msg = new ClientMessage("", params, params.size());
			ClientUI.chat.accept(msg);

			ServerMessage sm = GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
			if ((boolean) sm.getData()) {
				ParkManagerBaseGuiC vSHPCC = new ParkManagerBaseGuiC();
				vSHPCC.start(new Stage());
				AnPane.getScene().getWindow().hide();
			} else {
				incorrectLogin.setText("You are already logged in!");
				incorrectLogin.setVisible(true);
			}

		} else if (worker instanceof DepartmentManager) {
			try {
				params.add(new Connection(InetAddress.getLocalHost().getHostAddress(),
						InetAddress.getLocalHost().getHostName(), "Department Manager", worker.getWorkerId()));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ClientMessage msg = new ClientMessage("", params, params.size());
			ClientUI.chat.accept(msg);
			ServerMessage sm = GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
			if ((boolean) sm.getData()) {
				DepManagerBaseGUIController vSHPCC = new DepManagerBaseGUIController();
				vSHPCC.start(new Stage());
				AnPane.getScene().getWindow().hide();
			} else {
				incorrectLogin.setText("You are already logged in!");
				incorrectLogin.setVisible(true);
			}
		} else if (worker instanceof Worker) {
			try {
				params.add(new Connection(InetAddress.getLocalHost().getHostAddress(),
						InetAddress.getLocalHost().getHostName(), "Worker", worker.getWorkerId()));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ClientMessage msg = new ClientMessage("", params, params.size());
			ClientUI.chat.accept(msg);
			ServerMessage sm = GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
			if ((boolean) sm.getData()) {
				WorkerHomePageCC workerHomePageCC = new WorkerHomePageCC();
				workerHomePageCC.start(new Stage());
				AnPane.getScene().getWindow().hide();
			} else {
				incorrectLogin.setText("You are already logged in!");
				incorrectLogin.setVisible(true);
			}
		} else {
			System.out.println("This Account is not exist");
			incorrectLogin.setText("*Login details are incorrect*");
			incorrectLogin.setVisible(true);

		}

	}

}
