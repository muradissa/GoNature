package controllers;

import java.util.ArrayList;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Subscriber;
import entities.Traveler;

public class ServiceRepresentativeCC {
	
	/**
	 * 
	 * addNewSubscriber adds new subscriber to the database   
	 * 
	 * @param newSubscriber - new subscriber 
	 * @return true if the process is done else false
	 */
	public static boolean addNewSubscriber(Subscriber newSubscriber)
	{
		 ArrayList<Object> newSubscriberList=new ArrayList<>();
		 newSubscriberList.add(newSubscriber);
		 ClientMessage msgFromClient=new ClientMessage("addSubscriberToDB",newSubscriberList, newSubscriberList.size());
		 ClientUI.chat.accept(msgFromClient);
		 ServerMessage res= GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		 return (boolean) res.getData();
		 
	}
	
	/**
	 * addNewGuide adds new guide to the database
	 * 
	 * @param newGuide
	 * @return true if the process is done else false
	 */
	public static boolean addNewGuide(Traveler newGuide)
	{
		 ArrayList<Object> newGuideList=new ArrayList<>();
		 newGuideList.add(newGuide);
		 ClientMessage msgFromClient=new ClientMessage("addTravelerToDB",newGuideList, newGuideList.size());
		 ClientUI.chat.accept(msgFromClient);
		 ServerMessage res= GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		 return (boolean) res.getData();
	}
	
	/**
	 * getSubscriptionCounter methode to get the new subscription number
	 * 
	 * @returns the next empty subscription number
	 */
	public static int getSubscriptionCounter()
	{
		ClientMessage msg=new ClientMessage("getSubscriptionCounter", null, 0);
		ClientUI.chat.accept(msg);
		ServerMessage res=GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
		return (int)res.getData();	
	}
	
	
	/**
	 * getAllSubscribers methode to get all the subscriptions in the database
	 * 
	 * @return arraylist that contains subscribers
	 */
	public static ArrayList<Subscriber> getAllSubscribers()
	{
		ClientMessage msg=new ClientMessage("getAllSubscribers", null, 0);
		ClientUI.chat.accept(msg);
		ServerMessage res=GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
		return (ArrayList<Subscriber>)res.getData();	
	}
	
	/**
	 * getAllGuidesmethode to get all the guides in the database
	 * 
	 * @return arraylist that contains travelers
	 */
	public static ArrayList<Traveler> getAllGuides()
	{
		ClientMessage msg=new ClientMessage("getAllGuides", null, 0);
		ClientUI.chat.accept(msg);
		ServerMessage res=GoNatureClient.messageRecievedFromServerEvents.get(msg.getMethodName());
		return (ArrayList<Traveler>)res.getData();	
	}


	
}
