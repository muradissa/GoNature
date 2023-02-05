package controllers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Order;
import entities.Park;
import entities.Subscriber;

public class ParkEntranceAndExitCC {

	/**
	 * identifyAtEntrance method to track the nuumber of the visitors in the park
	 * the method adds the number of the visitors at entrance from the total
	 * visitors in park and update the entrance time in the data base
	 * 
	 * @param identifyNumber
	 * @param parkName
	 * @return
	 */
	public static boolean identifyAtEntrance(String identifyNumber, String parkName) {
		Order order = identify(identifyNumber, parkName);
		if (order == null)
			return false;
		updateNumberOfVisitors(order);
		updateArrivalStatus(order);
		return true;
	}

	/**
	 * identifyAtExit method to track the nuumber of the visitors in the park the
	 * method subtracts the number of the visitors at exit from the total visitors
	 * in park and update the exit time in the data base
	 * 
	 * @param identifyNumber
	 * @param parkName
	 * @return false if the order id is not exist else true.
	 */
	public static boolean identifyAtExit(String identifyNumber, String parkName) {
		Order order = identify(identifyNumber, parkName);
		if (order == null)
			return false;
		order.setNumOfVisitors(-1 * order.getNumOfVisitors());
		updateArrivalStatusOnExit(order);
		updateNumberOfVisitors(order);
		return true;
	}

	/**
	 * identify methode checks if there is order now with the identifyNumber in the
	 * park
	 * 
	 * @param identifyNumber
	 * @param parkName
	 * @return order if its exist else null
	 */
	public static Order identify(String identifyNumber, String parkName) {
		Order order;
		Subscriber subscriber = null;
		ArrayList<Object> list = new ArrayList<>();// list(identifyCard(number),methodOfidentify)
		if (inputValidator.checkSubscription(identifyNumber) == true) {
			list.add(identifyNumber);
			ClientMessage msgFromClient1 = new ClientMessage("checkSubscriptionNumber", list, list.size());
			ClientUI.chat.accept(msgFromClient1);
			ServerMessage res1 = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient1.getMethodName());
			subscriber = (Subscriber) res1.getData();
			if (subscriber != null) {
				identifyNumber = subscriber.getIdNumber();
			}
		}
		if (inputValidator.checkId(identifyNumber) == false)
			return null;
		list.clear();
		list.add(identifyNumber);
		list.add(Date.valueOf(LocalDate.now()));
		list.add(Time.valueOf(LocalTime.now()));
		list.add(parkName);
		ClientMessage msgFromClient = new ClientMessage("searchOrder", list, list.size());
		ClientUI.chat.accept(msgFromClient);
		ServerMessage res = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		order = (Order) res.getData();
		return order;
	}

	/**
	 * method to update the arrival status at entrance
	 * 
	 * @param order
	 */
	public static void updateArrivalStatus(Order order) {
		ArrayList<Object> list = new ArrayList<>();
		list.add(order);
		ClientMessage msgFromClient = new ClientMessage("updateOrderArrivalStatus", list, list.size());
		ClientUI.chat.accept(msgFromClient);

	}

	/**
	 * method to update the arrival status at exit
	 * 
	 * @param order
	 */
	public static void updateArrivalStatusOnExit(Order order) {
		ArrayList<Object> list = new ArrayList<>();
		list.add(order);
		ClientMessage msgFromClient = new ClientMessage("updateOrderArrivalStatusOnExit", list, list.size());
		ClientUI.chat.accept(msgFromClient);

	}

	/**
	 * updating the number of visitors in park
	 * 
	 * @param order
	 * @return the current number of visitors in park
	 */
	public static int updateNumberOfVisitors(Order order) {
		ArrayList<Object> list = new ArrayList<>();
		list.add(order);
		ClientMessage msgFromClient = new ClientMessage("updateNumberOfVisitors", list, list.size());
		ClientUI.chat.accept(msgFromClient);
		ServerMessage res = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		int currentNumberOfVisitors = (int) res.getData();
		return currentNumberOfVisitors;

	}

}