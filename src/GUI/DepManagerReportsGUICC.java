package GUI;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Client.GoNatureClient;
import controllers.DepartmentMangerCC;
import controllers.ParkMangerCC;
import controllers.ReportsController;
import entities.DepartmentManager;
import entities.Park;
import entities.ReportType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DepManagerReportsGUICC implements Initializable {
	DepartmentManager departmentManager = (DepartmentManager) GoNatureClient.currentWorker;
	String ParkName;
	@FXML
	private Button CreateReportBtn;

	@FXML
	private ComboBox<String> ReportTypeCombo;

	@FXML
	private DatePicker ReportDatePicker;

	@FXML
	private ComboBox<String> ParkComboBox;

	@FXML
	private Label message;

	/**
	 * this function load the report GUI matching to the selected report type in the ComboBox
	 * it check if the dates is correct and there is possibility to get the report 
	 * if the report is not created by the department manger it take the report from the DB by controller 
	 * if the report doesn't exist in the DB that mean it is too early to create it 
	 * or the report is not created yet by the park manger so the department manager can't see it yet 
	 * @param event
	 */
	@SuppressWarnings("deprecation")
	@FXML
	void CreateReportBtnClicked(ActionEvent event) {

		LocalDate d = ReportDatePicker.getValue();

		if (ReportDatePicker.getValue() == null) {
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

		String reporttype = ReportTypeCombo.getValue();
		String str;
		ParkName = ParkComboBox.getValue();
		Date date = new Date(ReportDatePicker.getValue().getYear() - 1900,
				ReportDatePicker.getValue().getMonthValue() - 1, ReportDatePicker.getValue().getDayOfMonth());
		message.setVisible(false);

		switch (reporttype) {
		case "Cancelled Order Report":
			message.setVisible(false);
			DepartmentMangerCC.CreateCancelReport(date, ParkName);
			CancelReportC cancelReportC = new CancelReportC();
			CancelReportC.ParkName = ParkName;
			CancelReportC.reportDate = date;
			cancelReportC.start(new Stage());
			break;

		case "Visits Report":
			message.setVisible(false);
			DepartmentMangerCC.createVisitReport(date, ParkName);
			VisitReportC visitReportC = new VisitReportC();
			visitReportC.ParkName = ParkName;
			visitReportC.reportDate = date;
			visitReportC.start(new Stage());
			break;
		case "Income Report":
			if (ReportsController.CheckReportExistInDB(ReportType.Earnings_report, date, ParkName)) {
				ParkMangerCC.createMonthlyIncomeReport(date, ParkName);
				IncomeReportC incomeReport = new IncomeReportC();
				IncomeReportC.ParkN = ParkName;
				IncomeReportC.Rdate = date;
				incomeReport.start(new Stage());
			} else
			{
				message.setText("Report is not ready yet!");
			message.setVisible(true);
			}
			break;

		case "Number Of visitors Report":
			if (ReportsController.CheckReportExistInDB(ReportType.number_of_visitors_report, date, ParkName)) {
				ParkMangerCC.createTotalVisitorReport(date, ParkName);
				TotalVisitorReportC totalVisitorReportC = new TotalVisitorReportC();
				TotalVisitorReportC.ParkName = ParkName;
				TotalVisitorReportC.reportDate = date;
				totalVisitorReportC.start(new Stage());
			} else
			{
				message.setText("Report is not ready yet!");
			message.setVisible(true);
			}
			break;

		case "Uses Report":
			if (ReportsController.CheckReportExistInDB(ReportType.Uses_Report, date, ParkName)) {
				ParkMangerCC.createUsageReport(date, ParkName);
				UsageReportC usageReport = new UsageReportC();
				usageReport.ParkName = ParkName;
				UsageReportC.Rdate = date;
				usageReport.start(new Stage());
			} else
			{
				message.setText("Report is not ready yet!");
			message.setVisible(true);
			}
			break;

		default:

			break;
		}

	}

	/**
	 * this function prepare the ComboBox and the other labels 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DepartmentManager depManager = (DepartmentManager) GoNatureClient.currentWorker;
		ArrayList<String> parkNames = new ArrayList<String>();
		for (Park p : depManager.getParks())
			parkNames.add(p.getName());
		ParkComboBox.setItems(FXCollections.observableArrayList(parkNames));
		ParkComboBox.getSelectionModel().select(0);

		ArrayList<String> ReportsNames = new ArrayList<String>();

		ReportsNames.add(ReportType.Visits_Report.toString());
		ReportsNames.add(ReportType.cancelled_order_report.toString());
		ReportsNames.add(ReportType.Earnings_report.toString());
		ReportsNames.add(ReportType.number_of_visitors_report.toString());
		ReportsNames.add(ReportType.Uses_Report.toString());

		ReportTypeCombo.setItems(FXCollections.observableArrayList(ReportsNames));
		ReportTypeCombo.getSelectionModel().select(0);

	}

}
