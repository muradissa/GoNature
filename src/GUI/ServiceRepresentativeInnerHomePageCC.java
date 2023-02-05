package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import com.mysql.cj.conf.StringProperty;

import controllers.IdentificationCC;
import controllers.ServiceRepresentativeCC;
import entities.Subscriber;
import entities.Traveler;
import entities.personAddToTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


/**
 * this class is for ServiceRepresentative inner Pane
 * @author Group2
 *
 */
public class ServiceRepresentativeInnerHomePageCC implements Initializable {

	@FXML
	private Label firstBoxLabel;

	@FXML
	private Label secondBoxLabel;

	@FXML
	private TextField textFieldForId;

	@FXML
	private Button searchForIdButton;

	@FXML
	private TableColumn<personAddToTable, String> fullNameColumn;

	@FXML
	private TableColumn<personAddToTable, String> idColumn;

	@FXML
	private TableColumn<personAddToTable, String> SubscriptionOrGuideColumn;

	@FXML
	private TableView<personAddToTable> tableView;

	private ObservableList<personAddToTable> data = FXCollections.observableArrayList();
	
	/**
	 * this method is launched when the user put his mouse close to the button
	 * this method makes the button shining
	 * @param event -javaFx MouseEvent,the event that started this method 
	 */

	@FXML
    void mouseEntered(MouseEvent event) {
    	if(event.getSource() instanceof Button)
    			((Button)event.getSource()).setEffect(new DropShadow());
    	else
    		((ImageView)event.getSource()).setEffect(new DropShadow());
    	
    	

    }

	/**
	 * this method is launched when the user put his mouse far from the button
	 * this method makes the button stop shining
	 * @param event -javaFx MouseEvent,the event that started this method 
	 */
    @FXML
    void mouseExited(MouseEvent event) {
    	
    	if(event.getSource() instanceof Button)
			((Button)event.getSource()).setEffect(new DropShadow());
	else
		((ImageView)event.getSource()).setEffect(new DropShadow());

    }

    /**
	 * this method is launched when the user clicks on search button,
	 * this method search the id in the table view and filter non relevant cells
	 * @param event -javaFx ActionEvent,the event that started this method 
	 */

	@FXML
	void searchId(ActionEvent event) {
		tableView.setItems(filterList(data, textFieldForId.getText()));
	}

	/**
	 * this method is launched when the user clicks on search button,
		 * this method returns true if there is a relevant result in table view
		 * if there isn't any relevant result the method returns false.
	 * @param person
	 * @param searchText
	 * @return boolean
	 */
	private boolean searchFindsOrder(personAddToTable person, String searchText){

		return person.getId().toLowerCase().startsWith(searchText.toLowerCase());
	}

/**
 *  this method is launched when the user clicks on search button,
		 * this method returns filtered ObservableList with relevant results
 * @param list
 * @param searchText
 * @return filtered ObservableList
 */
	private ObservableList<personAddToTable> filterList(List<personAddToTable> list, String searchText) {
		List<personAddToTable> filteredList = new ArrayList<>();
		for (personAddToTable person : list) {
			if (searchFindsOrder(person, searchText))
				filteredList.add(person);
		}
		
		return FXCollections.observableList(filteredList);
	}
	
	

	/**
	 * this method runs at the beginning
	 * this method initialize the table view with all the subscribers and guides in the application 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ArrayList<Subscriber> subList = ServiceRepresentativeCC.getAllSubscribers();
		ArrayList<Traveler> guideList = ServiceRepresentativeCC.getAllGuides();
		int i = 0;
		// ObservableList<personAddToTable> data = FXCollections.observableArrayList();

		for (i = 0; i < subList.size(); i++) {
			Subscriber sub = subList.get(i);
			SimpleStringProperty fullName = new SimpleStringProperty(
					(String) sub.getFirstName() + " " + sub.getLastName());
			SimpleStringProperty id = new SimpleStringProperty((String) sub.getIdNumber());
			personAddToTable p1 = new personAddToTable(fullName, id, new SimpleStringProperty((String) "Subscription"));
			data.add(p1);

		}

		for (i = 0; i < guideList.size(); i++) {
			Traveler guide = guideList.get(i);
			SimpleStringProperty id = new SimpleStringProperty((String) guide.getIdNumber());
			personAddToTable p2 = new personAddToTable(new SimpleStringProperty((String) ""), id,
					new SimpleStringProperty((String) "Guide"));
			data.add(p2);
		}

		fullNameColumn.setCellValueFactory(new PropertyValueFactory<personAddToTable, String>("fullName"));
		idColumn.setCellValueFactory(new PropertyValueFactory<personAddToTable, String>("id"));
		SubscriptionOrGuideColumn.setCellValueFactory(new PropertyValueFactory<personAddToTable, String>("subOrGuide"));
		tableView.setItems(data);
		tableView.getSortOrder().add(idColumn);

		initLabels(String.valueOf(subList.size()), "sub");
		initLabels(String.valueOf(guideList.size()), "guide");

	}

	/**
	 * this method initialize the labels on the Gui
	 * @param numberOf
	 * @param type
	 */
	public void initLabels(String numberOf, String type) {
		if (type.equals("guide"))
			firstBoxLabel.setText(("There are\n" + numberOf + "\nguides"));
		else
			secondBoxLabel.setText(("There are\n" + numberOf + "\nsubscriptions"));

	}
}
