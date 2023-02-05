package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * controller class for the home page GUI
 * 
 * @author murad
 *
 */
public class HomePageCC {

	@FXML
	private Button workHerebtn;

	@FXML
	private Button startOrderbtn;

	/**
	 * this method loads the fxml file for this gui and displays it on primaryStage
	 * Stage parameter
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/HomePageGui.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Home Page");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is launched when the user clicks on start order button, this
	 * method opens customer identification page while also closing the current page
	 * 
	 * @param event -javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void openIdentificationGUI(ActionEvent event) {

		IdentificationGUICC idController = new IdentificationGUICC();
		idController.start(new Stage());

		((Node) event.getSource()).getScene().getWindow().hide();

	}

	/**
	 * this method is launched when the user clicks on worker button, this method
	 * opens worker login page while also closing the current page
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void openSignInGUI(ActionEvent event) {

		WorkersLoginCC cc = new WorkersLoginCC();
		cc.start(new Stage());

		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * this method gives the button it is used on , a dropShadow effect when the
	 * user's mouse enters the bounds of the button
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void MouseEntered(MouseEvent event) {
		((Button) event.getSource()).setEffect(new DropShadow());
		startOrderbtn.getScene().setCursor(Cursor.HAND);

	}

	/**
	 * this method gives the button it is used on , a Reflection effect when the
	 * user's mouse enters the bounds of the button
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void MouseExited(MouseEvent event) {
		((Button) event.getSource()).setEffect(new Reflection(0.0, 0.79, 0.4, 0.06));
		startOrderbtn.getScene().setCursor(Cursor.DEFAULT);

	}

}
