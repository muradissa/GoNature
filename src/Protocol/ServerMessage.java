package Protocol;

import java.io.Serializable;

/**
 * this class is used to send messages from server to client
 * @author group 2
 *
 */
@SuppressWarnings("serial")
public class ServerMessage implements Serializable {

	private String command;//methodName
	private Object data;

	/**
	 * 
	 * @param command-the method(in mysqlconnection) that we are returning the data from
	 * @param data-the data to be returned
	 */
	public ServerMessage(String command, Object data) {
		super();
		this.command = command;
		this.data = data;
	}

	/**
	 * 
	 * @return command field
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * sets the command field using given parameter
	 * @param command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * 
	 * @return data field
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 
	 * @param sets the data field using given parameter
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * to string method
	 */
	@Override
	public String toString() {
		return "SRMessage [command=" + command.toString() + ", data=" + data.toString() + "]";
	}

}
