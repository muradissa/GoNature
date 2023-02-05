package GUI;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.DepartmentMangerCC;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CancelReportC implements Initializable{


	public static String ParkName; 
	public static Date reportDate ;

    @FXML
    private Label rMonth;

    @FXML
    private Label pName;

    @FXML
    private Label NoResponseOrderNumber;

    @FXML
    private Label CancelOrderN;

    @FXML
    private Label TotalN;
    
	/**
	 * this function prepare the cancel report before loading the GUI
	 * it also takes the data from the Department manger controller 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ArrayList<Integer> report = DepartmentMangerCC.cancelReport;
		rMonth.setText((reportDate.getMonth()+1)+"/"+(reportDate.getYear()+1900));
		pName.setText("GoNature - "+ParkName);
		NoResponseOrderNumber.setText(String.valueOf(report.get(0)));
		CancelOrderN.setText(String.valueOf(report.get(1)));
		TotalN.setText(String.valueOf(report.get(0)+report.get(1)));
	}
	
	  public void start(Stage primaryStage) {
			try {	
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/GUI/CancellationReport.fxml"));
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
