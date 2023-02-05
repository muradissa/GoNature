package controllers;

import java.util.ArrayList;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Order;

public class WaitingListCC {

	/**
	 * enterWaitingList sending message to the server to insert order to the waiting
	 * list
	 * 
	 * @param order - order to enter the waiting list
	 * @return true if its done , else false
	 */
	public static boolean enterWaitingList(Order order) {
		ArrayList<Object> arr = new ArrayList<Object>();
		arr.add(order);
		ClientMessage msg1 = new ClientMessage("enterWaitingList", arr, arr.size());

		ClientUI.chat.accept(msg1);

		ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());
		return (boolean) msg2.getData();
	}

	/**
	 * cancelOrderFromWaitingList sends message to the server to delete order from
	 * waiting list
	 * 
	 * 
	 * @param order
	 * @return true if its done , else false
	 */
	public static boolean cancelOrderFromWaitingList(Order order) {
		ArrayList<Object> arr = new ArrayList<Object>();
		arr.add(order);
		ClientMessage msg1 = new ClientMessage("cancelOrderFromWaitingList", arr, arr.size());
		ClientUI.chat.accept(msg1);
		ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());
		return (boolean) msg2.getData();
	}

	/**
	 * confirmOrderFromWaitingList sending message to server to confirm an order.
	 * 
	 * @param order
	 * @return true if its done , else false
	 */
	public static boolean confirmOrderFromWaitingList(Order order) {
		ArrayList<Object> arr = new ArrayList<Object>();
		arr.add(order);
		ClientMessage msg1 = new ClientMessage("confirmOrderFromWaitingList", arr, arr.size());
		ClientUI.chat.accept(msg1);
		ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());
		return (boolean) msg2.getData();
	}

	/**
	 * 
	 * getAllOrdersInWaitingListForTraveler sending message to server to get all the
	 * orders of the specific traveler
	 * 
	 * @param idNumber
	 * @return arraylist of orders that contains all the orders
	 */
	public static ArrayList<Order> getAllOrdersInWaitingListForTraveler(String idNumber) {

		ArrayList<Object> arr = new ArrayList<Object>();
		ArrayList<Order> arrOrders = new ArrayList<Order>();
		arr.add(idNumber);
		ClientMessage msg1 = new ClientMessage("getAllOrdersInWaitingListForTraveler", arr, arr.size());
		ClientUI.chat.accept(msg1);
		ServerMessage msg2 = GoNatureClient.messageRecievedFromServerEvents.get(msg1.getMethodName());
		arrOrders = (ArrayList<Order>) msg2.getData();
		return arrOrders;
	}

}
