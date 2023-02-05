package controllers;

import java.util.ArrayList;

import Client.GoNatureClient;
import Client.ClientUI;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Worker;
import unitTests.ILogIn;
import unitTests.LoginManager;

public class WorkerLoginCC {
	/**
	 * 
	 * checkWorker sends message to server to search after a worker with the input
	 * username and password and returns instance of the worker.
	 * 
	 * @param username
	 * @param password
	 * @return worker if exist else null
	 */
	
	private static  ILogIn login;
	
	public static Worker checkWorker(String username, String password) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(username);
		list.add(password);
		
		if(login == null) {
			login = new LoginManager();
		}

		Worker worker =login.getWorker(list);
		return worker;
	}
	

	public ILogIn getLogin() {
		return login;
	}

	public void setLogin(ILogIn login) {
		WorkerLoginCC.login = login;
	}




}
