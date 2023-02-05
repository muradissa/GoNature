package entities;

import java.io.Serializable;
import java.sql.Date;
/**
 * this is ManagerRequest class that hold information about the request  
 * class ManagerRequest (String statuString, Date date)
 * @author Group'2'
 *
 */
@SuppressWarnings("serial")
public class ManagerRequest implements Serializable {

	String status;
	Date sendDate;
	String name;

	/**
	 * 
	 * @param String statuString
	 * @param Date date
	 */
	public ManagerRequest(String statuString, Date date) {

		this.status = statuString;
		this.sendDate = date;
	}

	/**
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param set new Sting in status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * 
	 * @param set new Date in date
	 */
	public void setSendDate(Date date) {
		this.sendDate = date;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param set new Sting in name
	 */
	public void setName(String name) {
		this.name = name;
	}



}