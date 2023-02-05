package unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ReportsController;
import entities.ReportType;

class ClientVisitorReportTests {

	private ReportsController reportController;
	private Date date;
	private String parkName;
	private ReportType reportType;
	String result, expected;

	@BeforeEach
	void setUp() throws Exception {
		reportController = new ReportsController();
		ReportsController.setIreport(new StubReport());
		reportType = ReportType.Visits_Report;

	}

	private class StubReport implements IReport {
		HashMap<Integer, String> parkReportData;
		HashMap<String, HashMap<Integer, String>> hashMapDB;

		/**
		 * This constructor initalize the fake date base
		 */
		public StubReport() {
			hashMapDB = new HashMap<String, HashMap<Integer, String>>();
			parkReportData = new HashMap<Integer, String>();

			parkReportData = new HashMap<Integer, String>();
			parkReportData.put(1, "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
			parkReportData.put(2,
					"12 88 28 60 28 0 76 40 20 32 76 0 16 96 0 96 0 0 60 56 28 56 28 0 60 28 0 56 0 0 92 28 0 44 28 0");
			parkReportData.put(3, "1 2 3 4 5 6 1 23 2 8 9 2 3 2 4 3 2 2 1 1 1 5 32 6 12 7 23 8 12 5 32 6 12 5 23 7");
			hashMapDB.put("Haifa Park", parkReportData);
			parkReportData = new HashMap<Integer, String>();
			parkReportData.put(4, "1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 1 1 1 0 0 1");
			parkReportData.put(5, "1 2 7 4 7 6 7 7 0 8 7 0 7 7 7 0 7 0 7 5 7 5 7 6 7 7 7 8 7 5 7 6 7 5 7 7");
			parkReportData.put(6,
					"11 22 33 44 55 66 17 23 5 8 4 2 3 2 54 34 2 32 1 61 1 75 32 46 12 67 23 82 12 56 32 36 12 85 232 7");
			hashMapDB.put("Tel-Aviv Park", parkReportData);

			parkReportData = new HashMap<Integer, String>();
			parkReportData.put(7, "1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 1 1 1 0 0 1");
			parkReportData.put(8, "1 2 3 4 5 6 0 0 0 8 9 0 3 0 4 0 6 0 0 5 5 5 0 6 0 7 0 8 0 5 0 6 0 5 0 7");
			parkReportData.put(9, "1 2 3 4 5 6 1 23 2 8 9 2 3 2 4 3 2 2 1 1 1 5 32 6 12 7 23 8 12 5 32 6 12 5 23 7");
			hashMapDB.put("Safed Park", parkReportData);
		}

		/**
		 * This method searches for the report in the fake data base In case of lack of
		 * data the method returns String of zeros
		 */
		@Override
		public String getReportMessage(ArrayList<Object> msg) {
			String parkName = (String) msg.get(2);
			int month = ((Date) msg.get(1)).getMonth() + 1;

			HashMap<Integer, String> reportData = hashMapDB.get(parkName);

			if (reportData.get(month) == null)
				return "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
			return reportData.get(month);
		}

		/**
		 * This method adds a new report to the fake data base
		 */
		@Override
		public void addReportToDBMessage(ArrayList<Object> msg) {
			String parkName = (String) msg.get(3);
			int month = ((Date) msg.get(1)).getMonth() + 1;
			String stringToAdd = "";
			ArrayList<String> DataToAddToDB = (ArrayList<String>) msg.get(2);
			for (String s : DataToAddToDB)
				stringToAdd += s + " ";
			stringToAdd = stringToAdd.substring(0, stringToAdd.length() - 1);
			hashMapDB.get(parkName).put(month, stringToAdd);
		}

		/**
		 * This methods returns true if the report was found else the method returns
		 * false
		 */
		@Override
		public boolean checkReportIfExist(ArrayList<Object> msg) {
			String parkName = (String) msg.get(2);
			int month = ((Date) msg.get(1)).getMonth() + 1;
			if (hashMapDB.get(parkName).get(month) == null)
				return false;
			return true;
		}

	}

	/**
	 * This test checks if there aren't any order in this month
	 * 
	 */
	@Test
	void lackOfDataTest() {
		parkName = "Haifa Park";
		date = new Date(120, 8, 1);
		expected = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
		result = ReportsController.getReport(date, reportType, parkName);

		assertEquals(expected, result);

	}

	/**
	 * This test checks a month with details
	 */
	@Test
	void existDataInDBTest() {
		parkName = "Haifa Park";
		date = new Date(120, 2, 1);
		expected = "1 2 3 4 5 6 1 23 2 8 9 2 3 2 4 3 2 2 1 1 1 5 32 6 12 7 23 8 12 5 32 6 12 5 23 7";
		result = ReportsController.getReport(date, reportType, parkName);

		assertEquals(expected, result);

	}

	/**
	 * This test adds a new report to the fake data base if the report is already
	 * exists the test fails
	 */
	@Test
	void addReportDataToDBTest() {
		parkName = "Haifa Park";
		boolean condition = false;
		date = new Date(120, 4, 1);
		expected = "1 2 3 4 5 6 1 23 2 8 9 2 3 2 4 3 2 2 1 1 1 5 32 6 12 7 23 8 12 5 32 6 12 5 2 5";

		String[] str = expected.split(" ");

		if (ReportsController.CheckReportExistInDB(reportType, date, parkName))
			assertTrue(condition);

		ReportsController.addReportToDB(reportType, date, new ArrayList<>(Arrays.asList(str)), parkName);

		result = ReportsController.getReport(date, reportType, parkName);

		assertEquals(expected, result);

	}

	/**
	 * This test checks a month with details
	 */
	@Test
	void existDataInDBTest2() {
		parkName = "Tel-Aviv Park";
		date = new Date(120, 5, 1);
		expected = "11 22 33 44 55 66 17 23 5 8 4 2 3 2 54 34 2 32 1 61 1 75 32 46 12 67 23 82 12 56 32 36 12 85 232 7";
		result = ReportsController.getReport(date, reportType, parkName);

		assertEquals(expected, result);

	}

}
