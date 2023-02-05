package GUI;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.GoNatureClient;
import controllers.ParkMangerCC;
import entities.ParkManager;
import entities.ReportType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ParkMangerReportsGuiC implements Initializable {
	ParkManager ParkManager = (ParkManager) GoNatureClient.currentWorker;
	@FXML
	private ComboBox<String> ReportTypeCombo;

	@FXML
	private DatePicker ReportMonth;

	@FXML
	private Label message;

	@FXML
	private Button CreateReportBtn;

	/**
	 * this function start the GUI
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/ManagersReportsGui.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("GONATURE");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** this function Opened the match report to the ParkManger 
	 * it take the report date and report name from the EdditText 
	 * if the month of the wanted report didn't finish yet , it show error message 
	 * @param event
	 */
	@SuppressWarnings("deprecation")
	@FXML
	void CreateReport(MouseEvent event) {
		String reporttype = ReportTypeCombo.getValue();
		LocalDate d = ReportMonth.getValue();

		if (ReportMonth.getValue() == null) {
			message.setText("All Fields Are Required");
			message.setVisible(true);
			return;
		}

		if (!(d.compareTo(LocalDate.now()) < 0
				& (d.getYear() < LocalDate.now().getYear() | (d.getYear() == LocalDate.now().getYear()
						& d.getMonth().getValue() < LocalDate.now().getMonth().getValue())))) {
			message.setText("It`s too early for this report");
			message.setVisible(true);
			return;
		}
		Date date = new Date(ReportMonth.getValue().getYear() - 1900, ReportMonth.getValue().getMonthValue() - 1 , ReportMonth.getValue().getDayOfMonth());
		switch (reporttype) {

		case "Income Report":
			message.setVisible(false);
			ParkMangerCC.createMonthlyIncomeReport(date, ParkManager.getPark().getName());
			IncomeReportC incomeReport = new IncomeReportC();
			IncomeReportC.ParkN = ParkManager.getPark().getName();
			IncomeReportC.Rdate = date;
			incomeReport.start(new Stage());
			break;

		case "Number Of visitors Report":
			message.setVisible(false);
			ParkMangerCC.createTotalVisitorReport(date, ParkManager.getPark().getName());
			TotalVisitorReportC totalVisitorReportC = new TotalVisitorReportC();
			TotalVisitorReportC.ParkName = ParkManager.getPark().getName();
			TotalVisitorReportC.reportDate = date;
			totalVisitorReportC.start(new Stage());
			break;

		case "Uses Report":
			message.setVisible(false);
			ParkMangerCC.createUsageReport(date, ParkManager.getPark().getName());
			UsageReportC usageReport = new UsageReportC();
			usageReport.ParkName = ParkManager.getPark().getName();
			usageReport.Rdate = date;
			usageReport.start(new Stage());
			break;

		default:
			break;
		}

	}

	/**
	 * this function prepare the combox before load the GUI 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ArrayList<String> ReportsNames = new ArrayList<String>();
		ReportsNames.add(ReportType.Earnings_report.toString());
		ReportsNames.add(ReportType.number_of_visitors_report.toString());
		ReportsNames.add(ReportType.Uses_Report.toString());
		ReportTypeCombo.setItems(FXCollections.observableArrayList(ReportsNames));
		ReportTypeCombo.getSelectionModel().select(0);
		message.setVisible(false);

	}

}
