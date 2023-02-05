
package GUI;

import java.net.URL;

import java.util.ResourceBundle;

import controllers.IdentificationCC;
import controllers.ServiceRepresentativeCC;
import controllers.SubscriberCC;
import controllers.inputValidator;
import entities.CreditCard;
import entities.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * this class is for addSubscriptionGui
 * @author Group2
 *
 */

public class addSubscriptionCC implements Initializable {
	@FXML
	private ImageView CVVStar;

	@FXML
	private ImageView expireDateStar;

	@FXML
	private Label creditCardnumberLabel;

	@FXML
	private Label expireDateLabel;

	@FXML
	private Label CVVLabel;

	@FXML
	private Label firstBoxLabel;

	@FXML
	private Label secondBoxLabel;

	@FXML
	private Label errorLabel;

	@FXML
	private ImageView creditNumberStar;

	@FXML
	private TextField textFieldForEmail;

	@FXML
	private CheckBox creditCardCheckBox;

	@FXML
	private ImageView imageForPeople;
	@FXML
	private TextField textFieldForCreditCard;

	@FXML
	private TextField textFieldForExpireDate;

	@FXML
	private TextField textFieldForCVV;

	@FXML
	private TextField textFieldForFirstName;

	@FXML
	private TextField textFieldForLastName;

	@FXML
	private TextField textFieldForTelephone;

	@FXML
	private ImageView visaImage;

	@FXML
	private ComboBox<Integer> numberOfMembers;

	@FXML
	private TextField textFieldForId;

	@FXML
	private Button addSubscriptionButton;

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
 *this method is launched when the user press add subscription button
 * this method opens add subscription page  while also closing the current page
 * @param event -javaFx ActionEvent,the event that started this method 
 */
	@FXML
	void addSubscription(ActionEvent event) {

		if (inputValidator.isAlpha(textFieldForFirstName.getText()) == false) {
			errorLabel.setText("Please make sure you enter a valid First Name");
			errorLabel.setVisible(true);
			return;
		}

		if (inputValidator.isAlpha(textFieldForLastName.getText()) == false) {
			errorLabel.setText("Please make sure you enter a valid Last Name");
			errorLabel.setVisible(true);
			return;
		}

		if (textFieldForTelephone.getText().equals("")) {
			errorLabel.setText("Please make sure Telephone is a number with 10 digits");
			errorLabel.setVisible(true);
			return;
		}

		if (inputValidator.checkAllDigitsOfSize(textFieldForTelephone.getText().length(),
				textFieldForTelephone.getText()) == false) {
			errorLabel.setText("Please make sure Telephone is a number with 10 digits");
			errorLabel.setVisible(true);
			return;
		}
		if (textFieldForTelephone.getText().length() != 10) {
			errorLabel.setText("Please make sure Telephone is a number with 10 digits");
			errorLabel.setVisible(true);
			return;
		}

		if (inputValidator.checkEmail(textFieldForEmail.getText()) == false) {
			errorLabel.setText("Please make sure you enter a valid email");
			errorLabel.setVisible(true);
			return;
		}

		if (inputValidator.checkId(textFieldForId.getText()) == false) {
			errorLabel.setText("Please make sure ID is a number with 9 digits");
			errorLabel.setVisible(true);
			return;
		}

		// creditCard
		if (creditCardCheckBox.isSelected()) {
			if (textFieldForCreditCard.getText().equals("")) {
				errorLabel.setText("Please make sure you enter valid Credit Card number");
				errorLabel.setVisible(true);
				return;
			}
			if (textFieldForExpireDate.getText().equals("")) {
				errorLabel.setText("Please make sure you enter valid Expire Date");
				errorLabel.setVisible(true);
				return;
			}
			if (textFieldForCVV.getText().equals("")) {
				errorLabel.setText("Please make sure you enter valid CVV");
				errorLabel.setVisible(true);
				return;
			}
		}

		// success case
		System.out.println(ServiceRepresentativeCC.getSubscriptionCounter());
		String subscriptionNumber = String.valueOf(ServiceRepresentativeCC.getSubscriptionCounter());
		if (SubscriberCC.subscriptionNumberValidator(textFieldForId.getText())) {
			errorLabel.setVisible(false);
			popUpCC failure = new popUpCC();
			failure.start(new Stage(), false, "oops", "Subscription is already exists", "OK");
			return;
		}

		if (creditCardCheckBox.isSelected()) {
			Subscriber subscriber1 = new Subscriber(subscriptionNumber, textFieldForId.getText(),
					textFieldForFirstName.getText(), textFieldForLastName.getText(), textFieldForTelephone.getText(),
					textFieldForEmail.getText(), numberOfMembers.getValue(),
					new CreditCard(textFieldForCreditCard.getText(), textFieldForExpireDate.getText(),
							textFieldForCVV.getText(), textFieldForId.getText()));
			ServiceRepresentativeCC.addNewSubscriber(subscriber1);
		} else {
			Subscriber subscriber2 = new Subscriber(subscriptionNumber, textFieldForId.getText(),
					textFieldForFirstName.getText(), textFieldForLastName.getText(), textFieldForTelephone.getText(),
					textFieldForEmail.getText(), numberOfMembers.getValue(), null);
			ServiceRepresentativeCC.addNewSubscriber(subscriber2);

		}
		errorLabel.setVisible(false);
		popUpCC success = new popUpCC();
		success.start(new Stage(), true, "Awesome", "Subscription added successfully, subscription # : "+subscriptionNumber, "Great!");
	}

	/**
	 * this method if the credit card check box is selected or not
	 * @param event
	 */
	@FXML
	void creditCardCheck(ActionEvent event) {

		if (creditCardCheckBox.isSelected())
		{
			imageForPeople.setVisible(false);
			hideOrShow(true);
		}
		else
		{
			imageForPeople.setVisible(true);
			hideOrShow(false);
		}

	}
	/**
	 * this method runs at the beginning
	 * this method initialize the combo box 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorLabel.setVisible(false);
		numberOfMembers.getItems().removeAll(numberOfMembers.getItems());
		for (int i = 1; i <= 15; i++)
			numberOfMembers.getItems().add(i);
		numberOfMembers.getSelectionModel().select(0);
		hideOrShow(false);

	}

	/**
	 * this method makes the labels and images visible if the input is true
	 * otherwise the method makes the labels and images invisible
	 * @param check
	 */
	public void hideOrShow(Boolean check) {
		creditCardnumberLabel.setVisible(check);
		textFieldForCreditCard.setVisible(check);
		creditNumberStar.setVisible(check);
		expireDateLabel.setVisible(check);
		textFieldForExpireDate.setVisible(check);
		expireDateStar.setVisible(check);
		CVVLabel.setVisible(check);
		textFieldForCVV.setVisible(check);
		CVVStar.setVisible(check);
		visaImage.setVisible(check);

	}

}
