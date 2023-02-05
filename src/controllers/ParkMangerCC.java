package controllers;

import java.awt.Desktop;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.javafx.binding.StringFormatter;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.ParameterChangeRequest;
import entities.ReportType;
import entities.Sale;
import entities.reportHelper;

public class ParkMangerCC {
	public static ArrayList<String> totalVisitorReport;
	public static ArrayList<Double> IncomeReport;
	public static ArrayList<reportHelper> usageReport;

	// total visit report in the create in return array list of long , if we get
	// report from DB it return array list string
	/**
	 * this function creats a total visitor report and saves ArrayList of long int
	 * in totalVisitorReport arraylist , about the number of visitors sorted by type
	 * 
	 * @param date
	 * @param ParkName ArrayList of long int , about the number of visitors sorted
	 *                 by type
	 */
	@SuppressWarnings("unchecked")
	public static void createTotalVisitorReport(Date date, String ParkName) {
		ReportType reportType = ReportType.number_of_visitors_report;

		ArrayList<Long> result = new ArrayList<Long>();
		if (!ReportsController.CheckReportExistInDB(reportType, date, ParkName)) {

			ArrayList<Object> msg = new ArrayList<Object>();
			msg.add(reportType);
			msg.add(date);
			msg.add(ParkName);
			ClientMessage msg2 = new ClientMessage("MakeTotalVisitorsReport", msg, 1);
			ClientUI.chat.accept(msg2);
			ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
			result = (ArrayList<Long>) srMessage.getData();

			// Add To database after create
			ArrayList<String> parameter = new ArrayList<String>();
			for (int i = 0; i < result.size(); i++)
				parameter.add(result.get(i).toString());

			totalVisitorReport = parameter;
			ReportsController.addReportToDB(reportType, date, parameter, ParkName);
		} else {// get report from DataBase

			String str = ReportsController.getReport(date, reportType, ParkName);
			System.out.println(str);
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(str.split(" ")));
			System.out.println(list.toString());
			totalVisitorReport = list;

		}

	}

	/**
	 * this function creats a total usage report and saves the data in static
	 * arraylist called usageReport
	 * 
	 * @param date
	 * @param ParkName
	 */
	@SuppressWarnings("unchecked")
	public static void createUsageReport(Date date, String Parkname) {

		ArrayList<reportHelper> report = new ArrayList<reportHelper>();
		ReportType reportType = ReportType.Uses_Report;
		if (!ReportsController.CheckReportExistInDB(reportType, date, Parkname)) {
			ArrayList<Object> msg = new ArrayList<Object>();
			msg.add(reportType.toString());
			msg.add(date);
			msg.add(Parkname);
			ClientMessage msg2 = new ClientMessage("createUsageReport", msg, 2);
			ClientUI.chat.accept(msg2);
			ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());

			report = (ArrayList<reportHelper>) srMessage.getData();
			usageReport = report;

			ArrayList<String> data = new ArrayList<>();
			for (reportHelper tempHelper : report) {

				data.add(String.valueOf(tempHelper.getDate().getTime()));
				data.add(tempHelper.getRange());
			}
			ReportsController.addReportToDB(reportType, date, data, Parkname);

		} else {

			String str = ReportsController.getReport(date, reportType, Parkname);
			System.out.println(str);
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(str.split(" ")));
			ArrayList<reportHelper> reportHelpers = new ArrayList<>();
			for (int i = 0; i < list.size(); i += 2) {
				reportHelpers.add(new reportHelper(new Date(Long.valueOf(list.get(i))), list.get(i + 1)));
			}
			usageReport = reportHelpers;

		}

	}

	/**
	 * this function create a income report in the received month , if it exist it
	 * got it from DB. and saving the ArrayList of string holding the report in
	 * IncomeReport
	 * 
	 * @param date
	 * @param ParkName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void createMonthlyIncomeReport(Date date, String ParkName) {
		ReportType reportType = ReportType.Earnings_report;
		// ArrayList<String> result = new ArrayList<String>();

		if (!ReportsController.CheckReportExistInDB(reportType, date, ParkName)) {

			ArrayList<Object> msg = new ArrayList<Object>();
			msg.add(reportType.toString());
			msg.add(date);
			msg.add(ParkName);

			ClientMessage msg2 = new ClientMessage("MakeMonethlyIncomeReport", msg, 2);
			ClientUI.chat.accept(msg2);

			ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
			ArrayList<Double> report = (ArrayList<Double>) srMessage.getData();
			IncomeReport = report;
			ArrayList<String> data = new ArrayList<>();
			for (Double temp : report) {
				data.add(String.valueOf(temp));
			}
			ReportsController.addReportToDB(reportType, date, data, ParkName);

		} else {
			String str = ReportsController.getReport(date, reportType, ParkName);
			System.out.println(str);
			ArrayList<String> list = new ArrayList(Arrays.asList(str.split(" ")));
			ArrayList<Double> report = new ArrayList<>();
			for (String temp : list) {
				report.add(Double.valueOf(temp));
			}
			IncomeReport = report;
		}

	}

	/**
	 * this function insert new sale request in the sql and make it status waiting
	 * 
	 * @param sale
	 * @param parkName
	 */
	public static boolean setSale(Sale sale, String parkName) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add(sale.getStartDate());
		msg.add(sale.getEndDate());
		msg.add(sale.getSalePercent());
		msg.add(sale.getSaleName());
		msg.add(parkName);
		msg.add("waiting");
		msg.add(sale.getSendDate());

		ClientMessage msg2 = new ClientMessage("InsertSale", msg, 6);
		ClientUI.chat.accept(msg2);
		
		ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
		return (boolean) srMessage.getData();

	}

	/**
	 * this function insert new parameter request in the sql and make it status
	 * waiting
	 * 
	 * @param parameter
	 * @param startDate
	 * @param endDate
	 */
	public static void setParameter(ParameterChangeRequest parameter, Date startDate) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add(startDate);
		msg.add(parameter.getType().toString());
		msg.add(parameter.getPark().getName());
		msg.add("waiting");
		msg.add(parameter.getNewValue());
		msg.add(parameter.getSendDate());
		ClientMessage msg2 = new ClientMessage("InsertParameter", msg, 6);
		ClientUI.chat.accept(msg2);
	}

	/**
	 * method to get the paramater request by the park name
	 * 
	 * @param ParkName
	 * @return arraylist contains the requests
	 */
	public static ArrayList<Object> getParameter(String ParkName) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add(ParkName);
		ClientMessage msg2 = new ClientMessage("getParkMangerParameters", msg, 1);
		ClientUI.chat.accept(msg2);
		ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
		return (ArrayList<Object>) srMessage.getData();
	}

}