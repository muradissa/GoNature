package GUI;

import java.beans.beancontext.BeanContext;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.PrimitiveIterator.OfDouble;

import com.sun.javafx.webkit.ThemeClientImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * controller for popup gui that can in a positive and negative context
 * 
 * @author ayman
 *
 */
public class popUpCC implements Initializable {

	@FXML
	private Label colorLabel2;

	@FXML
	private Label colorLabel;

	@FXML
	private Label mainMessage;

	@FXML
	private Label secondaryMessage;

	@FXML
	private Button Button;

	@FXML
	private ImageView image;

	@FXML
	private ImageView image1;

	private static String succesColor = "-fx-background-color: rgb(58,186,111);";
	private static String failureColor = "-fx-background-color: rgb(239,81,58);";
	private static String color;
	private static boolean success;
	private static String mainMessageStr, secondaryMessageStr, buttonTextStr;

	private double xOffset = 0;
	private double yOffset = 0;

	private static Stage primaryStage;

	/**
	 * this method loads the fxml file for this popUp and displays it on
	 * primaryStage Stage parameter the context of the popUp will be determined
	 * according to the success boolean parameter.
	 * 
	 * @param primaryStage     - the stage to display the popUp on
	 * @param success          - boolean parameter describing the context of popUp
	 *                         ,if its true the popUp will be success popUp
	 *                         ,otherwise the popUp will be a failure popUp
	 * @param mainMessage      - String describing the main message
	 * @param secondaryMessage - String describing secondary message
	 * @param buttonText       - String describing the text inside the button
	 */
	public void start(Stage primaryStage, boolean success, String mainMessage, String secondaryMessage,
			String buttonText) {
		mainMessageStr = mainMessage;
		secondaryMessageStr = secondaryMessage;
		this.success = success;
		this.color = success ? succesColor : failureColor;
		buttonTextStr = buttonText;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/popUp.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			this.primaryStage = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method initializes the elements of this gui
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colorLabel2.setStyle(color + " " + "-fx-border-radius: 30; -fx-background-radius: 30;");
		colorLabel.setStyle(color);
		Button.setStyle(color + " -fx-background-radius: 40;");
		Button.setText(buttonTextStr);
		mainMessage.setText(mainMessageStr);
		secondaryMessage.setText(secondaryMessageStr);

		image1.setVisible(!success);
		image.setVisible(success);

	}

	/**
	 * this method is launched when the user clicks on the button this method closes
	 * the popUp
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */

	@FXML
	void exit(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * this method is launched when the user drags the root anchorPane of the popUp
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void anchorPaneDragged(MouseEvent event) {
		System.out.println(primaryStage.toString());
		primaryStage.setX(event.getScreenX() + xOffset);
		primaryStage.setY(event.getScreenY() + yOffset);
	}

	/**
	 * this method is launched when the user clicks on the root anchorPane of the
	 * popUp
	 * 
	 * @param event-javaFx ActionEvent,the event that started this method
	 */
	@FXML
	void anchorPanePressed(MouseEvent event) {
		xOffset = primaryStage.getX() - event.getScreenX();
		yOffset = primaryStage.getY() - event.getScreenY();
	}

}
