package controllers;


import java.util.ArrayList;

import Client.*;
import Protocol.*;
import entities.*;

/**
 * 
 * @author Murad Eissa
 * @date 15/12/2020
 *
 */
public class SubscriberCC {
	
	/* 
	 *  Subscriber(String subscriptionNumber, String idNumber, String firstName, String lastName, String phoneNumber,
	 * 		String email, int numberOfFamilyMembers, CreditCard creditCard)
	 * 
	 * 
	 *  CreditCard(String cardNumber, String expireDate, String cvvString, String cardHolderId)
	 *  
	 */
	private Subscriber subscriber;
	private String subscriptionNumber;
	
	
	public SubscriberCC (Subscriber subscriber) {
		this.subscriber=subscriber;
	}
	public SubscriberCC (String subscriptionNumber) {
		this.subscriptionNumber=subscriptionNumber;
	}
	
	
	/**
	 * subscriptionNumberValidator checks if the subscriber is in the database
	 * 
	 * @return true if its in the database, else false
	 * 
	 */
	public static  boolean subscriptionNumberValidator(String subscriptionNumber) {
		
		ArrayList<Object> newSubscriberList = new ArrayList<Object>();
		newSubscriberList.add(subscriptionNumber);
		ClientMessage msgFromClient=new ClientMessage("checkSubscriptionNumber",newSubscriberList, newSubscriberList.size());
		
		ClientUI.chat.accept(msgFromClient);
		ServerMessage res= GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		if (res.getData() !=null) {
//			subscriber =(Subscriber) res.getData();
//			System.out.println(subscriber);
			return true;

		}
		return false;	
	}
	/**
	 * getSubsscriber sends message to the server to get 
	 * the subscriber from hir subscriptionNumber
	 * 
	 * @param subscriptionNumber - subscription number 
	 * @return subscriber if its found else null
	 */
	public static Subscriber getSubsscriber(String subscriptionNumber) {
		
		Subscriber subscriber=null;
		ArrayList<Object> newSubscriberList = new ArrayList<Object>();
		newSubscriberList.add(subscriptionNumber);
		ClientMessage msgFromClient=new ClientMessage("checkSubscriptionNumber",newSubscriberList, newSubscriberList.size());
		
		ClientUI.chat.accept(msgFromClient);
		ServerMessage res= GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		if (res.getData() !=null) {
			subscriber =(Subscriber) res.getData();
			
			return subscriber;
		}
		return null;	
	}
}