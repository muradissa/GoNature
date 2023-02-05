package GUI;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import controllers.ParkMangerCC;
import entities.reportHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UsageReportC implements Initializable {


	public static String ParkName;

	public static Date Rdate;
	@FXML
	private Label reportD;

	@FXML
	private Label parkN;

	@FXML
	private TableColumn<reportHelper, Date> date;

	@FXML
	private TableColumn<reportHelper, String> timespan;
	
	@FXML
	private TableView<reportHelper> reporttable;

	/**
	 *this function take the data from the database and present in the report 
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parkN.setText("GoNature - " + ParkName);
		reportD.setText((Rdate.getMonth()+1) + "\\" + (Rdate.getYear()+1900));
		date.setCellValueFactory(new PropertyValueFactory<reportHelper, Date>("date"));
		timespan.setCellValueFactory(new PropertyValueFactory<reportHelper, String>("range"));
		reporttable.setItems(FXCollections.observableArrayList(ParkMangerCC.usageReport));
	}

	
	/**
	 * this function start the Gui
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/UsageReport.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Report");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
