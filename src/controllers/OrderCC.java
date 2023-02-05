package controllers;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Order;
import entities.Park;

public class OrderCC {
	/**
	 * checkOrderAvailibilty checks if its available to add the order.
	 * 
	 * 
	 * @param time
	 * @param date
	 * @param numOfVisitors - number of visitors
	 * @param parkName
	 * @return returns true if its available, otherwise false
	 */
	public static boolean checkOrderAvailibilty(Time time, Date date, int numOfVisitors, String parkName) {
		ArrayList<Object> arr = new ArrayList<Object>();
		arr.add(time);
		arr.add(date);
		arr.add(numOfVisitors);
		arr.add(parkName);
		boolean res = false;

		if (time.compareTo(new Time(19, 0, 0)) < 0 & time.compareTo(new Time(8, 0, 0)) > 0) {

			ClientMessage msgFromClient = new ClientMessage("checkAvailibility", arr, arr.size());
			ClientUI.chat.accept(msgFromClient);

			ServerMessage msgFromServer = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
			res = (boolean) msgFromServer.getData();
		}
		return res;
	}

	/**
	 * addOrder adding order to the database
	 * 
	 * 
	 * @param order- order to add
	 * @return true if the add completed , else false
	 */
	public static boolean addOrder(Order order) {

		if (order.getOrderType().toString().startsWith("UNPLANNED") || checkOrderAvailibilty(order.getVisitTime(),
				order.getVisitDate(), order.getNumOfVisitors(), order.getPark().getName())) {
			ArrayList<Object> arr = new ArrayList<Object>();
			arr.add(order);
			ClientMessage msg1 = new ClientMessage("addOrder", arr, arr.size());
			ClientUI.chat.accept(msg1);

			ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());
			return (boolean) msg2.getData();
		}
		return false;
	}

	/**
	 * 
	 * 
	 * @param order
	 * @return
	 */
	public static boolean confirmOrder(Order order) {
		ArrayList<Object> arr = new ArrayList<Object>();
		arr.add(order);
		arr.add("confirmed");

		return updateOrderStatus(arr);

	}

	/**
	 * cancels order
	 * 
	 * @param order
	 * @return false if the order is not existed else true
	 */
	public static boolean cancelOrder(Order order) {
		ArrayList<Object> arr = new ArrayList<Object>();
		arr.add(order);
		arr.add("unconfirmed");

		return updateOrderStatus(arr);

	}

	/**
	 * updates the status of the order
	 * 
	 * @param arr - arraylist contains arr[0] = Order , arr[1] = new status as
	 *            string
	 * @return
	 */
	public static boolean updateOrderStatus(ArrayList<Object> arr) {
		ClientMessage msg1 = new ClientMessage("updateOrderStatus", arr, arr.size());

		ClientUI.chat.accept(msg1);

		ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());
		return (boolean) msg2.getData();

	}

	/**
	 * 
	 * @param time
	 * @param date
	 * @param numOfVisitors
	 * @param parkName
	 * @return
	 */

	public static ArrayList<Timestamp> fetchAvailibleOrderDates(Time time, Date date, int numOfVisitors,
			String parkName) {

		ArrayList<Timestamp> arr = new ArrayList<Timestamp>();

		for (int i = 8; i < 20; i++) {
			Time time2 = new Time(i, 0, 0);
			if (checkOrderAvailibilty(time2, date, numOfVisitors, parkName))
				arr.add(new Timestamp(date.getYear(), date.getMonth(), date.getDate(), i, 0, 0, 0));
		}

		ArrayList<Date> dates = new ArrayList<Date>();
		for (int i = -3; i < 4; i++) {
			dates.add(new Date(date.getYear(), date.getMonth(), date.getDate() + i));
		}

		for (Date date1 : dates)
			if (checkOrderAvailibilty(time, date1, numOfVisitors, parkName))
				arr.add(new Timestamp(date1.getYear(), date1.getMonth(), date1.getDate(), time.getHours(),
						time.getMinutes(), 0, 0));

		return arr;
	}

	/**
	 * getAllOrderForTraveler searching after the the traveler orders in the
	 * database
	 * 
	 * 
	 * @param idNumber
	 * @return arraylist with all the traveler orders
	 */
	public static ArrayList<Order> getAllOrderForTraveler(String idNumber) {

		ArrayList<Object> arr = new ArrayList<Object>();
		ArrayList<Order> arrOrders = new ArrayList<Order>();

		arr.add(idNumber);
		ClientMessage msg1 = new ClientMessage("getAllOrdersForTraveler", arr, arr.size());

		ClientUI.chat.accept(msg1);

		ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());

		arrOrders = (ArrayList<Order>) msg2.getData();

		return arrOrders;
	}

	/**
	 * getAllParksforClient gets all the parks
	 * 
	 * @return arraylist that contains all the parks
	 */
	public static ArrayList<Park> getAllParksforClient() {
		ArrayList<Object> arr = new ArrayList<Object>();
		ArrayList<Park> arrParks = new ArrayList<Park>();
		arr.add(null);
		ClientMessage msg1 = new ClientMessage("getAllParksForClient", arr, arr.size());
		ClientUI.chat.accept(msg1);
		ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());
		arrParks = (ArrayList<Park>) msg2.getData();
		return arrParks;
	}

}
