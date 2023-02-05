package controllers;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;
import java.util.Date;
import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.*;
import javafx.concurrent.Worker;

public class PaymentController {

	final static int priceCard = 50;

	/**
	 * public Order(Park park, Date visitDate, Time visitTime, int numOfVisitors,
	 * String email, Traveler orderer ,OrderType orderType, double price,boolean
	 * confirmed,boolean payStatus)
	 * 
	 * public CreditCard(String cardNumber, String expireDate, String cvvString,
	 * String cardHolderId)
	 * 
	 * public Sale(Date startDate, Date endDate, int salePercent, String saleName)
	 * 
	 * public addSaleRequest(Worker sender, Park park, Sale sale)
	 * 
	 * @param order
	 */
	public static double calculateOrderPrice(Order order) {

		double price = priceCard;
		Sale sale2;

		price = price * order.getNumOfVisitors();
		// if the traveler have SUBSCRIBER then will take sale 20%
		if (order.getOrderType() == OrderType.UNPLANNEDSUBSCRIBER) {
			price = (double) (price * 0.8);
		}
		if (order.getOrderType() == OrderType.PLANNEDSUBSCRIBER) {
			price = (double) (price * 0.8 * 0.85);
		}

		if (order.getOrderType() == OrderType.PLANNEDTRAVELER) {
			price = (double) (price * 0.85);
		}

		if (order.getOrderType() == OrderType.PLANNEDGUIDE) {
			price -= priceCard;
			price = (double) (price * 0.75);
			if (order.getPayStatus()) {
				price = (double) (price * 0.88);
			}
		}

		if (order.getOrderType() == OrderType.UNPLANNEDGUIDE) {
			price = (double) (price * 0.9);
			/*
			 * should ask him if he want to pay now or not if yes then he will take sale 12%
			 * (price*0.88)
			 * 
			 */
		}

		/*
		 * check if the park have sale in visit Date Date startDate <= Date visitDate <=
		 * Date endDate
		 * 
		 */
		sale2 = getParkSalesInVisitDate(order.getPark().getName(), order.getVisitDate());
		if (sale2 != null) {
			price = price * ((100 - sale2.getSalePercent()) / 100.0);
		}

		return Math.round(price * 100.0) / 100.0;
	}

	/*
	 * PLANNEDTRAVELER, //an order that has been placed using the application for a
	 * nonsubscriber ->> 15% PLANNEDSUBSCRIBER, //an order that has been placed
	 * using the application for a subscriber ->> 20% PLANNEDGUIDE, //an order that
	 * has been placed using the application for a group guide ->> 25% +12% + guide
	 * 100% UNPLANNEDTRAVELER, //an order that has been placed at the park(mezdamen)
	 * for a nonsubscriber ->> 0% UNPLANNEDSUBSCRIBER,//an order that has been
	 * placed at the park(mezdamen) for a subscriber ->> 20% UNPLANNEDGUIDE //an
	 * order that has been placed at the park(mezdamen) for a group guide ->> 10%
	 */

	/**
	 * 
	 * getParkSalesInVisitDate searching after sale in the park in the the visit
	 * date.
	 * 
	 * @param namePark
	 * @param visitdate
	 * @return Sale or null
	 */
	public static Sale getParkSalesInVisitDate(String namePark, Date visitdate) {
		Sale sale;
		ArrayList<Object> parkSaleList = new ArrayList<Object>();
		parkSaleList.add(namePark);
		parkSaleList.add(visitdate);
		System.out.println(visitdate);

		ClientMessage msgFromClient = new ClientMessage("getParkSaleInVisitDate", parkSaleList, parkSaleList.size());

		ClientUI.chat.accept(msgFromClient);
		ServerMessage res = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		if (res.getData() != null) {
			System.out.println("sale found");
			sale = (Sale) res.getData();
			return sale;
		}
		return null;
	}

	/**
	 * checkSubscriptionWithIdTraveler checking if there is subscriber with the
	 * input ud number
	 * 
	 * @param IdTraveler
	 * @return true if exist else false
	 */
	public static boolean checkSubscriptionWithIdTraveler(String IdTraveler) {

		ArrayList<Object> newSubscriberList = new ArrayList<Object>();
		newSubscriberList.add(IdTraveler);
		ClientMessage msgFromClient = new ClientMessage("checkSubscriptionNumber", newSubscriberList,
				newSubscriberList.size());

		ClientUI.chat.accept(msgFromClient);

		ServerMessage res = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		if (res.getData() != null)
			return true;

		return false;

	}

	/**
	 * getbill method to get explenation of the price in String.
	 * 
	 * @param order
	 * @return String
	 */
	public static String getbill(Order order) {
		OrderType orderType = order.getOrderType();
		double price = priceCard;
		Sale sale2;
		double discount;
		StringBuilder stringBuilder = new StringBuilder();
		price = price * order.getNumOfVisitors();
		stringBuilder.append(
				"Price before discount :  " + order.getNumOfVisitors() + " * " + priceCard + " = " + price + "\n\n");

		// if the traveler have SUBSCRIBER then will take sale 20%
		if (orderType == OrderType.UNPLANNEDSUBSCRIBER) {
			discount = (double) (price * 0.2);
			price = (double) (price * 0.8);
			stringBuilder.append("Subscriber discount( 20% ) :   -" + discount + "\n\n");
		}
		if (orderType == OrderType.PLANNEDSUBSCRIBER) {
			discount = (double) (price * 0.2);
			price = (double) (price * 0.8);
			stringBuilder.append("Subscriber discount( 20% ) :   -" + discount + "\n\n");
			discount = (double) (price * 0.15);
			price = (double) (price * 0.85);
			stringBuilder.append("Order in app discount( 15% ) :   -" + discount + "\n\n");
		}

		if (orderType == OrderType.PLANNEDTRAVELER) {
			discount = (double) (price * 0.15);
			price = (double) (price * 0.85);
			stringBuilder.append("Order in app discount( 15% ) :   -" + discount + "\n\n");
		}

		if (orderType == OrderType.PLANNEDGUIDE) {
			discount = priceCard;
			price -= priceCard;
			stringBuilder.append("Guide payment on us :   -" + discount + "\n\n");
			discount = (double) (price * 0.25);
			price = (double) (price * 0.75);
			stringBuilder.append("Order in app discount( 25% ) :   -" + discount + "\n\n");
			if (order.getPayStatus()) {
				discount = (double) (price * 0.12);
				price = (double) (price * 0.88);
				stringBuilder.append("Paying in app discount( 12% ) :   -" + discount + "\n\n");
			}
		}

		if (orderType == OrderType.UNPLANNEDGUIDE) {
			discount = (double) (price * 0.1);
			price = (double) (price * 0.9);
			stringBuilder.append("Guide discount( 10% ):   -" + discount + "\n\n");
		}

		sale2 = getParkSalesInVisitDate(order.getPark().getName(), order.getVisitDate());
		if (sale2 != null) {
			discount = (sale2.getSalePercent() / 100.0) * price;
			price = price * ((100 - sale2.getSalePercent()) / 100.0);
			stringBuilder.append(sale2.getSaleName() + "( " + sale2.getSalePercent() + "% ):   -" + discount + "\n\n");
		}
		stringBuilder.append("Final Price :  " + (Math.round(price * 100.0) / 100.0) + "\n\n");
		return stringBuilder.toString();
	}
}
