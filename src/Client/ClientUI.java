package Client;


import GUI.HomePageCC;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientController chat;

	/**
	 * starts the client user interface
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			chat = new ClientController("localhost", 5555);

			HomePageCC homePage = new HomePageCC();
			homePage.start(primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop()
	{
		System.exit(0);
	}
}