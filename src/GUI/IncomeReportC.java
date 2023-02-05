package GUI;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.ParkMangerCC;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IncomeReportC implements Initializable{
	public static String ParkN;
	public static Date Rdate;
    @FXML
    private Label reportdate;

    @FXML
    private Label ParkName;

    @FXML
    private Label PlannedTravellerN;

    @FXML
    private Label PlannedSubscriberN;

    @FXML
    private Label PlannedGuideN;

    @FXML
    private Label UnPlannedTravelerN;

    @FXML
    private Label UnplannedGuideN;

    @FXML
    private Label UnPlannedSubscriberN;

    @FXML
    private Label TotalAmount;
    
    /**
     * this Function start the GUI 
     * @param primaryStage
     */
    public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/IncomeReport.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Report");
			primaryStage.show();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function get the income report from the parkManger Controller and set's the labels as the report values 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ArrayList<Double> report = ParkMangerCC.IncomeReport;
		ParkName.setText("GoNature - " + ParkN);
		reportdate.setText((Rdate.getMonth()+1) +"/"+(Rdate.getYear()+1900));
		PlannedTravellerN.setText(String.valueOf(report.get(0))+"₪");
		PlannedSubscriberN.setText(String.valueOf(report.get(1))+"₪");
		PlannedGuideN.setText(String.valueOf(report.get(2))+"₪");
		UnPlannedTravelerN.setText(String.valueOf(report.get(3))+"₪");
		UnPlannedSubscriberN.setText(String.valueOf(report.get(4))+"₪");
		UnplannedGuideN.setText(String.valueOf(report.get(5))+"₪");
		TotalAmount.setText(String.valueOf(report.get(6)) +"₪" );
		
	}

    
    
}
