package server;

import javafx.application.Application;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.util.Vector;

import GUI.ServerGUIController;
import GUI.TotalVisitorReportC;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static EchoServer sv;

	
	public static void main(String args[]) throws Exception {
		launch(args);
	} 

	/**
	 * starts the server user interface
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		ServerGUIController server = new ServerGUIController();

		server.start(primaryStage);

	}

	/**
	 * starts the server
	 * @param p-the port to run server on
	 * @return true if server started successfully,false otherwise
	 */
	public static boolean runServer(String p) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(p); // Set port to 5555
			if (port != 5555)
				return false;
		} catch (Throwable t) {
			System.out.println("ERROR - Could not connect!");
		}

		sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
		return true;
	}
	
	/**
	 * closes the server
	 * @return true if server closed successfully,false otherwise
	 */
	public static boolean closeServer()
	{
		try {
			if(sv!=null)
				sv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void stop()
	{
		closeServer();
		System.exit(0);
	}

}
