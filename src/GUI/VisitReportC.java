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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VisitReportC implements Initializable {
	public static String ParkName;
	public static Date reportDate;
	@FXML
	private Label RDate;

	@FXML
	private Label parkN;

	@FXML
	private Label PlannedTraveler;

	@FXML
	private Label PlannedSubscriber;

	@FXML
	private Label PlannedGuide;

	@FXML
	private Label UnplannedTraveler;

	@FXML
	private Label UnPlannedScbscriber;

	@FXML
	private Label UnplannedGuide;

	@FXML
	private BarChart<String, Integer> IntranceTimeChart;

	@FXML
	private BarChart<String, Integer> StayTimeChart;

	/**
	 * This function prepare the visit report before loading the report THE function
	 * takes the data from the DB via the report controller it also prepare chart
	 * that present the number of each type of visitors were in range of hours in
	 * the park and it prepare chart that present number of each type of visitors
	 * that stay x time in the park
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parkN.setText("GoNature - " + ParkName);
		RDate.setText((reportDate.getMonth() + 1) + "/" + (reportDate.getYear() + 1900));
		ArrayList<Integer> report = DepartmentMangerCC.visitReport;
		PlannedTraveler.setText("\t" + report.get(0) + "\t\t\t" + report.get(1) + "\t\t\t" + report.get(2));
		PlannedSubscriber.setText("\t" + report.get(6) + "\t\t\t" + report.get(7) + "\t\t\t" + report.get(8));
		PlannedGuide.setText("\t" + report.get(12) + "\t\t\t" + report.get(13) + "\t\t\t" + report.get(14));
		UnplannedTraveler.setText("\t" + report.get(18) + "\t\t\t" + report.get(19) + "\t\t\t" + report.get(20));
		UnPlannedScbscriber.setText("\t" + report.get(24) + "\t\t\t" + report.get(25) + "\t\t\t" + report.get(26));
		UnplannedGuide.setText("\t" + report.get(30) + "\t\t\t" + report.get(31) + "\t\t\t" + report.get(32));


		XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
		series1.setName("Planned traveller");
		series1.getData().add(new XYChart.Data<>("8:00-12:00", report.get(0)));
		series1.getData().add(new XYChart.Data<>("12:00-16:00", report.get(1)));
		series1.getData().add(new XYChart.Data<>("16:00-19:00", report.get(2)));
		XYChart.Series<String, Integer> series11 = new XYChart.Series<>();
		series11.setName("Planned traveller");
		series11.getData().add(new XYChart.Data<>("Stay Time <= 2", report.get(3)));
		series11.getData().add(new XYChart.Data<>("2 < Stay Time <= 3", report.get(4)));
		series11.getData().add(new XYChart.Data<>("Stay Time > 3", report.get(5)));

		XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
		series2.setName("Planned Subscriber");
		series2.getData().add(new XYChart.Data<>("8:00-12:00", report.get(6)));
		series2.getData().add(new XYChart.Data<>("12:00-16:00", report.get(7)));
		series2.getData().add(new XYChart.Data<>("16:00-19:00", report.get(8)));
		XYChart.Series<String, Integer> series22 = new XYChart.Series<>();
		series22.setName("Planned Subscriber");
		series22.getData().add(new XYChart.Data<>("Stay Time <= 2", report.get(9)));
		series22.getData().add(new XYChart.Data<>("2 < Stay Time <= 3", report.get(10)));
		series22.getData().add(new XYChart.Data<>("Stay Time > 3", report.get(11)));

		XYChart.Series<String, Integer> series3 = new XYChart.Series<>();
		series3.setName("Planned Guide");
		series3.getData().add(new XYChart.Data<>("8:00-12:00", report.get(12)));
		series3.getData().add(new XYChart.Data<>("12:00-16:00", report.get(13)));
		series3.getData().add(new XYChart.Data<>("16:00-19:00", report.get(14)));
		XYChart.Series<String, Integer> series33 = new XYChart.Series<>();
		series33.setName("Planned Guide");
		series33.getData().add(new XYChart.Data<>("Stay Time <= 2", report.get(15)));
		series33.getData().add(new XYChart.Data<>("2 < Stay Time <= 3", report.get(16)));
		series33.getData().add(new XYChart.Data<>("Stay Time > 3", report.get(17)));

		XYChart.Series<String, Integer> series4 = new XYChart.Series<>();
		series4.setName("Unplanned Traveller");
		series4.getData().add(new XYChart.Data<>("8:00-12:00", report.get(18)));
		series4.getData().add(new XYChart.Data<>("12:00-16:00", report.get(19)));
		series4.getData().add(new XYChart.Data<>("16:00-19:00", report.get(20)));
		XYChart.Series<String, Integer> series44 = new XYChart.Series<>();
		series44.setName("Unplanned Traveller");
		series44.getData().add(new XYChart.Data<>("Stay Time <= 2", report.get(21)));
		series44.getData().add(new XYChart.Data<>("2 < Stay Time <= 3", report.get(22)));
		series44.getData().add(new XYChart.Data<>("Stay Time > 3", report.get(23)));

		XYChart.Series<String, Integer> series5 = new XYChart.Series<>();
		series5.setName("Unplanned Subscriber");
		series5.getData().add(new XYChart.Data<>("8:00-12:00", report.get(24)));
		series5.getData().add(new XYChart.Data<>("12:00-16:00", report.get(25)));
		series5.getData().add(new XYChart.Data<>("16:00-19:00", report.get(26)));
		XYChart.Series<String, Integer> series55 = new XYChart.Series<>();
		series55.setName("Unplanned Subscriber");
		series55.getData().add(new XYChart.Data<>("Stay Time <= 2", report.get(27)));
		series55.getData().add(new XYChart.Data<>("2 < Stay Time <= 3", report.get(28)));
		series55.getData().add(new XYChart.Data<>("Stay Time > 3", report.get(29)));

		XYChart.Series<String, Integer> series6 = new XYChart.Series<>();
		series6.setName("Unplanned Guide");
		series6.getData().add(new XYChart.Data<>("8:00-12:00", report.get(30)));
		series6.getData().add(new XYChart.Data<>("12:00-16:00", report.get(31)));
		series6.getData().add(new XYChart.Data<>("16:00-19:00", report.get(32)));
		XYChart.Series<String, Integer> series66 = new XYChart.Series<>();
		series66.setName("Unplanned Guide");
		series66.getData().add(new XYChart.Data<>("Stay Time <= 2", report.get(33)));
		series66.getData().add(new XYChart.Data<>("2 < Stay Time <= 3", report.get(34)));
		series66.getData().add(new XYChart.Data<>("Stay Time > 3", report.get(35)));

		IntranceTimeChart.getData().addAll(series1, series2, series3, series4, series5, series6);
		IntranceTimeChart.setBarGap(3);
		IntranceTimeChart.setCategoryGap(30);
		IntranceTimeChart.setTitle("Entrance Statistic chart");
		IntranceTimeChart.getYAxis().setLabel("Number of Visitors");
		IntranceTimeChart.getXAxis().setLabel("Ranges (Hour)");

		StayTimeChart.getData().addAll(series11, series22, series33, series44, series55, series66);
		StayTimeChart.setBarGap(3);
		StayTimeChart.setCategoryGap(30);
		StayTimeChart.setTitle("stay time Statistic chart");
		StayTimeChart.getYAxis().setLabel("Number of Visitors");
		StayTimeChart.getXAxis().setLabel("Ranges (Hour)");

	}

	/**
	 * this function start the Gui
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/visitReport.fxml"));
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
