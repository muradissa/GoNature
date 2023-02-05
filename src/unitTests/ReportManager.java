package unitTests;

import java.util.ArrayList;

import Client.ClientUI;
import Client.GoNatureClient;
import Protocol.ClientMessage;
import Protocol.ServerMessage;

public class ReportManager implements IReport {
	public String getReportMessage(ArrayList<Object> msg) {
		ClientMessage msg2 = new ClientMessage("GetReport", msg, 3);
		ClientUI.chat.accept(msg2);
		ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
		return (String) srMessage.getData();
	}

	public void addReportToDBMessage(ArrayList<Object> msg) {
		ClientMessage msg2 = new ClientMessage("InsertReports", msg, 4);
		ClientUI.chat.accept(msg2);
	}

	public boolean checkReportIfExist(ArrayList<Object> msg) {
		ClientMessage msg2 = new ClientMessage("IsReportExisted", msg, 3);
		ClientUI.chat.accept(msg2);
		ServerMessage srMessage = GoNatureClient.messageRecievedFromServerEvents.get(msg2.getMethodName());
		boolean result = (boolean) srMessage.getData();
		return result;
	}
}
