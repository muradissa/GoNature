package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.xml.stream.events.StartDocument;

import com.sun.javafx.webkit.ThemeClientImpl;

import entities.personAddToTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * controller for the order bill gui
 * @author ayman
 *
 */
public class billController implements Initializable {

    @FXML
    private Label DateLabel;

    @FXML
    private Label ordererIdLabel;

    @FXML
    private Label orderSummaryLabel;
    
    private static String DateString,ordererIdString,orderSummaryString;
    
    /**
     * this start method sets necessary fields for the bill gui and loads the fxml file and starts it
     * @param primaryStage - stage to open the gui on
     * @param DateString - String representing the date of this bill
     * @param ordererIdString - String representing the id number of the person whom this bill is for
     * @param orderSummaryString - String representing the data of the bill
     * 
     */
    
    public void start(Stage primaryStage,String DateString,String ordererIdString,String orderSummaryString) {
    	this.DateString=DateString;
		this.ordererIdString=ordererIdString;
		this.orderSummaryString=orderSummaryString;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/GUI/billGUI.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setTitle("Bill");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    
	@Override
	/**
	 * initialize method for the gui to fill all the gui elements
	 */
	public void initialize(URL location, ResourceBundle resources) {
		DateLabel.setText(DateString);
		ordererIdLabel.setText(ordererIdString);
		orderSummaryLabel.setText(orderSummaryString);
	}

}