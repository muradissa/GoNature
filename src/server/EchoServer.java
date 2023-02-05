package server;
// This file contains material supporting section 3.7 of the textbook:

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import Client.GoNatureClient;
import GUI.ServerGUIController;
import GUI.choosingAnotherOrderTimeCC;
import Protocol.ClientMessage;
import Protocol.ServerMessage;
import entities.Connection;
import entities.Order;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	
	public static ArrayList<Connection> clientTypes=new ArrayList<Connection>();
	public static HashMap<ConnectionToClient, Connection> clientsMap=new HashMap<ConnectionToClient, Connection>();
	public static ServerGUIController serverController;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);

		try {

			mysqlConnection.connecttoDB();

		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	// public void handleMessageFromClient(Object msg, ConnectionToClient client)

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Object result = null;

		if (msg instanceof ClientMessage) {

			java.lang.reflect.Method method = null;

			try {
				if(((ClientMessage) msg).getMethodName().equals(""))
				{
					ServerMessage sr = new ServerMessage(((ClientMessage) msg).getMethodName(), true);
					if(!clientsMap.containsValue((Connection)((ClientMessage) msg).getParameters().get(0)))
					{
						clientTypes.add((Connection)((ClientMessage) msg).getParameters().get(0));
						serverController.addConnection();
						clientsMap.put(client, (Connection)((ClientMessage) msg).getParameters().get(0));	
					}
					else {
						sr.setData(false);
					}
					try {
						client.sendToClient(sr);
					} catch (IOException e) {e.printStackTrace();}
					serverController.refresh();
					return;
				}
				else 
				if (((ClientMessage) msg).getNumParameters() == 0)
					method = mysqlConnection.class.getMethod(((ClientMessage) msg).getMethodName(), null);
				else
					method = mysqlConnection.class.getMethod(((ClientMessage) msg).getMethodName(),
							((ClientMessage) msg).getParameters().getClass());

			} catch (NoSuchMethodException e) {
				
				e.printStackTrace();
			} catch (SecurityException e) {
				
				e.printStackTrace();
			}

			try {
				if (((ClientMessage) msg).getNumParameters() == 0)
					result = method.invoke(null);
				else
					result = method.invoke(null, ((ClientMessage) msg).getParameters());
				
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				
			}

			try {
				ServerMessage sr = new ServerMessage(((ClientMessage) msg).getMethodName(), result);
				if(sr.getCommand().equals("addOrder")&&(sr.getData())!=null)
				{
					boolean res=(boolean)sr.getData();
					if(res)
					{
						ClientMessage cmsg=(ClientMessage) msg;
						Order order=(Order)((ClientMessage) msg).getParameters().get(0);
						serverController.writeToConsole("["+"sending sms to: "+order.getPhoneNumber()+"  sending email to: "+order.getEmail()+"  message : order added succesfully, we are waiting for you on : "+order.getVisitDate() +", "+order.getVisitTime()+"]");
					}
				}
				client.sendToClient(sr);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Message received: " + ((ClientMessage) msg).getMethodName() + " from " + client);

		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}
	
	
	
	public int checkDisconnectedClients() {
		ArrayList<ConnectionToClient> connections=new ArrayList<ConnectionToClient>(clientsMap.keySet());
		int cnt=0;
		for(ConnectionToClient c:connections)
		{
			synchronized (c) {
				if(c!=null)
				{
					if(c.toString()==null)
					{
					clientDisconnected(c);
					cnt++;
					}
				}
			}	
		}
		return cnt;
	}

	protected  void	clientDisconnected(ConnectionToClient client)
	{
		Connection connection=clientsMap.remove(client);
		clientTypes.remove(connection);
		serverController.refresh();
	}
	
	

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}
//End of EchoServer class
