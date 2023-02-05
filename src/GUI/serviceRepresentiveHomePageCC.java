package GUI;

import java.util.ArrayList;

import java.util.ResourceBundle;

import Client.GoNatureClient;
import Client.ClientUI;
import controllers.IdentificationCC;
import controllers.ServiceRepresentativeCC;
import entities.Subscriber;
import entities.Traveler;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * this class is for serviceRepresentiveHomePage
 * 
 * @author Group2
 *
 */
public class serviceRepresentiveHomePageCC implements Initializable {

	@FXML
	private ImageView homePageButton;

	@FXML
	private ImageView addGuideButton;

	@FXML
	private ImageView addSubscriberButton;

	@FXML
	private Label pageTextLabel;

	@FXML
	private Label helloMessageLabel;

	@FXML
	private Button logOutButton;

	@FXML
	private AnchorPane anPane;

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
	 * this method is launched when the user put his mouse close to the button this
	 * method makes the button shining
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void mouseEntered(MouseEvent event) {

		if (event.getSource() instanceof Button)
			((Button) event.getSource()).setEffect(new DropShadow());
		logOutButton.getScene().setCursor(Cursor.HAND);

	}

	/**
	 * this method is launched when the user put his mouse far from the button this
	 * method makes the button stop shining
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void mouseExited(MouseEvent event) {

		if (event.getSource() instanceof Button)
			((Button) event.getSource()).setEffect(null);
		logOutButton.getScene().setCursor(Cursor.DEFAULT);

	}

	/**
	 * this method is launched when the user press add subscription button this
	 * method opens add subscription page while also closing the current page
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void addSubscriberGui(MouseEvent event) {

		try {
			pageTextLabel.setText("Add Subscription Page");
			anPane.getChildren().clear();
			anPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("addSubscriptionGui.fxml")));
			selectedImage(addSubscriberButton);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method is launched when the user press add guide button this method
	 * opens add guide page while also closing the current page
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void openAddGuideGui(MouseEvent event) {

		try {
			pageTextLabel.setText("Add Register Guide Page");
			anPane.getChildren().clear();
			anPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("registerGuide.fxml")));
			selectedImage(addGuideButton);
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
	void logOut(ActionEvent event) {

		ClientUI clientUI = new ClientUI();
		((Node) event.getSource()).getScene().getWindow().hide();
		clientUI.chat.quit();
		clientUI.start(new Stage());
	}

	/**
	 * this method is launched when the user press HomePage button this method opens
	 * home page of ServiceRepresentative while also closing the current page
	 * 
	 * @param event -javaFx MouseEvent,the event that started this method
	 */
	@FXML
	void openServiceHomePageGui(MouseEvent event) {
		try {
			pageTextLabel.setText("Home Page");
			anPane.getChildren().clear();
			anPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("serviceRepresentiveInnerGui.fxml")));
			selectedImage(homePageButton);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method lunches serviceRepresentiveHomePageGUI
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/serviceRepresentiveHomePageGUI.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Service Representive");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method runs at the beginning this method initialize the labels on the
	 * screen
	 */
	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		String name = GoNatureClient.currentWorker.getFirstName();
		helloMessageLabel.setText("Hello," + name + " (Service Representive)");
		openServiceHomePageGui(null);

	}

}
