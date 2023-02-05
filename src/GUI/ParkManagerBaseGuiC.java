package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import Client.GoNatureClient;
import Client.ClientUI;
import entities.ParkManager;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ParkManagerBaseGuiC implements Initializable {

	ParkManager ParkManager = (ParkManager) GoNatureClient.currentWorker;
	@FXML
	private ImageView HomepageBtn;

	@FXML
	private ImageView ReportButton;

	@FXML
	private ImageView ChangeParameterBtn;

	@FXML
	private ImageView SetSaleBtn;

	@FXML
	private ImageView CheckParkVacancyBtn;

	@FXML
	private Label PageTitle;

	@FXML
	private Label NameTextView;

	@FXML
	private Button LogoutBtn;

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
	 * this function Load the pane of homePage of parkmanger GUI
	 */
	public void initialize(URL location, ResourceBundle resources) {
		try {

			NameTextView.setText("Hello , (" + ParkManager.getFirstName() + " " + ParkManager.getLastName() + ")");
			anPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("/GUI/ParkMangerHomePage(Inner).fxml")));
			selected = HomepageBtn;
			selectedImage(HomepageBtn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function start the Scene of parkmangerBaseGui
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/parkManagerBaseGUI.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("GONATURE");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function set the button handle event , it open 'homePage' Gui ( by load
	 * the pane of the Gui and place it on the old pane )
	 * 
	 * @param event
	 */
	@FXML
	void OpenHomePage(MouseEvent event) {
		try {
			anPane.getChildren().clear();
			anPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("/GUI/ParkMangerHomePage(Inner).fxml")));
			PageTitle.setText("Home Page");

			selectedImage(HomepageBtn);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * this function load 'park manger report GUI' after clicking on report Button
	 * in the baseGui it load the pane of the report GUI and replace it on the inner
	 * pane in the baseGui
	 * 
	 * @param event
	 */
	@FXML
	void OpenReportGUI(MouseEvent event) {
		try {
			// setPageTitle("Reports page");

			PageTitle.setText("Reports Page");
			anPane.getChildren().clear();
			anPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManagersReportsGui.fxml")));
			selectedImage(ReportButton);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * this function load the pane of the 'change park parameter's' it replace the
	 * GUI on the inner pane in the ParkMangerbaseGui
	 * 
	 * @param event
	 */
	@FXML
	void OpenChangeParkParameters(MouseEvent event) {
		try {
			PageTitle.setText("Change Parameters Page");
			anPane.getChildren().clear();
			anPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("/GUI/ChangeParametersGui.fxml")));
			selectedImage(ChangeParameterBtn);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * This function open the GUI of 'check park vacancy GUI ' it replace the GUI on
	 * the inner pane in the ParkMangerbaseGui
	 * 
	 * @param event
	 */
	@FXML
	void OpenCheckParkVacancyGui(MouseEvent event) {
		try {
			PageTitle.setText("Check Park Vacancy Page");
			anPane.getChildren().clear();
			anPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("/GUI/CheckVacancyParkMangerGUI.fxml")));
			selectedImage(CheckParkVacancyBtn);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * this function open the 'set new sale GUI' it load the ' set new sale GUI '
	 * after clicking on Set new Sale Button it replace the GUI on the inner pane in
	 * the ParkMangerbaseGui
	 * 
	 * @param mouseEvent event
	 */
	@FXML
	void OpenNewSaleGui(MouseEvent event) {
		try {
			PageTitle.setText("Set New Sale Page");
			anPane.getChildren().clear();
			anPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("/GUI/SetSalesParkManagerGui.fxml")));
			selectedImage(SetSaleBtn);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * this function disconnect the connector from the system and open the LogIn GUI
	 * 
	 * @param MouseEvent event
	 */
	@FXML
	void Logout(MouseEvent event) {
		ClientUI clientUI = new ClientUI();
		((Node) event.getSource()).getScene().getWindow().hide();
		clientUI.chat.quit();
		clientUI.start(new Stage());
	}

	@FXML
	void mouseEntered(MouseEvent event) {
		LogoutBtn.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void mouseExit(MouseEvent event) {
		LogoutBtn.getScene().setCursor(Cursor.DEFAULT);
	}

}
