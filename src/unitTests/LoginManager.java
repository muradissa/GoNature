package unitTests;

import java.util.ArrayList;

import Client.ClientUI;
import Client.GoNatureClient;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Worker;

public class LoginManager implements  ILogIn{

	public  Worker getWorker(ArrayList<Object> list) {
		ClientMessage msgFromClient = new ClientMessage("checkWorker", list, list.size());
		ClientUI.chat.accept(msgFromClient);
			
		ServerMessage msgFromServer = GoNatureClient.messageRecievedFromServerEvents.get(msgFromClient.getMethodName());
		Worker worker = (Worker) msgFromServer.getData();
		return worker;
	}
}
