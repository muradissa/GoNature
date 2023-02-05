package entities;

import java.sql.Date;

/**
 * this class is for adding support for javafx TableView
 * @author adi
 *
 */
public class TableViewHelper {
	int id;
	String name, status;
	Date date, endDate, startDate;

	String parkName;
	String classType;
	Object oldValue, newValue;

	/**
	 * 
	 * @param id-id number in table view
	 * @param nameString-name of item in tableview
	 * @param status-status of item in tableview
	 * @param date-date of item in tableview
	 */
	public TableViewHelper(int id, String nameString, String status, Date date) {
		super();
		this.id = id;
		this.name = nameString;
		this.status = status;
		this.date = date;
	}
	/**
	 * 
	 * @param id-id number in table view
	 * @param name-name of item in tableview
	 * @param status-status of item in tableview
	 * @param date-date of item in tableview
	 * @param parkName-name of park
	 */
	public TableViewHelper(int id, String name, String status, Date date, String parkName) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.date = date;
		this.parkName = parkName;
	}

	/**
	 * 
	 * @return endDate field
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * sets the endDate field using parameter
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return startDate field
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * sets the startDate field using parameter
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 
	 * @return oldValue field
	 */
	public Object getOldValue() {
		return oldValue;
	}

	/**
	 * sets the oldValue field using parameter
	 * @param oldValue
	 */
	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
	/**
	 * 
	 * @return newValue field
	 */
	public Object getNewValue() {
		return newValue;
	}

	/**
	 * sets the newValue field using parameter
	 * @param newValue
	 */
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	/**
	 * 
	 * @return classType field
	 */
	public String getClassType() {
		return classType;
	}
	/**
	 * sets the classType field using parameter
	 * @param classType
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}
	/**
	 * 
	 * @return parkName field
	 */
	public String getParkName() {
		return parkName;
	}

	/**
	 * sets the parkName field using parameter
	 * @param parkName
	 */
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	/**
	 * 
	 * @return id field
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets the id field using parameter
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return name field
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name field using parameter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return status field
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * sets the status field using parameter
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return date field
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * sets the date field using parameter
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
