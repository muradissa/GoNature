package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.GoNatureClient;
import Client.ClientUI;
import entities.DepartmentManager;
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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DepManagerBaseGUIController implements Initializable {

	@FXML
	private ImageView HomePageImage;

	@FXML
	private ImageView ReportsImage;

	@FXML
	private ImageView ParametersImage;

	@FXML
	private ImageView SalesImage;

	@FXML
	private ImageView VacancyImage;

	@FXML
	private Label RoleLabel;

	@FXML
	private Button LogoutBtn;

	@FXML
	private AnchorPane AnPane;

	@FXML
	private Label MainLabel;

	private ImageView selected;

	public static DepartmentManager depManager;

	/**
	 * this function loading HomePage GUI by replace the inner pane in the pane GUI
	 * with the HomePage Pane
	 * 
	 * @param event
	 */
	@FXML
	void HomePageClicked(MouseEvent event) {
		try {
			AnPane.getChildren().clear();
			AnPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("DepartmentMangagerHomePageGUI.fxml")));
			MainLabel.setText("Home Page");
			if (selected == null)
				selected = HomePageImage;
			selectedImage(HomePageImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function log out the current user from the system after clicking log out
	 * button
	 * 
	 * @param event
	 */
	@FXML
	void LogOutClicked(ActionEvent event) {
		Stage stage = (Stage) AnPane.getScene().getWindow();
		HomePageCC homePage = new HomePageCC();
		ClientUI.chat.quit();
		homePage.start(stage);

	}

	/**
	 * this function load the department parameter GUI after click the button
	 * 
	 * @param event
	 */
	@FXML
	void ParametersClicked(MouseEvent event) {
		try {
			AnPane.getChildren().clear();
			AnPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("DepartmentManagerParametersGUI.fxml")));
			MainLabel.setText("Parameters Confirm Page");

			selectedImage(ParametersImage);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void selectedImage(ImageView newImage) {

		Pane p1 = (Pane) selected.getParent();
		p1.setStyle(null);

		Pane p = (Pane) newImage.getParent();

		p.setStyle("-fx-background-color: #e7d8c0");

		selected = newImage;
	}

	@FXML
	void mouseEntered(MouseEvent event) {
		LogoutBtn.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void mouseExit(MouseEvent event) {
		LogoutBtn.getScene().setCursor(Cursor.DEFAULT);
	}

	/**
	 * this function load the department report GUI after click the button
	 * 
	 * @param event
	 */
	@FXML
	void ReportsClicked(MouseEvent event) {
		try {
			AnPane.getChildren().clear();
			AnPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("DepManagerReportsGui.fxml")));
			MainLabel.setText("Reports Page");
			selectedImage(ReportsImage);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	
	
	
	public ImageView getParametersImage() {
		return ParametersImage;
	}

	public ImageView getSalesImage() {
		return SalesImage;
	}

	/**
	 * this function load the department sale GUI after click the button
	 * 
	 * @param event
	 */
	@FXML
	void SalesClicked(MouseEvent event) {
		try {
			AnPane.getChildren().clear();
			AnPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ConfirmSalesGui.fxml")));
			MainLabel.setText("Sales Confirm Page");
			selectedImage(SalesImage);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * this function set the GUI main Title
	 * 
	 * @param txt
	 */
	void setTxt(String txt) {

		MainLabel.setText(txt);
	}

	/**
	 * this function load the department check vacancy GUI after click the button
	 * 
	 * @param event
	 */
	@FXML
	void VacancyClicked(MouseEvent event) {
		try {
			AnPane.getChildren().clear();
			AnPane.getChildren()
					.setAll((Node) FXMLLoader.load(getClass().getResource("CheckVacancyDepManagerGUI.fxml")));
			MainLabel.setText("Sales Confirm Page");
			selectedImage(VacancyImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/DepartmentManagerBaseGui.fxml"));
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(getClass().getResource("DepartmentMangagerHomePageGUI.fxml"));
			DepManagerControllerGUI c = loader2.getController();
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Department Manager GUI");
			primaryStage.show();
			c.controller = loader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function prepare the name label and the GUI title name before load this
	 * GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HomePageClicked(null);
		RoleLabel.setText("Hello, " + GoNatureClient.currentWorker.getFirstName() + "(Department Manager)");
		MainLabel.setText("Home Page");

	}

}
