package unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.ReportType;
import server.mysqlConnection;

public class ServerVisitorsReportTests {

	ArrayList<Object> parametersForMySql;
	private String parkName;
	private Date date;
	private String expected;
	private String result;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {
			mysqlConnection.connecttoDB();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		parametersForMySql = new ArrayList<Object>();
	}

	/**
	 * This test searches for the report in database and get from data base reoprt
	 * and check if its the expected one
	 * 
	 */
	@Test
	void getReportDataFromDBTest() {
		parkName = "Haifa Park";
		date = new Date(120, 0, 11);
		expected = "12 88 28 60 28 0 76 40 20 32 76 0 16 96 0 96 0 0 60 56 28 56 28 0 60 28 0 56 0 0 92 28 0 44 28 0";

		parametersForMySql = new ArrayList<Object>();
		parametersForMySql.add(date);
		parametersForMySql.add(parkName);

		ArrayList<Integer> arrayList = mysqlConnection.makeVisitReport(parametersForMySql);

		result = "";

		for (Integer num : arrayList)
			result += String.valueOf(num) + " ";
		result = result.substring(0, result.length() - 1);

		assertEquals(expected, result);

	}

	/**
	 * This test add to Data Base 'Table report' visits reoprt and check if the
	 * report was inserted correctly after insert the report we delete it handle
	 **************************************************************************************
	 ****************** Don't forget to delete the reoprt after the Test ******************
	 **************************************************************************************
	 */
	@Test
	void addReportDataToDBTest() {

		parkName = "Haifa Park";
		date = new Date(120, 1, 11);

		parametersForMySql.add(ReportType.Visits_Report.toString());
		parametersForMySql.add(date);
		parametersForMySql.add(parkName);

		if (mysqlConnection.IsReportExisted(parametersForMySql))
			assertTrue(false);

		parametersForMySql.remove(0);
		ArrayList<Integer> arrayList = mysqlConnection.makeVisitReport(parametersForMySql);
		result = "";

		ArrayList<String> arrayListString = new ArrayList<String>();

		for (Integer num : arrayList) {
			result += String.valueOf(num) + " ";
			arrayListString.add(String.valueOf(num));
		}

		expected = result;

		parametersForMySql.clear();
		parametersForMySql.add(ReportType.Visits_Report.toString());
		parametersForMySql.add(date);
		parametersForMySql.add(arrayListString);
		parametersForMySql.add(parkName);

		mysqlConnection.InsertReports(parametersForMySql);

		parametersForMySql.clear();
		parametersForMySql.add(ReportType.Visits_Report.toString());
		parametersForMySql.add(date);
		parametersForMySql.add(parkName);

		result = mysqlConnection.GetReport(parametersForMySql);
		assertEquals(expected, result);

	}

	/**
	 * This Test check if reoprt in march is none exist Report in march shouldn't
	 * exist
	 * 
	 */
	@Test
	void nonexistReportDataToDBTest() {
		parkName = "Haifa Park";
		date = new Date(120, 2, 11);

		parametersForMySql.add(ReportType.Visits_Report.toString());
		parametersForMySql.add(date);
		parametersForMySql.add(parkName);

		if (mysqlConnection.IsReportExisted(parametersForMySql))
			assertTrue(false);
		assertTrue(true);

	}

}
