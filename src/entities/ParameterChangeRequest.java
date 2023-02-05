package entities;

import java.io.Serializable;
import java.sql.Date;

/**
 * this is ParameterChangeRequest class that hold information about parameter change request
 * @author Adi this is ParameterChangeRequest class that hold the values of the
 *         new value of parameter and the old value
 * 
 */
public class ParameterChangeRequest extends ManagerRequest implements Serializable {

	private ParameterType type;
	private Object newValue;
	private Park park;

	/**
	 * this Constructor ParameterChangeRequest with specific type , newvalue and
	 * oldvalue and park and sender
	 * 
	 * @param type
	 * @param oldValue
	 * @param newValueString
	 * @param park
	 * @param sender
	 */
	public ParameterChangeRequest(ParameterType type,  Object newValue, Park park, Date date,
			String status) {
		super(status, date);
		this.type = type;
		this.newValue = newValue;
		this.park = park;
	}

	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}


	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

}