package Protocol;

import java.io.Serializable;
import java.security.Policy.Parameters;
import java.util.ArrayList;

/**
 * this class is used to send a message from client to server
 * @author group 2
 *
 */
public class ClientMessage implements Serializable {
	private ArrayList<Object> parameters;
	private String methodName;
	private int numParameters;


	/**
	 * 
	 * @return methodName field
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * sets the methodName field using the given parameter
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * 
	 * @return numParameters field
	 */
	public int getNumParameters() {
		return numParameters;
	}

	/**
	 * sets the numParameters field using the given parameter
	 * @param numParameters
	 */
	public void setNumParameters(int numParameters) {
		this.numParameters = numParameters;
	}

	/**
	 * @param methodName-the name of method to be called on mysqlconnection class
	 * @param parameters-an arraylist<object> that has all parameter that the mysqlconnection method needs
	 * @param numParameters-the number of parameters
	 */
	public ClientMessage( String methodName,ArrayList<Object> parameters, int numParameters) {
		super();
		this.parameters = parameters;
		this.methodName = methodName;
		this.numParameters = numParameters;
	}

	/**
	 * 
	 * @return parameter field
	 */
	public ArrayList<Object> getParameters() {
		return parameters;
	}

	/**
	 * sets parameter field
	 * @param parameters
	 */
	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}



}
