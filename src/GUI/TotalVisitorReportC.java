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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TotalVisitorReportC implements Initializable {

	public static String ParkName;
	public static Date reportDate;

	@FXML
	private Label Month;

	@FXML
	private Label planedVisitorN;

	@FXML
	private Label PlannedSubscriberN;

	@FXML
	private Label PlannedGuideN;

	@FXML
	private Label UnPlannedTravelerN;

	@FXML
	private Label UnPlannedSubscriberN;

	@FXML
	private Label UnPlannedGuideN;

	@FXML
	private Label TotalNumber;

	@FXML
	private BarChart<String, Integer> barChart;

	
	/**
	 * this function prepare the chart of the report 
	 * the function take the data from the DataBase and divide the number of the visitor in days
	 * in preset how much visitors was in each day for each type of visitors 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int sum = 0;
		ArrayList<String> report = ParkMangerCC.totalVisitorReport;
		Month.setText((reportDate.getMonth() + 1) + "/" + (reportDate.getYear() + 1900));
		planedVisitorN.setText(report.get(0));
		PlannedSubscriberN.setText(report.get(1));
		PlannedGuideN.setText(report.get(2));
		UnPlannedTravelerN.setText(report.get(3));
		UnPlannedSubscriberN.setText(report.get(4));
		UnPlannedGuideN.setText(report.get(5));
		for (int i = 0; i < 6; i++) {
			sum += Integer.valueOf(report.get(i));
		}
		TotalNumber.setText(String.valueOf(sum));
		barChart.setStyle("-fx-font-size: " + 20 + "px;");
		int i = 6;
		XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
		series1.setName("Planned Traveller");
		series1.getData().add(new XYChart.Data<>("Sunday", Integer.valueOf(report.get(i++))));
		series1.getData().add(new XYChart.Data<>("Monday", Integer.valueOf(report.get(i++))));
		series1.getData().add(new XYChart.Data<>("Tuesday", Integer.valueOf(report.get(i++))));
		series1.getData().add(new XYChart.Data<>("Wednesday", Integer.valueOf(report.get(i++))));
		series1.getData().add(new XYChart.Data<>("Thursday", Integer.valueOf(report.get(i++))));
		series1.getData().add(new XYChart.Data<>("Friday", Integer.valueOf(report.get(i++))));
		series1.getData().add(new XYChart.Data<>("Saturday", Integer.valueOf(report.get(i++))));

		XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
		series2.setName("Planned Subscriber");
		series2.getData().add(new XYChart.Data<>("Sunday", Integer.valueOf(report.get(i++))));
		series2.getData().add(new XYChart.Data<>("Monday", Integer.valueOf(report.get(i++))));
		series2.getData().add(new XYChart.Data<>("Tuesday", Integer.valueOf(report.get(i++))));
		series2.getData().add(new XYChart.Data<>("Wednesday", Integer.valueOf(report.get(i++))));
		series2.getData().add(new XYChart.Data<>("Thursday", Integer.valueOf(report.get(i++))));
		series2.getData().add(new XYChart.Data<>("Friday", Integer.valueOf(report.get(i++))));
		series2.getData().add(new XYChart.Data<>("Saturday", Integer.valueOf(report.get(i++))));

		XYChart.Series<String, Integer> series3 = new XYChart.Series<>();
		series3.setName("Planned Guide");
		series3.getData().add(new XYChart.Data<>("Sunday", Integer.valueOf(report.get(i++))));
		series3.getData().add(new XYChart.Data<>("Monday", Integer.valueOf(report.get(i++))));
		series3.getData().add(new XYChart.Data<>("Tuesday", Integer.valueOf(report.get(i++))));
		series3.getData().add(new XYChart.Data<>("Wednesday", Integer.valueOf(report.get(i++))));
		series3.getData().add(new XYChart.Data<>("Thursday", Integer.valueOf(report.get(i++))));
		series3.getData().add(new XYChart.Data<>("Friday", Integer.valueOf(report.get(i++))));
		series3.getData().add(new XYChart.Data<>("Saturday", Integer.valueOf(report.get(i++))));

		XYChart.Series<String, Integer> series4 = new XYChart.Series<>();
		series4.setName("Unplanned Traveller");
		series4.getData().add(new XYChart.Data<>("Sunday", Integer.valueOf(report.get(i++))));
		series4.getData().add(new XYChart.Data<>("Monday", Integer.valueOf(report.get(i++))));
		series4.getData().add(new XYChart.Data<>("Tuesday", Integer.valueOf(report.get(i++))));
		series4.getData().add(new XYChart.Data<>("Wednesday", Integer.valueOf(report.get(i++))));
		series4.getData().add(new XYChart.Data<>("Thursday", Integer.valueOf(report.get(i++))));
		series4.getData().add(new XYChart.Data<>("Friday", Integer.valueOf(report.get(i++))));
		series4.getData().add(new XYChart.Data<>("Saturday", Integer.valueOf(report.get(i++))));

		XYChart.Series<String, Integer> series5 = new XYChart.Series<>();
		series5.setName("Unplanned Subscriber");
		series5.getData().add(new XYChart.Data<>("Sunday", Integer.valueOf(report.get(i++))));
		series5.getData().add(new XYChart.Data<>("Monday", Integer.valueOf(report.get(i++))));
		series5.getData().add(new XYChart.Data<>("Tuesday", Integer.valueOf(report.get(i++))));
		series5.getData().add(new XYChart.Data<>("Wednesday", Integer.valueOf(report.get(i++))));
		series5.getData().add(new XYChart.Data<>("Thursday", Integer.valueOf(report.get(i++))));
		series5.getData().add(new XYChart.Data<>("Friday", Integer.valueOf(report.get(i++))));
		series5.getData().add(new XYChart.Data<>("Saturday", Integer.valueOf(report.get(i++))));

		XYChart.Series<String, Integer> series6 = new XYChart.Series<>();
		series6.setName("Unplanned Guide");
		series6.getData().add(new XYChart.Data<>("Sunday", Integer.valueOf(report.get(i++))));
		series6.getData().add(new XYChart.Data<>("Monday", Integer.valueOf(report.get(i++))));
		series6.getData().add(new XYChart.Data<>("Tuesday", Integer.valueOf(report.get(i++))));
		series6.getData().add(new XYChart.Data<>("Wednesday", Integer.valueOf(report.get(i++))));
		series6.getData().add(new XYChart.Data<>("Thursday", Integer.valueOf(report.get(i++))));
		series6.getData().add(new XYChart.Data<>("Friday", Integer.valueOf(report.get(i++))));
		series6.getData().add(new XYChart.Data<>("Saturday", Integer.valueOf(report.get(i++))));

		barChart.getData().addAll(series1, series2, series3, series4, series5, series6);
		barChart.setBarGap(3);
		barChart.setCategoryGap(30);
		barChart.setTitle("Number Of Visitors By Day Of Week");
		barChart.getYAxis().setLabel("Number of Visitors");
		barChart.getXAxis().setLabel("Day Of Week");

	}

	/**
	 * this Function start the GUI 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/TotalVisitorsReport.fxml"));
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
