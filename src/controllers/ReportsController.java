package controllers;

import java.sql.Date;
import java.util.ArrayList;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.ManagerRequest;
import entities.ReportType;
import unitTests.IReport;
import unitTests.ReportManager;

public class ReportsController {
	private static IReport ireport = new ReportManager();

	/**
	 * this function gets report from the database and checks if its exist already
	 * 
	 * @param month
	 * @param year
	 * @param reportType
	 * @return report data as a String
	 */
	public static String getReport(Date date, ReportType reportType, String ParkName) {

		ArrayList<Object> msg = new ArrayList<Object>();

		msg.add(reportType.toString());
		msg.add(date);
		msg.add(ParkName);

		return ireport.getReportMessage(msg);

	}

	public static IReport getIreport() {
		return ireport;
	}

	public static void setIreport(IReport ireport) {
		ReportsController.ireport = ireport;
	}

	/**
	 * this function adds report data to the database
	 * 
	 * @param reportType
	 * @param month
	 * @param year
	 * @param parameterStrings
	 */
	public static void addReportToDB(ReportType reportType, Date date, ArrayList<String> parameterStrings,
			String ParkName) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add(reportType.toString());
		msg.add(date);
		msg.add(parameterStrings);
		msg.add(ParkName);

		ireport.addReportToDBMessage(msg);

	}

	/**
	 * this function check if the received report with the specific month and year
	 * is exist in the database or not
	 * 
	 * @param reportType
	 * @param month
	 * @param year
	 * @return boolean number true/false if it exist or not
	 */
	public static boolean CheckReportExistInDB(ReportType reportType, Date date, String ParkName) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add(reportType.toString());
		msg.add(date);
		msg.add(ParkName);

		boolean result = ireport.checkReportIfExist(msg);
		return result;
	}

}