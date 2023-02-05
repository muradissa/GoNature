package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.ManagerRequest;
import entities.ParameterChangeRequest;
import entities.ReportType;
import entities.Sale;

public class DepartmentMangerCC {

	public static ArrayList<Integer> cancelReport;
	public static ArrayList<Integer> visitReport;

	/**
	 * this function creates a cancel_report , first it check if it already exist in
	 * the database , if yes it get it , else it will create a new report and will
	 * add it to the database
	 * 
	 * @param date
	 * @param ParkName
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void CreateCancelReport(Date date, String ParkName) {

		ArrayList<String> result = new ArrayList<String>();
		ReportType reportType = ReportType.cancelled_order_report;

		// check if the repost is exist
		if (!ReportsController.CheckReportExistInDB(ReportType.cancelled_order_report, date, ParkName)) {

			ArrayList<Object> msg = new ArrayList<Object>();
			msg.add(reportType.toString());
			msg.add(date);
			msg.add(ParkName);
			ClientMessage msg2 = new ClientMessage("MakeCancelReport", msg, 2);
			ClientUI.chat.accept(msg2);

			ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
			@SuppressWarnings("unchecked")
			ArrayList<Integer> array = (ArrayList<Integer>) srMessage.getData();
			cancelReport = array;
			result.add(String.valueOf(array.get(0)));
			result.add(String.valueOf(array.get(1)));

			// insert the report to DB
			ReportsController.addReportToDB(ReportType.cancelled_order_report, date, result, ParkName);

		} else {

			String str = ReportsController.getReport(date, reportType, ParkName);
			System.out.println(str);
			ArrayList<String> list = new ArrayList(Arrays.asList(str.split(" ")));
			ArrayList<Integer> report = new ArrayList<>();
			report.add(Integer.valueOf(list.get(0)));
			report.add(Integer.valueOf(list.get(1)));
			cancelReport = report;
		}

	}

	/**
	 * this function creates a visit_report , first it check if it already exist in
	 * the database , if yes it get it , else it will create a new report and will
	 * add it to the database
	 * 
	 * 
	 * @param date
	 * @param parkName
	 */
	public static void createVisitReport(Date date, String parkName) {

		if (!ReportsController.CheckReportExistInDB(ReportType.Visits_Report, date, parkName)) {

			ArrayList<Object> msg = new ArrayList<Object>();
			msg.add(date);
			msg.add(parkName);
			ClientMessage msg2 = new ClientMessage("makeVisitReport", msg, 3);
			ClientUI.chat.accept(msg2);

			ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
			ArrayList<Integer> array = (ArrayList<Integer>) srMessage.getData();
			visitReport = array;
			ArrayList<String> data = new ArrayList<>();
			for (Integer num : array) {
				data.add(String.valueOf(num));
			}
			ReportsController.addReportToDB(ReportType.Visits_Report, date, data, parkName);

		} else {

			String str = ReportsController.getReport(date, ReportType.Visits_Report, parkName);
			System.out.println(str);
			ArrayList<String> list = new ArrayList(Arrays.asList(str.split(" ")));
			ArrayList<Integer> report = new ArrayList<>();
			for (String num : list) {
				report.add(Integer.valueOf(num));
			}
			visitReport = report;
		}

	}

	/**
	 * this function confirm sale by update sale status to confirm
	 * 
	 * @param sale
	 * @param parkName
	 */
	public static void confirmSale(Sale sale, String parkName) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add("confirm"); // new status
		msg.add(sale.getSaleName());
		msg.add(parkName);
		msg.add(sale.getStartDate());
		msg.add("waiting"); // old status
		msg.add(sale.getSendDate());
		ClientMessage msg2 = new ClientMessage("UpdateSaleStatus", msg, 6);
		ClientUI.chat.accept(msg2);
	}

	/**
	 * this function unconfirm sale by update sale status to Unconfirm
	 * 
	 * @param sale
	 * @param parkName
	 */
	public static void UnconfirmSale(Sale sale, String parkName) {
		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add("unconfirmed"); // new status
		msg.add(sale.getSaleName());
		msg.add(parkName);
		msg.add(sale.getStartDate());
		msg.add("waiting"); // old status
		msg.add(sale.getSendDate());
		ClientMessage msg2 = new ClientMessage("UpdateSaleStatus", msg, 6);
		ClientUI.chat.accept(msg2);

	}

	/**
	 * this function confirm parameter by update sale status to confirm and updating
	 * the parks data in the database
	 * 
	 * @param parameter
	 * @param startDate
	 * @param value
	 */
	public static void confirmParameter(ParameterChangeRequest parameter, Date startDate, Object value) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add("confirmed"); // new status
		msg.add(startDate);
		msg.add(parameter.getType().toString());
		msg.add(parameter.getPark().getName());
		msg.add("waiting"); // old status
		msg.add(value); // old status
		ClientMessage msg2 = new ClientMessage("UpdateParameter", msg, 6);
		ClientUI.chat.accept(msg2);

	}

	/**
	 * this function unconfirm parameter by update sale status to Unconfirm
	 * 
	 * @param parameter
	 * @param startDate
	 * @param value
	 */
	public static void UnconfirmParameter(ParameterChangeRequest parameter, Date startDate, Object value) {

		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add("unconfirmed"); // new status
		msg.add(startDate);
		msg.add(parameter.getType().toString());
		msg.add(parameter.getPark().getName());
		msg.add("waiting"); // old status
		msg.add(value);
		ClientMessage msg2 = new ClientMessage("UpdateParameter", msg, 6);
		ClientUI.chat.accept(msg2);

	}

	/**
	 * GetAllRequests returns all the requests of the park from the database as
	 * ManagerRequest
	 * 
	 * 
	 * @param ParkName
	 * @return arraylist that contains all the requests
	 */
	public static ArrayList<ManagerRequest> GetAllRequests(String ParkName) {
		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add(ParkName);
		ClientMessage msg2 = new ClientMessage("getAllRequests", msg, msg.size());
		ClientUI.chat.accept(msg2);
		ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
		ArrayList<ManagerRequest> result = (ArrayList<ManagerRequest>) srMessage.getData();
		return result;
	}
}