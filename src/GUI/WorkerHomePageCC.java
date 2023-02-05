package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import Client.ClientUI;
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

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

/**
 * controller for the workerHomePage GUI
 * 
 * @author ayman
 *
 */
public class WorkerHomePageCC implements Initializable {

	@FXML
	private ImageView HomePageBtn;

	@FXML
	private ImageView NewOrderButton;

	@FXML 
	private Label PageTitleLabel;

	@FXML
	private Label GreetingLabel;

	@FXML
	private Button LogOutBtn;

	@FXML
	private Pane innerPane;
	
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
	 * this method loads the fxml file for this gui and displays it on primaryStage
	 * Stage parameter
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/WorkerHomePage.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(GoNatureClient.currentWorker.getPark().getName());
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user clicks homepage button this method
	 * loads the homepage gui inside the inner anchorPane
	 * 
	 * @param event-javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void goToHomePage(MouseEvent event) {
		try {
			innerPane.getChildren().clear();
			innerPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("WorkerHomePageGUI(Inner).fxml")));
			PageTitleLabel.setText("Worker Home Page");
			GreetingLabel.setText("Hello," + GoNatureClient.currentWorker.getFirstName() + "(Worker)");
			selectedImage(HomePageBtn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user clicks new order button this method
	 * loads the unplanned visit gui inside the inner anchorPane
	 * 
	 * @param event-javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void goToUnplannedOrderPage(MouseEvent event) {
		try {
			innerPane.getChildren().clear();
			innerPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("UnplannedVisitGUI.fxml")));
			PageTitleLabel.setText("Unplanned Visit Page");
			selectedImage(NewOrderButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user clicks the log out button this method
	 * closes the worker window and loads the homepage window
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 * @throws IOException
	 */
	@FXML
	void LogOut(ActionEvent event) throws IOException {
		ClientUI clientUI = new ClientUI();
		((Node) event.getSource()).getScene().getWindow().hide();
		clientUI.chat.quit();
		clientUI.start(new Stage());
	}

	/**
	 * this method loads the default inner anchor pane
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		goToHomePage(null);
	}

	@FXML
	void mouseEntered(MouseEvent event) {
		LogOutBtn.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void mouseExit(MouseEvent event) {
		LogOutBtn.getScene().setCursor(Cursor.DEFAULT);
	}

}
