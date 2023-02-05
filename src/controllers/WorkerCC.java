package controllers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Order;
import entities.Park;

public class WorkerCC {
	
	
	/**
	 * 
	 * checkParkVacancy sends to server message to get the park data.
	 * 
	 * @param parkName
	 * @return park
	 */
	public static Park checkParkVacancy(String parkName)
	{
		ArrayList<Object> list=new ArrayList<>();
		list.add(parkName);
		ClientMessage msgFromClient=new ClientMessage("checkParkVacancy",list, list.size());
		 ClientUI.chat.accept(msgFromClient);
		 ServerMessage res= GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		 Park park=(Park) res.getData();//current park
		 return park;
	}
	
	/**
	 * cardReaderSimulator simulates the card reader
	 * 
	 * 
	 * @param parkName
	 * @param entrance
	 * @return order 
	 */
	public static Order cardReaderSimulator(String parkName, boolean entrance)
	{
		ArrayList<Object> list=new ArrayList<>();
		list.add(Date.valueOf(LocalDate.now()));
		list.add(Time.valueOf(LocalTime.now()));
		list.add(parkName);
		list.add(entrance);
		ClientMessage msgFromClient=new ClientMessage("getRandomOrder",list, list.size());
		ClientUI.chat.accept(msgFromClient);
		ServerMessage res= GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		ArrayList<Order> orders=(ArrayList<Order>) res.getData();
		int n=orders.size();
		if(n>0)
		{
			Random random=new Random();
			 int randIndex=random.nextInt(n);
			 System.out.println(randIndex);
			 return orders.get(randIndex);
		 }
		 return null;
	}

	
}
