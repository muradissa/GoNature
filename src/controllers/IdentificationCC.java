package controllers;

import java.util.ArrayList;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Subscriber;
import entities.Traveler;

public class IdentificationCC {
	/**
	 * checkSubscriptionNumber serching after subscriber in the date base.
	 * 
	 * 
	 * @param subNum - subscription number
	 * @return null if the subscriber doesn`t exist,if exist returns Subscriber
	 *         class with his details
	 * 
	 */
	public static Subscriber checkSubscriptionNumber(String subNum) {

		if (inputValidator.checkSubscription(subNum)) {
			ArrayList<Object> list = new ArrayList<>();
			list.add(subNum);
			ClientMessage msgFromClient = new ClientMessage("checkSubscriptionNumber", list, list.size());

			ClientUI.chat.accept(msgFromClient);
			ServerMessage msgFromServer = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
			Subscriber result = (Subscriber) msgFromServer.getData();

			return result;
		}
		return null;
	}

	/**
	 * checkId serching after Traveler in the date base.
	 * 
	 * @param idNumber - id number
	 * @return
	 */
	public static Traveler checkId(String idNumber) {
		if (inputValidator.checkId(idNumber)) {
			Traveler result = null;
			ArrayList<Object> list = new ArrayList<>();
			list.add(idNumber);
			ClientMessage msgFromClient = new ClientMessage("checkId", list, list.size());

			ClientUI.chat.accept(msgFromClient);
			ServerMessage msgFromServer = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
			result = (Traveler) msgFromServer.getData();

			return result;
		}
		return null;
	}

	/**
	 * checking is the input id is a guide
	 * 
	 * @param idNumber - id number
	 * @return true if the traveler is an guide
	 * else false
	 */
	public static boolean checkGuide(String idNumber) {
		Traveler guide = checkId(idNumber);
		if (guide == null)
			return false;
		return guide.isGuide();
	}

}
