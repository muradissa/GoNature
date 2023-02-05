package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.IdentificationCC;
import controllers.ServiceRepresentativeCC;
import controllers.inputValidator;
import entities.Traveler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * this class is for addGuideGui
 * @author Group2
 *
 */
public class addGuideCC implements Initializable{

	@FXML
	private Button addGuideButton;

	@FXML
	private TextField textFieldForId;
	
    @FXML
    private Label errorLabel;
    
    @FXML
    private ImageView visitor_guide_img;
    
    /**
	 * this method is launched when the user put his mouse close to the button
	 * this method makes the button shining
	 * @param event -javaFx MouseEvent,the event that started this method 
	 */
    @FXML
    void mouseEntered(MouseEvent event) {
    	if(event.getSource() instanceof Button)	
    	((Button)event.getSource()).setEffect(new DropShadow());

    }

    /**
	 * this method is launched when the user put his mouse far from the button
	 * this method makes the button stop shining
	 * @param event -javaFx MouseEvent,the event that started this method 
	 */
    @FXML
    void mouseExited(MouseEvent event) {
    	if(event.getSource() instanceof Button)
    	((Button)event.getSource()).setEffect(null);

    }

/**
 *this method is launched when the user press add guide button
 * this method opens Register Guide page while also closing the current page
 * @param event -javaFx ActionEvent,the event that started this method 
 */
	@FXML
	void addGuide(ActionEvent event) {

		if (inputValidator.checkId(textFieldForId.getText()) == false) {
			errorLabel.setVisible(true);
			return;
		}
		
		if (IdentificationCC.checkGuide(textFieldForId.getText())==true)
		{
			popUpCC failure=new popUpCC();
			failure.start(new Stage(),false, "oops","Guide is already exists", "OK");
			errorLabel.setVisible(false);
			return;
		}
		
		Traveler traveler=new Traveler(textFieldForId.getText(), true);
		ServiceRepresentativeCC.addNewGuide(traveler);
		popUpCC success=new popUpCC();
		success.start(new Stage(),true, "Awesome","Guide added successfully", "Great!");
		errorLabel.setVisible(false);
	}

	/**
	 * this method runs at the beginning
	 * this method initialize the errors label to be invisible
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorLabel.setVisible(false);

	}

}
